package com.PowerUpFullStack.ms_cart.application.handler.impl;

import com.PowerUpFullStack.ms_cart.application.dto.request.CartDetailsRequestDto;
import com.PowerUpFullStack.ms_cart.application.dto.response.CartResponseDto;
import com.PowerUpFullStack.ms_cart.application.handler.ICartHandler;
import com.PowerUpFullStack.ms_cart.application.mapper.ICartDetailsRequestMapper;
import com.PowerUpFullStack.ms_cart.application.mapper.ICartRequestMapper;
import com.PowerUpFullStack.ms_cart.application.mapper.ICartResponseMapper;
import com.PowerUpFullStack.ms_cart.domain.api.ICartServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartHandlerImpl implements ICartHandler {
    private final ICartServicePort cartServicePort;
    private final ICartRequestMapper cartRequestMapper;
    private final ICartResponseMapper cartResponseMapper;

    private final ICartDetailsRequestMapper cartDetailsRequestMapper;


    @Override
    public CartResponseDto addProductToCart(CartDetailsRequestDto cartRequest) {
        return cartResponseMapper.toCartResponseDto(cartServicePort.addProductToCart(cartDetailsRequestMapper.toCartDetails(cartRequest)));
    }
}
