package com.PowerUpFullStack.ms_cart.application.dto.request;

import jakarta.validation.constraints.NotNull;

public record AddProductToCartRequestDto(
        @NotNull
        CartDetailsRequestDto cartDetailsRequestDto,
        OperationTypeRequestDto operationTypeRequestDto
) {
}
