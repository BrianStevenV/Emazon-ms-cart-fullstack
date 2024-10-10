package com.PowerUpFullStack.ms_cart.domain.spi;

import com.PowerUpFullStack.ms_cart.domain.model.CartDetails;

import java.util.List;
import java.util.Optional;


public interface ICartDetailsPersistencePort {
    void saveCartDetails(CartDetails cartDetails);
    List<CartDetails> findByCartIdListCartDetails(long cartId);
    Optional<CartDetails> findByCartIdAndProductId(long cartId, long productId);
    void deleteCartDetailsByCartIdAndProductId(long cartId, long productId);
    void enableCartDetailByCartIdAndProductId(long cartId, long productId);
}
