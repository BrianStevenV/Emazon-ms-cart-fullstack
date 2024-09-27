package com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.adapters;

import com.PowerUpFullStack.ms_cart.domain.model.Cart;
import com.PowerUpFullStack.ms_cart.domain.spi.ICartPersistencePort;
import com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.mapper.ICartEntityMapper;
import com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.repositories.ICartRepository;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CartMySqlAdapter implements ICartPersistencePort {
    private final ICartRepository cartRepository;
    private final ICartEntityMapper cartEntityMapper;

    @Override
    public void saveCart(Cart cart) {
        cartRepository.save(cartEntityMapper.toCartEntity(cart));
    }

    @Override
    public List<Object[]> findCartByUserId(long userId) {
        List<Object[]> results = cartRepository.findByUserId(userId);
        if(results.isEmpty()) return results;
        return results;
    }

    @Override
    public Optional<Cart> findById(long cartId) {
        return cartRepository.findById(cartId)
                .map(cartEntityMapper::toCart);
    }
}
