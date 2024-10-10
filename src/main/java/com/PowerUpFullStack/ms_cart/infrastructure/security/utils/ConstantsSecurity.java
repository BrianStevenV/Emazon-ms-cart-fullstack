package com.PowerUpFullStack.ms_cart.infrastructure.security.utils;

public class ConstantsSecurity {
    private ConstantsSecurity() { throw new IllegalStateException("Utility class"); }

    public static final String ADMINISTRATOR_ROLE = "ADMINISTRATOR_ROLE";
    public static final String WAREHOUSE_ASSISTANT_ROLE = "WAREHOUSE_ASSISTANT_ROLE";
    public static final String CUSTOMER_ROLE = "CUSTOMER_ROLE";

    // Cart Controller

    public static final String CART_CONTROLLER_POST_ADD_PRODUCT_CART = "/cart/add";
    public static final String CART_CONTROLLER_POST_REMOVE_PRODUCT_CART = "/cart/remove";
    //Swagger

    public static final String SWAGGER_UI_HTML = "/swagger-ui.html";
    public static final String SWAGGER_UI = "/swagger-ui/**";
    public static final String V3_API_DOCS = "/v3/api-docs/**";

    // Security

    public static final String ERROR_EXTRACTING_ID_FROM_INFRASTRUCTURE_CONTEXT = "Error to get ID from infrastructure context: ";

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_TOKEN = "Bearer ";
    public static final int AUTHORIZATION_HEADER_SUBSTRING = 7;

    public static final String DELIMETER_JOINING_AUTH_GET_AUTHORITIES = ", ";

    public static final int PREFIX_RECURSIVE = 0;
    public static final int PREFIX_RECURSIVE_NEXT = 1;
}
