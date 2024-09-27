package com.PowerUpFullStack.ms_cart.domain.model;

import java.time.LocalDateTime;

public class CartDetails {
    private Long id;
    private Integer amount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean active; // Cambiado a 'active'
    private Long productId;
    private Cart cart;

    public CartDetails(Long id, Integer amount, LocalDateTime createdAt, LocalDateTime updatedAt, Boolean active, Long productId, Cart cart) {
        this.id = id;
        this.amount = amount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.active = active;
        this.productId = productId;
        this.cart = cart;
    }

    public CartDetails() {}

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getAmount() { return amount; }
    public void setAmount(Integer amount) { this.amount = amount; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Boolean getActive() { return active; }  // Cambiado a 'getActive'
    public void setActive(Boolean active) { this.active = active; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Cart getCart() { return cart; }
    public void setCart(Cart cartId) { this.cart= cartId; }
}
