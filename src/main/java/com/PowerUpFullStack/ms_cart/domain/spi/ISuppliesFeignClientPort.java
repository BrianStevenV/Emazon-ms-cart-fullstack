package com.PowerUpFullStack.ms_cart.domain.spi;

public interface ISuppliesFeignClientPort {
    String getNextDateSupply(long productId);
}
