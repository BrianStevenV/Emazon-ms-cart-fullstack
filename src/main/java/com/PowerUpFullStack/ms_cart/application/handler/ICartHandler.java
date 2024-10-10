package com.PowerUpFullStack.ms_cart.application.handler;

import com.PowerUpFullStack.ms_cart.application.dto.request.AddProductToCartRequestDto;
import com.PowerUpFullStack.ms_cart.application.dto.request.CartDetailsRequestDto;
import com.PowerUpFullStack.ms_cart.application.dto.request.FilterByRequestDto;
import com.PowerUpFullStack.ms_cart.application.dto.request.OperationTypeRequestDto;
import com.PowerUpFullStack.ms_cart.application.dto.request.SortDirectionRequestDto;
import com.PowerUpFullStack.ms_cart.application.dto.response.CartResponseDto;
import com.PowerUpFullStack.ms_cart.application.dto.response.PaginationResponseDto;

public interface ICartHandler {
    void addProductToCart(AddProductToCartRequestDto addProductToCartRequestDto);

    void removeProductFromCart(long productId);

    PaginationResponseDto<CartResponseDto>
    getPaginationCartByAscAndDescByProductNameAndBrandNameAndCategoryName
            (SortDirectionRequestDto sortDirectionRequestDto, FilterByRequestDto filterByRequestDto);
}
