package com.PowerUpFullStack.ms_cart.domain.exception;

public class CartDetailNotAvailableInCartException extends RuntimeException{
    public CartDetailNotAvailableInCartException(String message) {
        super(message);
    }
}
