package com.PowerUpFullStack.ms_cart.domain.spi;

import com.PowerUpFullStack.ms_cart.domain.model.Cart;
import com.PowerUpFullStack.ms_cart.domain.model.CustomPage;

import java.util.List;
import java.util.Optional;

public interface ICartPersistencePort {
    void saveCart(Cart cart);
    Optional<Cart> findCartEntity(long userId);
    CustomPage<Cart> getPaginationCart();
}
