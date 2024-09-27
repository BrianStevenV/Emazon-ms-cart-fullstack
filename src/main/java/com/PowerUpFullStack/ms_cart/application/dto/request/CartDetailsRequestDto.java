package com.PowerUpFullStack.ms_cart.application.dto.request;

public record CartDetailsRequestDto(
       Integer amount,
       Long productId
       ) {
}
