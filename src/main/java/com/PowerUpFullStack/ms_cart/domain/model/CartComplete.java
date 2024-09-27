package com.PowerUpFullStack.ms_cart.domain.model;

import java.sql.Timestamp;

public class CartComplete {
    Long cartId;
    Long userIdFromCart;
    Long cartDetailId;
    Integer amount;
    Timestamp createdAt;
    Timestamp updatedAt;
    Boolean isActive;
    Long productId;

    public CartComplete(Long cartId, Long userIdFromCart, Long cartDetailId, Integer amount, Timestamp createdAt, Timestamp updatedAt, Boolean isActive, Long productId) {
        this.cartId = cartId;
        this.userIdFromCart = userIdFromCart;
        this.cartDetailId = cartDetailId;
        this.amount = amount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isActive = isActive;
        this.productId = productId;
    }

    public CartComplete() {
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getUserIdFromCart() {
        return userIdFromCart;
    }

    public void setUserIdFromCart(Long userIdFromCart) {
        this.userIdFromCart = userIdFromCart;
    }

    public Long getCartDetailId() {
        return cartDetailId;
    }

    public void setCartDetailId(Long cartDetailId) {
        this.cartDetailId = cartDetailId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
