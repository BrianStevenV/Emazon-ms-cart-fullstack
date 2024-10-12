package com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.adapters;

import com.PowerUpFullStack.ms_cart.domain.model.CartDetails;
import com.PowerUpFullStack.ms_cart.domain.model.CustomPage;
import com.PowerUpFullStack.ms_cart.domain.spi.ICartDetailsPersistencePort;
import com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.entities.CartDetailsEntity;
import com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.mapper.ICartDetailsEntityMapper;
import com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.repositories.ICartDetailsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.adapters.utils.ConstantsJpaAdapters.PAGINATION_PAGE_NUMBER;
import static com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.adapters.utils.ConstantsJpaAdapters.PAGINATION_PAGE_SIZE;


@RequiredArgsConstructor
public class CartDetailsEntityMySqlAdapter implements ICartDetailsPersistencePort {

    private final ICartDetailsRepository cartDetailsRepository;
    private final ICartDetailsEntityMapper cartDetailsEntityMapper;


    @Override
    public void saveCartDetails(CartDetails cartDetails) {
        cartDetailsRepository.save(cartDetailsEntityMapper.toCartDetailsEntity(cartDetails));
    }

    @Override
    public List<CartDetails> findByCartIdListCartDetails(long cartId) {
        return cartDetailsEntityMapper.toCartDetailsList(cartDetailsRepository.findAllByCartId(cartId));
    }

    @Override
    public Optional<CartDetails> findByCartIdAndProductId(long cartId, long productId) {
        return cartDetailsRepository.findByCartIdAndProductId(cartId, productId)
                .map(cartDetailsEntityMapper::toCartDetails);
    }

    @Transactional
    @Override
    public void deleteCartDetailsByCartIdAndProductId(long cartId, long productId) {
        cartDetailsRepository.disableCartDetailByCartIdAndProductId(cartId, productId);
    }

    @Transactional
    @Override
    public void enableCartDetailByCartIdAndProductId(long cartId, long productId) {
        cartDetailsRepository.enableCartDetailByCartIdAndProductId(cartId, productId);
    }


    @Override
    public CustomPage<CartDetails> getPaginationCartDetails(long cartId){
        Pageable pageable = PageRequest.of(PAGINATION_PAGE_NUMBER, PAGINATION_PAGE_SIZE);
        Page<CartDetailsEntity> cartDetailsPage = cartDetailsRepository.findAllByCartId(pageable, cartId);

        if(cartDetailsPage.isEmpty()) return null;

        List<CartDetails> content = cartDetailsPage.getContent()
                .stream()
                .map(cartDetailsEntityMapper::toCartDetails)
                .toList();

        CustomPage<CartDetails> customPage = new CustomPage<>(
                content,
                cartDetailsPage.getNumber(),
                cartDetailsPage.getSize(),
                cartDetailsPage.getTotalElements(),
                cartDetailsPage.getTotalPages(),
                cartDetailsPage.isFirst(),
                cartDetailsPage.isLast()
        );
        return customPage;
    }


}
