package com.PowerUpFullStack.ms_cart.domain.model;

import java.util.List;

public class CartDetailAndProduct {
    long id;
    String name;
    Integer amountInStock;
    Double price;
    Integer amountInCart;
    String brandName;
    List<String> categoryNames;
    String nextSupplDate;
    long cartDetailsId;

    public CartDetailAndProduct(long id, String name, Integer amountInStock, Double price, Integer amountInCart, String brandName, List<String> categoryNames, long cartDetailsId) {
        this.id = id;
        this.name = name;
        this.amountInStock = amountInStock;
        this.price = price;
        this.amountInCart = amountInCart;
        this.brandName = brandName;
        this.categoryNames = categoryNames;
        this.cartDetailsId = cartDetailsId;
    }

    public CartDetailAndProduct(String nextSupplDate){
        this.nextSupplDate = nextSupplDate;
    }

    public CartDetailAndProduct() {};

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmountInStock() {
        return amountInStock;
    }

    public void setAmountInStock(Integer amountInStock) {
        this.amountInStock = amountInStock;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getAmountInCart() {
        return amountInCart;
    }

    public void setAmountInCart(Integer amountInCart) {
        this.amountInCart = amountInCart;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public List<String> getCategoryNames() {
        return categoryNames;
    }

    public void setCategoryNames(List<String> categoryNames) {
        this.categoryNames = categoryNames;
    }

    public String getNextSupplDate() {
        return nextSupplDate;
    }

    public void setNextSupplDate(String nextSupplDate) {
        this.nextSupplDate = nextSupplDate;
    }

    public long getCartDetailsId() {
        return cartDetailsId;
    }

    public void setCartDetailsId(long cartDetailsId) {
        this.cartDetailsId = cartDetailsId;
    }
}
