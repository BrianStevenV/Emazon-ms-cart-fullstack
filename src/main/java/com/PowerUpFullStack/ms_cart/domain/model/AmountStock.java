package com.PowerUpFullStack.ms_cart.domain.model;

public class AmountStock {
    private Long productId;
    private Integer amount;

    public AmountStock() {
    }

    public AmountStock(Long productId, Integer amount) {
        this.productId = productId;
        this.amount = amount;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
