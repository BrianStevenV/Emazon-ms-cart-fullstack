package com.PowerUpFullStack.ms_cart.domain.api;

import com.PowerUpFullStack.ms_cart.domain.model.Cart;
import com.PowerUpFullStack.ms_cart.domain.model.CartDetails;
import com.PowerUpFullStack.ms_cart.domain.model.CustomPage;
import com.PowerUpFullStack.ms_cart.domain.model.FilterBy;
import com.PowerUpFullStack.ms_cart.domain.model.OperationType;
import com.PowerUpFullStack.ms_cart.domain.model.SortDirection;

public interface ICartServicePort {
    void addProductToCart(CartDetails cart, OperationType operationType);
    void removeProductFromCart(long productId);
    CustomPage<Cart> getPaginationCartByAscAndDescByProductNameAndBrandNameAndCategoryName(SortDirection sortDirection, FilterBy filterBy);
}
