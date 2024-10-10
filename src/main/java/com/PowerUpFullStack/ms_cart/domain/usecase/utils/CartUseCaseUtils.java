package com.PowerUpFullStack.ms_cart.domain.usecase.utils;

import com.PowerUpFullStack.ms_cart.domain.exception.ObjectHasNotMethodException;
import com.PowerUpFullStack.ms_cart.domain.exception.ObjectMethodUpdateTimestampException;
import com.PowerUpFullStack.ms_cart.domain.model.Cart;
import com.PowerUpFullStack.ms_cart.domain.model.CartDetails;
import com.PowerUpFullStack.ms_cart.infrastructure.security.IAuthContext;

import java.lang.reflect.Method;

import java.time.LocalDateTime;

public class CartUseCaseUtils {
    private final IAuthContext authContext;

    public CartUseCaseUtils(IAuthContext authContext) {
        this.authContext = authContext;
    }



    public void setCreationTimestamp(Object cart) {
        LocalDateTime now = LocalDateTime.now();

        try{
            Method setCreatedAt = cart.getClass().getMethod("setCreatedAt", LocalDateTime.class);
            Method setUpdatedAt = cart.getClass().getMethod("setUpdatedAt", LocalDateTime.class);

            setCreatedAt.invoke(cart, now);
            setUpdatedAt.invoke(cart, now);
        } catch (Exception e) {
            throw new ObjectHasNotMethodException();
        }


    }


    public void setUpdateTimestamp(Object cart) {
        LocalDateTime now = LocalDateTime.now();
        try {
            Method setUpdatedAt = cart.getClass().getMethod("setUpdatedAt", LocalDateTime.class);
            setUpdatedAt.invoke(cart, now);
        } catch (Exception e) {
            throw new ObjectMethodUpdateTimestampException();
        }
    }

    public long getIdFromAuthContext() {
        return authContext.getAuthenticationId();
    }
}
