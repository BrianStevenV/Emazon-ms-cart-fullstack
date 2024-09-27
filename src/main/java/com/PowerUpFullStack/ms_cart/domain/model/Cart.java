package com.PowerUpFullStack.ms_cart.domain.model;

import java.util.List;

public class Cart {
    private Long id;
    private Long userId;
    private List<CartDetails> cartDetails;

    public Cart(Long id, Long userId, List<CartDetails> cartDetails) {
        this.id = id;
        this.userId = userId;
        this.cartDetails = cartDetails;
    }

    public Cart(Long id){
        this.id = id;
    }

    public Cart() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<CartDetails> getCartDetails() {
        return cartDetails;
    }

    public void setCartDetails(List<CartDetails> cartDetails) {
        this.cartDetails = cartDetails;
    }
}
