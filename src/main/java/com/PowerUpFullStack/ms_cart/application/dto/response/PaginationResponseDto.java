package com.PowerUpFullStack.ms_cart.application.dto.response;

import java.util.List;

public record PaginationResponseDto<T>(
        List<T> content,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages,
        boolean isFirst,
        boolean isLast
) {
}
