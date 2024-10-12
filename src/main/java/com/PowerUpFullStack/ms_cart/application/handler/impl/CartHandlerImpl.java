package com.PowerUpFullStack.ms_cart.application.handler.impl;

import com.PowerUpFullStack.ms_cart.application.dto.request.AddProductToCartRequestDto;
import com.PowerUpFullStack.ms_cart.application.dto.request.CartDetailsRequestDto;
import com.PowerUpFullStack.ms_cart.application.dto.request.FilterByRequestDto;
import com.PowerUpFullStack.ms_cart.application.dto.request.OperationTypeRequestDto;
import com.PowerUpFullStack.ms_cart.application.dto.request.SortDirectionRequestDto;
import com.PowerUpFullStack.ms_cart.application.dto.response.CartResponseDto;
import com.PowerUpFullStack.ms_cart.application.dto.response.PaginationResponseDto;
import com.PowerUpFullStack.ms_cart.application.dto.response.ProductResponseDto;
import com.PowerUpFullStack.ms_cart.application.handler.ICartHandler;
import com.PowerUpFullStack.ms_cart.application.mapper.ICartDetailsRequestMapper;
import com.PowerUpFullStack.ms_cart.application.mapper.ICartRequestMapper;
import com.PowerUpFullStack.ms_cart.application.mapper.ICartResponseMapper;
import com.PowerUpFullStack.ms_cart.domain.api.ICartServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartHandlerImpl implements ICartHandler {
    private final ICartServicePort cartServicePort;
    private final ICartRequestMapper cartRequestMapper;
    private final ICartResponseMapper cartResponseMapper;
    private final ICartDetailsRequestMapper cartDetailsRequestMapper;


    @Override
    public void addProductToCart(AddProductToCartRequestDto addProductToCartRequestDto) {
        cartServicePort.addProductToCart(
                cartDetailsRequestMapper.toCartDetails(addProductToCartRequestDto.cartDetailsRequestDto()),
                cartRequestMapper.toOperationType(addProductToCartRequestDto.operationTypeRequestDto()));
    }

    @Override
    public void removeProductFromCart(long productId) {
        cartServicePort.removeProductFromCart(productId);
    }

    @Override
    public PaginationResponseDto<ProductResponseDto> getPaginationCartByAscAndDescByProductNameAndBrandNameAndCategoryName(SortDirectionRequestDto sortDirectionRequestDto, FilterByRequestDto filterByRequestDto) {
        return cartResponseMapper.toPaginationResponseDtoFromProductResponseDto(
                cartServicePort.getPaginationCartByAscAndDescByProductNameAndBrandNameAndCategoryName(
                        cartRequestMapper.toSortDirection(sortDirectionRequestDto),
                        cartRequestMapper.toFilterBy(filterByRequestDto)));
    }
}
