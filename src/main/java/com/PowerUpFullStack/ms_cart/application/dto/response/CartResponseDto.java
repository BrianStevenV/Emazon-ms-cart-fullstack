package com.PowerUpFullStack.ms_cart.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class CartResponseDto {
    private String name;
    private Double price;
    private Integer amount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long productId;
    private Long userId;
}
