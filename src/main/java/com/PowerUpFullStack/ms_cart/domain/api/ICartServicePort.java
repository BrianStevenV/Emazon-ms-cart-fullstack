package com.PowerUpFullStack.ms_cart.domain.api;

import com.PowerUpFullStack.ms_cart.domain.model.Cart;
import com.PowerUpFullStack.ms_cart.domain.model.CartDetails;

public interface ICartServicePort {
    Cart addProductToCart(CartDetails cart);
}
