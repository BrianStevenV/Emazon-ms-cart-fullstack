package com.PowerUpFullStack.ms_cart.application.dto.request;

import jakarta.validation.constraints.NotNull;

public record CartDetailsRequestDto(
        @NotNull
       Integer amount,
       @NotNull
       Long productId
       ) {
}
