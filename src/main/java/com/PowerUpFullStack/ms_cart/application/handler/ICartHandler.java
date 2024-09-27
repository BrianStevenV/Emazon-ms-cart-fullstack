package com.PowerUpFullStack.ms_cart.application.handler;

import com.PowerUpFullStack.ms_cart.application.dto.request.CartDetailsRequestDto;
import com.PowerUpFullStack.ms_cart.application.dto.response.CartResponseDto;

public interface ICartHandler {
    CartResponseDto addProductToCart(CartDetailsRequestDto cartRequest);
}
