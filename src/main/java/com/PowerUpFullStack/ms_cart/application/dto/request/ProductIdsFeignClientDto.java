package com.PowerUpFullStack.ms_cart.application.dto.request;

import java.util.List;

public record ProductIdsFeignClientDto(
        List<Long> productIds
) {
}
