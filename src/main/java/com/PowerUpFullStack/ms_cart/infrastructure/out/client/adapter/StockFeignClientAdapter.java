package com.PowerUpFullStack.ms_cart.infrastructure.out.client.adapter;

import com.PowerUpFullStack.ms_cart.domain.model.AllCategories;
import com.PowerUpFullStack.ms_cart.domain.model.AmountStock;
import com.PowerUpFullStack.ms_cart.domain.model.Available;
import com.PowerUpFullStack.ms_cart.domain.model.Product;
import com.PowerUpFullStack.ms_cart.domain.model.ProductCategory;
import com.PowerUpFullStack.ms_cart.domain.model.ProductIds;
import com.PowerUpFullStack.ms_cart.domain.spi.IStockFeignClientPort;
import com.PowerUpFullStack.ms_cart.infrastructure.out.client.feing.port.IStockFeignClientExternalPort;
import com.PowerUpFullStack.ms_cart.infrastructure.out.client.mapper.IStockFeignClientMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class StockFeignClientAdapter implements IStockFeignClientPort {
    private final IStockFeignClientExternalPort stockFeignClientExternalPort;
    private final IStockFeignClientMapper stockFeignClientMapper;

    @Override
    public ProductCategory findCategoryByProductId(long productId) {
        return stockFeignClientMapper.toProductCategory(stockFeignClientExternalPort.getCategoryFromProductById(productId));
    }

    @Override
    public Available amountStockAvailable(AmountStock amountStock) {
        return stockFeignClientMapper.toAvailable(stockFeignClientExternalPort.getAmountStockAvailable(stockFeignClientMapper.toAmountStockFeignClientDto(amountStock)));
    }

    @Override
    public AllCategories getCategoriesNames(ProductIds productIds) {
        return stockFeignClientMapper.toAllCategories(stockFeignClientExternalPort.getAllCategories(stockFeignClientMapper.toProductIdsFeignClientDto(productIds)));
    }

    @Override
    public List<Product> getProductsByProductsIds(ProductIds productIds) {
        return stockFeignClientMapper.toProductList(stockFeignClientExternalPort.getProductsByProductsIds(stockFeignClientMapper.toProductIdsFeignClientDto(productIds)));
    }
}
