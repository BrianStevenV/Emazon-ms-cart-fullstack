package com.PowerUpFullStack.ms_cart.infrastructure.security;

import com.PowerUpFullStack.ms_cart.infrastructure.security.utils.SecurityUtils;
import org.springframework.stereotype.Service;

@Service
public class AuthContextServiceImpl implements IAuthContext {
    @Override
    public long getAuthenticationId() {
        return SecurityUtils.getIdFromInfrastructure();
    }
}
