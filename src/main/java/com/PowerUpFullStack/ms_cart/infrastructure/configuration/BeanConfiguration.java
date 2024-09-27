package com.PowerUpFullStack.ms_cart.infrastructure.configuration;

import com.PowerUpFullStack.ms_cart.domain.api.ICartServicePort;
import com.PowerUpFullStack.ms_cart.domain.spi.ICartDetailsPersistencePort;
import com.PowerUpFullStack.ms_cart.domain.spi.ICartPersistencePort;
import com.PowerUpFullStack.ms_cart.domain.spi.IStockFeignClientPort;
import com.PowerUpFullStack.ms_cart.domain.spi.ISuppliesFeignClientPort;
import com.PowerUpFullStack.ms_cart.domain.usecase.CartUseCase;
import com.PowerUpFullStack.ms_cart.domain.usecase.utils.CartUseCaseUtils;
import com.PowerUpFullStack.ms_cart.infrastructure.out.client.adapter.StockFeignClientAdapter;
import com.PowerUpFullStack.ms_cart.infrastructure.out.client.adapter.SuppliesFeignClientAdapter;
import com.PowerUpFullStack.ms_cart.infrastructure.out.client.feing.port.IStockFeignClientExternalPort;
import com.PowerUpFullStack.ms_cart.infrastructure.out.client.feing.port.ISuppliesFeignClientExternalPort;
import com.PowerUpFullStack.ms_cart.infrastructure.out.client.mapper.IStockFeignClientMapper;
import com.PowerUpFullStack.ms_cart.infrastructure.out.client.mapper.ISuppliesFeignClientMapper;
import com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.adapters.CartDetailsEntityMySqlAdapter;
import com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.adapters.CartMySqlAdapter;
import com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.mapper.ICartDetailsEntityMapper;
import com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.mapper.ICartEntityMapper;
import com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.repositories.ICartDetailsRepository;
import com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.repositories.ICartRepository;
import com.PowerUpFullStack.ms_cart.infrastructure.security.IAuthContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final ICartRepository cartRepository;
    private final ICartEntityMapper cartEntityMapper;

    private final ICartDetailsRepository cartDetailsRepository;
    private final ICartDetailsEntityMapper cartDetailsEntityMapper;

    private final IStockFeignClientExternalPort stockFeignClientExternalPort;
    private final IStockFeignClientMapper stockFeignClientMapper;

    private final ISuppliesFeignClientExternalPort suppliesFeignClientExternalPort;
    private final ISuppliesFeignClientMapper suppliesFeignClientMapper;

    private final IAuthContext authContext;

    @Bean
    public ICartPersistencePort cartPersistencePort() {
        return new CartMySqlAdapter(cartRepository, cartEntityMapper);
    }

    @Bean
    public ICartDetailsPersistencePort cartDetailsPersistencePort() {
        return new CartDetailsEntityMySqlAdapter(cartDetailsRepository, cartDetailsEntityMapper);
    }

    @Bean
    public IStockFeignClientPort stockFeignClientPort() {
        return new StockFeignClientAdapter(stockFeignClientExternalPort, stockFeignClientMapper);
    }

    @Bean
    public ISuppliesFeignClientPort suppliesFeignClientPort() {
        return new SuppliesFeignClientAdapter(suppliesFeignClientExternalPort, suppliesFeignClientMapper);
    }

    @Bean
    public ICartServicePort cartServicePort() {
        return new CartUseCase(cartPersistencePort(), cartDetailsPersistencePort(), stockFeignClientPort(), suppliesFeignClientPort(), cartUseCaseUtils());
    }

    @Bean
    public CartUseCaseUtils cartUseCaseUtils() {
        return new CartUseCaseUtils(authContext);
    }
}
