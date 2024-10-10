package com.PowerUpFullStack.ms_cart.application.dto.response;

import com.PowerUpFullStack.ms_cart.domain.model.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartDetailsResponseDto {
    private Long id;
    private Integer amount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean active; // Cambiado a 'active'
    private Long productId;
    private Cart cart;
}
