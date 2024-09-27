package com.PowerUpFullStack.ms_cart.infrastructure.input.rest;

public class ConstantsCartController {
    private ConstantsCartController() {
        throw new IllegalStateException("Utility class");
    }

    public static final String CART_REST_CONTROLLER_BASE_PATH = "/cart";
    public static final String CART_REST_CONTROLLER_POST_ADD_PRODUCT_TO_CART = "/add";

}
