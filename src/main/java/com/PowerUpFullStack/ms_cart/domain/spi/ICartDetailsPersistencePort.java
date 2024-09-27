package com.PowerUpFullStack.ms_cart.domain.spi;

import com.PowerUpFullStack.ms_cart.domain.model.CartDetails;



public interface ICartDetailsPersistencePort {
    void saveCartDetails(CartDetails cartDetails);
    CartDetails findByCartId(long cartId);

}
