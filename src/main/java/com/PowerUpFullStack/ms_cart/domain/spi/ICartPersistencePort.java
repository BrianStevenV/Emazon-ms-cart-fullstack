package com.PowerUpFullStack.ms_cart.domain.spi;

import com.PowerUpFullStack.ms_cart.domain.model.Cart;

import java.util.List;
import java.util.Optional;

public interface ICartPersistencePort {
    void saveCart(Cart cart);
    List<Object[]> findCartByUserId(long userId);
    Optional<Cart> findById(long cartId);
}
