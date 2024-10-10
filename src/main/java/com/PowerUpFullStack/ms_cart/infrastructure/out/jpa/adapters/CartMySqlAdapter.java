package com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.adapters;

import com.PowerUpFullStack.ms_cart.domain.model.Cart;
import com.PowerUpFullStack.ms_cart.domain.model.CustomPage;
import com.PowerUpFullStack.ms_cart.domain.spi.ICartPersistencePort;
import com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.entities.CartEntity;
import com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.mapper.ICartEntityMapper;
import com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.repositories.ICartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.adapters.utils.ConstantsJpaAdapters.PAGINATION_PAGE_NUMBER;
import static com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.adapters.utils.ConstantsJpaAdapters.PAGINATION_PAGE_SIZE;

@RequiredArgsConstructor
public class CartMySqlAdapter implements ICartPersistencePort {
    private final ICartRepository cartRepository;
    private final ICartEntityMapper cartEntityMapper;

    @Override
    public void saveCart(Cart cart) {
        cartRepository.save(cartEntityMapper.toCartEntity(cart));
    }

    @Override
    public Optional<Cart> findCartEntity(long userId) {
        return cartRepository.findCartEntity(userId)
                .map(cartEntityMapper::toCart);
    }



    @Override
    public CustomPage<Cart> getPaginationCart() {
        Pageable pageable = PageRequest.of(PAGINATION_PAGE_NUMBER, PAGINATION_PAGE_SIZE);
        Page<CartEntity> cartEntityPage = cartRepository.findAll(pageable);

        if(cartEntityPage.isEmpty()) return null;

        List<Cart> cartContent = cartEntityPage.getContent()
                .stream()
                .map(cartEntityMapper::toCart)
                .toList();

        CustomPage<Cart> customPage = new CustomPage<>(
                cartContent,
                cartEntityPage.getNumber(),
                cartEntityPage.getSize(),
                cartEntityPage.getTotalElements(),
                cartEntityPage.getTotalPages(),
                cartEntityPage.isFirst(),
                cartEntityPage.isLast()
        );
        return null;
    }
}
