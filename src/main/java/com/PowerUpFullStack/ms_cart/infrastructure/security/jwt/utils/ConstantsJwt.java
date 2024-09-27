package com.PowerUpFullStack.ms_cart.infrastructure.security.jwt.utils;

public class ConstantsJwt {
    private ConstantsJwt() {
        throw new IllegalStateException("Utility class");
    }

    public static final String ROLES_CLAIM_JWT = "roles";
    public static final String ID_CLAIM_JWT = "id";
    public static final long RETURN_FALSE_CLAIM_JWT = 0L;
    public static final String ERROR_EXTRACTING_ID_FROM_JWT = "Error extracting ID from JWT: ";
}
