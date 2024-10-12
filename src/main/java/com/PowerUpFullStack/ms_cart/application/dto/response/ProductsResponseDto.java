package com.PowerUpFullStack.ms_cart.application.dto.response;

import java.util.List;

public record ProductsResponseDto(
        long id,
        String name,
        int amount,
        double price,
        String brandName,
        List<String> categoryNames
) {
}
