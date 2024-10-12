package com.PowerUpFullStack.ms_cart.infrastructure.out.client.mapper;

import com.PowerUpFullStack.ms_cart.application.dto.request.AmountStockFeignClientDto;
import com.PowerUpFullStack.ms_cart.application.dto.request.ProductIdsFeignClientDto;
import com.PowerUpFullStack.ms_cart.application.dto.response.AllCategoriesResponseDto;
import com.PowerUpFullStack.ms_cart.application.dto.response.AmountStockAvailableResponseDto;
import com.PowerUpFullStack.ms_cart.application.dto.response.ProductCategoryResponseDto;
import com.PowerUpFullStack.ms_cart.application.dto.response.ProductsResponseDto;
import com.PowerUpFullStack.ms_cart.domain.model.AllCategories;
import com.PowerUpFullStack.ms_cart.domain.model.AmountStock;
import com.PowerUpFullStack.ms_cart.domain.model.Available;
import com.PowerUpFullStack.ms_cart.domain.model.Product;
import com.PowerUpFullStack.ms_cart.domain.model.ProductCategory;
import com.PowerUpFullStack.ms_cart.domain.model.ProductIds;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IStockFeignClientMapper {
    AllCategories toAllCategories(AllCategoriesResponseDto allCategoriesResponseDto);
    ProductIds toProductIds(ProductIdsFeignClientDto productIdsFeignClientDto);

    AllCategoriesResponseDto toAllCategoriesResponseDto(AllCategories allCategories);
    ProductIdsFeignClientDto toProductIdsFeignClientDto(ProductIds productIds);

    AmountStockFeignClientDto toAmountStockFeignClientDto(AmountStock amountStock);

    ProductCategory toProductCategory(ProductCategoryResponseDto productCategoryResponseDto);
    Available toAvailable(AmountStockAvailableResponseDto amountStockAvailableResponseDto);

    List<Product> toProductList(List<ProductsResponseDto> productsResponseDto);


}
