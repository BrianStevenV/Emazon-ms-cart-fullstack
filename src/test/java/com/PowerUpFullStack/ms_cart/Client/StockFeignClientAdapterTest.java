package com.PowerUpFullStack.ms_cart.Client;

import com.PowerUpFullStack.ms_cart.application.dto.request.AmountStockFeignClientDto;
import com.PowerUpFullStack.ms_cart.application.dto.request.ProductIdsFeignClientDto;
import com.PowerUpFullStack.ms_cart.application.dto.response.AllCategoriesResponseDto;
import com.PowerUpFullStack.ms_cart.application.dto.response.AmountStockAvailableResponseDto;
import com.PowerUpFullStack.ms_cart.application.dto.response.ProductCategoryResponseDto;
import com.PowerUpFullStack.ms_cart.domain.model.AllCategories;
import com.PowerUpFullStack.ms_cart.domain.model.AmountStock;
import com.PowerUpFullStack.ms_cart.domain.model.Available;
import com.PowerUpFullStack.ms_cart.domain.model.ProductCategory;
import com.PowerUpFullStack.ms_cart.domain.model.ProductIds;
import com.PowerUpFullStack.ms_cart.infrastructure.out.client.adapter.StockFeignClientAdapter;
import com.PowerUpFullStack.ms_cart.infrastructure.out.client.feing.port.IStockFeignClientExternalPort;
import com.PowerUpFullStack.ms_cart.infrastructure.out.client.mapper.IStockFeignClientMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class StockFeignClientAdapterTest {
    @Mock
    private IStockFeignClientExternalPort stockFeignClientExternalPort;

    @Mock
    private IStockFeignClientMapper stockFeignClientMapper;

    @InjectMocks
    private StockFeignClientAdapter stockFeignClientAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findCategoryByProductId_returnsProductCategory() {
        long productId = 1L;
        ProductCategory productCategory = new ProductCategory(List.of(1L, 2L));
        ProductCategoryResponseDto responseDto = new ProductCategoryResponseDto(List.of(1L,2L));
        when(stockFeignClientExternalPort.getCategoryFromProductById(productId)).thenReturn(responseDto);
        when(stockFeignClientMapper.toProductCategory(responseDto)).thenReturn(productCategory);

        ProductCategory result = stockFeignClientAdapter.findCategoryByProductId(productId);

        assertEquals(productCategory, result);
    }

    @Test
    void amountStockAvailable_returnsAvailable() {
        AmountStock amountStock = new AmountStock(1L, 10);
        Available available = new Available(true);
        AmountStockAvailableResponseDto responseDto = new AmountStockAvailableResponseDto();
        AmountStockFeignClientDto feignClientDto = new AmountStockFeignClientDto(1L, 15);
        when(stockFeignClientExternalPort.getAmountStockAvailable(any())).thenReturn(responseDto);
        when(stockFeignClientMapper.toAvailable(responseDto)).thenReturn(available);
        when(stockFeignClientMapper.toAmountStockFeignClientDto(amountStock)).thenReturn(feignClientDto);

        Available result = stockFeignClientAdapter.amountStockAvailable(amountStock);

        assertEquals(available, result);
    }

    @Test
    void getCategoriesNames_returnsAllCategories() {
        ProductIds productIds = new ProductIds(List.of(1L, 2L));
        AllCategories allCategories = new AllCategories(Map.of(1L, List.of(1L, 2L)));
        AllCategoriesResponseDto responseDto = new AllCategoriesResponseDto();
        ProductIdsFeignClientDto feignClientDto = new ProductIdsFeignClientDto(List.of(1L,2L));
        when(stockFeignClientExternalPort.getAllCategories(any())).thenReturn(responseDto);
        when(stockFeignClientMapper.toAllCategories(responseDto)).thenReturn(allCategories);
        when(stockFeignClientMapper.toProductIdsFeignClientDto(productIds)).thenReturn(feignClientDto);

        AllCategories result = stockFeignClientAdapter.getCategoriesNames(productIds);

        assertEquals(allCategories, result);
    }
}
