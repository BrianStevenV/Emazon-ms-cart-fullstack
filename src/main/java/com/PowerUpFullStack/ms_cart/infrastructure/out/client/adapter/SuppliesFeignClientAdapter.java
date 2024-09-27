package com.PowerUpFullStack.ms_cart.infrastructure.out.client.adapter;

import com.PowerUpFullStack.ms_cart.domain.spi.ISuppliesFeignClientPort;
import com.PowerUpFullStack.ms_cart.infrastructure.out.client.feing.port.ISuppliesFeignClientExternalPort;
import com.PowerUpFullStack.ms_cart.infrastructure.out.client.mapper.ISuppliesFeignClientMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SuppliesFeignClientAdapter implements ISuppliesFeignClientPort {
    private final ISuppliesFeignClientExternalPort suppliesFeignClientExternalPort;
    private final ISuppliesFeignClientMapper suppliesFeignClientMapper;

    @Override
    public String getNextDateSupply(long productId) {
        return suppliesFeignClientExternalPort.getNextDateSupply(productId);
    }
}
