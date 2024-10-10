package com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.repositories.utils;

public class ConstantsRepository {

    private ConstantsRepository() { throw new IllegalArgumentException("utility class."); }

    // Cart Repository
    public static final String QUERY_FIND_CART_ENTITY = "SELECT * FROM cart WHERE user_id = :userId";
    public static final String PARAM_FIND_CART_ENTITY = "userId";


    // Cart Details Repository

    public static final String QUERY_DISABLE_CART_DETAIL_BY_CART_ID_AND_PRODUCT_ID = "UPDATE CartDetailsEntity c SET c.isActive = false where c.cartId = :cartId AND c.productId = :productId";
    public static final String QUERY_ENABLE_CART_DETAIL_BY_CART_ID_AND_PRODUCT_ID = "UPDATE CartDetailsEntity c SET c.isActive = true where c.cartId = :cartId AND c.productId = :productId";
}
