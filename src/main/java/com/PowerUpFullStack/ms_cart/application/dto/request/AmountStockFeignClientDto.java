package com.PowerUpFullStack.ms_cart.application.dto.request;

public record AmountStockFeignClientDto(
        Long productId,
        Integer amount
) {
}
