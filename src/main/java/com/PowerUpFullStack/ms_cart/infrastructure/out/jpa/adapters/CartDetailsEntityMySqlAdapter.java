package com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.adapters;

import com.PowerUpFullStack.ms_cart.domain.model.CartDetails;
import com.PowerUpFullStack.ms_cart.domain.spi.ICartDetailsPersistencePort;
import com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.mapper.ICartDetailsEntityMapper;
import com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.repositories.ICartDetailsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;


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



}
