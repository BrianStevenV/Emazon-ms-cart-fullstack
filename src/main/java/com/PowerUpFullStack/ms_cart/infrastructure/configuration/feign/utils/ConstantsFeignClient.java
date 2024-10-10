package com.PowerUpFullStack.ms_cart.infrastructure.configuration.feign.utils;

public class ConstantsFeignClient {
    private ConstantsFeignClient() { throw new IllegalStateException("Utility class"); }

    public static final int STATUS_CODE_400 = 400;
    public static final int STATUS_CODE_401 = 401;
    public static final int STATUS_CODE_403 = 403;
    public static final int STATUS_CODE_404 = 404;
    public static final int STATUS_CODE_500 = 500;

    public static final String STOCK_SERVICE = "stock-service";
    public static final String STOCK_BASE_HOST = "http://localhost:8081";
    public static final String STOCK_GET_PRODUCT_BY_ID = "/{productId}";
    public static final String STOCK_GET_PRODUCT_BY_ID_PATH_VARIABLE = "productId";
    public static final String STOCK_POST_CATEGORIES_BY_PRODUCTS_IDS = "/product/categories";
    public static final String STOCK_POST_AMOUNT_STOCK_AVAILABLE = "/product/stock/available";

    public static final String SUPPLY_SERVICE = "supply-service";
    public static final String SUPPLY_BASE_HOST = "http://localhost:8083";
    public static final String SUPPLY_GET_NEXT_DATE_SUPPLY = "/api/v1/supplies/next-date-supply/{productId}";
    public static final String SUPPLY_GET_NEXT_DATE_SUPPLY_PATH_VARIABLE_PRODUCT_ID = "productId";
}
