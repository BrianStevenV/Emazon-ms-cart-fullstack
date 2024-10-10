package com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.entities.utils;

public class ConstantsEntity {
    private ConstantsEntity() { throw new IllegalArgumentException("Utility class"); }

    public static final String CART_TABLE_NAME = "cart";
    public static final String CART_DETAILS_TABLE_NAME = "cart_details";


    public static final String CART_COLUMN_AMOUNT = "amount";
    public static final String CART_COLUMN_CREATED_AT = "created_at";
    public static final String CART_COLUMN_UPDATED_AT = "updated_at";
    public static final String CART_COLUMN_IS_ACTIVE = "is_active";
    public static final String CART_COLUMN_PRODUCT_ID = "product_id";
    public static final String CART_COLUMN_USER_ID = "user_id";
    public static final String CART_COLUMN_CART_ID = "cart_id";

    public static final String CART_DETAILS_ONE_TO_MANY_MAPPED_BY = "cartId";

    public static final String COLUMN_DEFINITION_BOOLEAN_DEFAULT_TRUE = "boolean default true";
}
