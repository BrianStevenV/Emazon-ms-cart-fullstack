package com.PowerUpFullStack.ms_cart.application.dto.response;

import java.util.List;

public record PaginationResponseDto<T>(
        List<T> content,
        double subtotal,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages,
        boolean isFirst,
        boolean isLast
) {
}
