package com.PowerUpFullStack.ms_cart.application.dto.response;

import java.util.List;

public record ProductCategoryResponseDto(
        List<Long> categories
) {
}
