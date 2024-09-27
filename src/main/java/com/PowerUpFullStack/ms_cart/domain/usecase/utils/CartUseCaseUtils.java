package com.PowerUpFullStack.ms_cart.domain.usecase.utils;

import com.PowerUpFullStack.ms_cart.domain.model.CartDetails;
import com.PowerUpFullStack.ms_cart.infrastructure.security.IAuthContext;

import java.time.LocalDateTime;

public class CartUseCaseUtils {
    private final IAuthContext authContext;

    public CartUseCaseUtils(IAuthContext authContext) {
        this.authContext = authContext;
    }

    public void setCreationTimestamp(CartDetails cart) {
        LocalDateTime now = LocalDateTime.now();
        cart.setCreatedAt(now);
        cart.setUpdatedAt(now);
    }


    public void setUpdateTimestamp(CartDetails cart) {
        cart.setUpdatedAt(LocalDateTime.now());
    }

    public long getIdFromAuthContext() {
        return authContext.getAuthenticationId();
    }
}
