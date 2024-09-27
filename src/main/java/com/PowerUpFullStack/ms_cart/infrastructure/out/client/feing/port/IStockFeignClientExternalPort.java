package com.PowerUpFullStack.ms_cart.infrastructure.out.client.feing.port;

import com.PowerUpFullStack.ms_cart.application.dto.request.AmountStockFeignClientDto;
import com.PowerUpFullStack.ms_cart.application.dto.request.ProductIdsFeignClientDto;
import com.PowerUpFullStack.ms_cart.application.dto.response.AllCategoriesResponseDto;
import com.PowerUpFullStack.ms_cart.application.dto.response.AmountStockAvailableResponseDto;
import com.PowerUpFullStack.ms_cart.application.dto.response.ProductCategoryResponseDto;
import com.PowerUpFullStack.ms_cart.infrastructure.configuration.feign.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.PowerUpFullStack.ms_cart.infrastructure.configuration.feign.utils.ConstantsFeignClient.STOCK_BASE_HOST;
import static com.PowerUpFullStack.ms_cart.infrastructure.configuration.feign.utils.ConstantsFeignClient.STOCK_GET_PRODUCT_BY_ID;
import static com.PowerUpFullStack.ms_cart.infrastructure.configuration.feign.utils.ConstantsFeignClient.STOCK_GET_PRODUCT_BY_ID_PATH_VARIABLE;
import static com.PowerUpFullStack.ms_cart.infrastructure.configuration.feign.utils.ConstantsFeignClient.STOCK_POST_AMOUNT_STOCK_AVAILABLE;
import static com.PowerUpFullStack.ms_cart.infrastructure.configuration.feign.utils.ConstantsFeignClient.STOCK_POST_CATEGORIES_BY_PRODUCTS_IDS;
import static com.PowerUpFullStack.ms_cart.infrastructure.configuration.feign.utils.ConstantsFeignClient.STOCK_SERVICE;

@FeignClient(name = STOCK_SERVICE, url = STOCK_BASE_HOST, configuration = FeignClientConfig.class)
public interface IStockFeignClientExternalPort {

    @PostMapping(STOCK_POST_AMOUNT_STOCK_AVAILABLE)
    AmountStockAvailableResponseDto getAmountStockAvailable(@RequestBody AmountStockFeignClientDto amountStockFeignClientDto);

    @GetMapping(STOCK_GET_PRODUCT_BY_ID)
    ProductCategoryResponseDto getCategoryFromProductById(@PathVariable(STOCK_GET_PRODUCT_BY_ID_PATH_VARIABLE) long productId);

    @PostMapping(STOCK_POST_CATEGORIES_BY_PRODUCTS_IDS)
    AllCategoriesResponseDto getAllCategories(@RequestBody ProductIdsFeignClientDto productIdsRequestDto);
}
