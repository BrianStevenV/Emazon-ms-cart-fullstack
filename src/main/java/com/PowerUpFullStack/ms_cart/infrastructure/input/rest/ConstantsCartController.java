package com.PowerUpFullStack.ms_cart.infrastructure.input.rest;

public class ConstantsCartController {
    private ConstantsCartController() {
        throw new IllegalStateException("Utility class");
    }

    public static final String CART_REST_CONTROLLER_BASE_PATH = "/cart";
    public static final String CART_REST_CONTROLLER_POST_ADD_PRODUCT_TO_CART = "/add";
    public static final String CART_REST_CONTROLLER_POST_REMOVE_PRODUCT_TO_CART = "/remove/{productId}";
    public static final String CART_REST_CONTROLLER_GET_PAGINATION_CART = "/pagination/";

    public static final String CART_REST_CONTROLLER_PATH_VARIABLE_PRODUCT_ID = "productId";

}
