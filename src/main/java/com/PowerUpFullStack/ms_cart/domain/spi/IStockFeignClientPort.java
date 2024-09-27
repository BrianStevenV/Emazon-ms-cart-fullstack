package com.PowerUpFullStack.ms_cart.domain.spi;

import com.PowerUpFullStack.ms_cart.domain.model.AllCategories;
import com.PowerUpFullStack.ms_cart.domain.model.AmountStock;
import com.PowerUpFullStack.ms_cart.domain.model.Available;
import com.PowerUpFullStack.ms_cart.domain.model.ProductCategory;
import com.PowerUpFullStack.ms_cart.domain.model.ProductIds;


public interface IStockFeignClientPort {
    ProductCategory findCategoryByProductId(long productId);
    Available amountStockAvailable(AmountStock amountStock);
    AllCategories getCategoriesNames(ProductIds productIds);
}
