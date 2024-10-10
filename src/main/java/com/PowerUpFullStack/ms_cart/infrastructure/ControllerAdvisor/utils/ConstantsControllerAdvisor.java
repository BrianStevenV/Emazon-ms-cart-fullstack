package com.PowerUpFullStack.ms_cart.infrastructure.ControllerAdvisor.utils;

public class ConstantsControllerAdvisor {

    private ConstantsControllerAdvisor() {
        throw new IllegalStateException("Utility class");
    }

    public static final String OPERATION_TYPE_NOT_PERMISSION_EXCEPTION_MESSAGE = "Operation type not allowed";
    public static final String PRODUCT_NOT_FOUND_IN_DATABASE_EXCEPTION_MESSAGE = "Product not found in cart";
    public static final String OBJECT_METHOD_UPDATE_TIMESTAMP_EXCEPTION_MESSAGE = "The provided object does not have setUpdatedAt method";
    public static final String OBJECT_HAS_NOT_METHOD_EXCEPTION_MESSAGE = "The provided object does not have setCreatedAt or setUpdatedAt methods";
    public static final String CART_NOT_FOUND_EXCEPTION_MESSAGE = "Cart not found";
    public static final String PRODUCT_CATEGORY_INVALID_EXCEPTION_MESSAGE = "Product category invalid, There are more than 3 repeated categories in the cart";

    // General Error
    public static final String RESPONSE_ERROR_MESSAGE = "Error:";
    public static final String WRONG_CREDENTIALS_MESSAGE = "Wrong credentials or role not allowed";


    // Feign Client

    public static final String FEIGN_CLIENT_BAD_REQUEST_EXCEPTION_MESSAGE = "The request could not be completed due to a problem with the stock service.";
    public static final String FEIGN_CLIENT_NOT_FOUND_EXCEPTION_MESSAGE = "The request could not be completed due to a problem with the stock service.";
    public static final String FEIGN_CLIENT_FORBIDDEN_EXCEPTION_MESSAGE = "The request could not be completed due to a problem with the stock service.";
    public static final String FEIGN_CLIENT_INTERNAL_SERVER_ERROR_EXCEPTION_MESSAGE = "The request could not be completed due to a problem with the stock service.";
    public static final String FEIGN_CLIENT_UNAUTHORIZED_EXCEPTION_MESSAGE = "The request could not be completed due to a problem with the stock service.";
}
