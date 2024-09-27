package com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.adapters;

import com.PowerUpFullStack.ms_cart.domain.model.Cart;
import com.PowerUpFullStack.ms_cart.domain.model.CartDetails;
import com.PowerUpFullStack.ms_cart.domain.spi.ICartDetailsPersistencePort;
import com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.entities.CartDetailsEntity;
import com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.mapper.ICartDetailsEntityMapper;
import com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.repositories.ICartDetailsRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CartDetailsEntityMySqlAdapter implements ICartDetailsPersistencePort {

    private final ICartDetailsRepository cartDetailsRepository;
    private final ICartDetailsEntityMapper cartDetailsEntityMapper;


    @Override
    public void saveCartDetails(CartDetails cartDetails) {
        cartDetailsRepository.save(cartDetailsEntityMapper.toCartDetailsEntity(cartDetails));
    }

    @Override
    public CartDetails findByCartId(long cartId) {
        return cartDetailsEntityMapper.toCartDetails(cartDetailsRepository.findByCartId(cartId));
    }


}
