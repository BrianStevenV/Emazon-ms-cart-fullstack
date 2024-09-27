package com.PowerUpFullStack.ms_cart.infrastructure.security.jwt.utils;

import com.PowerUpFullStack.ms_cart.infrastructure.security.jwt.JwtConfig;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.PowerUpFullStack.ms_cart.infrastructure.security.jwt.utils.ConstantsJwt.ERROR_EXTRACTING_ID_FROM_JWT;
import static com.PowerUpFullStack.ms_cart.infrastructure.security.jwt.utils.ConstantsJwt.ID_CLAIM_JWT;
import static com.PowerUpFullStack.ms_cart.infrastructure.security.jwt.utils.ConstantsJwt.RETURN_FALSE_CLAIM_JWT;
import static com.PowerUpFullStack.ms_cart.infrastructure.security.jwt.utils.ConstantsJwt.ROLES_CLAIM_JWT;

@Component
public class JwtMethodUtils {

    public JwtMethodUtils(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    private static JwtConfig jwtConfig;

    public static List<String> getRoleFromToken(String token){
        System.out.println("getidformrole");
        String secret = jwtConfig.getSecret();
        System.out.println(secret);
        return (List<String>) Jwts.parserBuilder()
                .setSigningKey(secret.getBytes())
                .build().parseClaimsJws(token)
                .getBody().get(ROLES_CLAIM_JWT, List.class);
    }

    public static long getIdFromToken(String token) {
        String secret = jwtConfig.getSecret();
        try {
            // Parsear el token y obtener los claims
            var claims = Jwts.parserBuilder()
                    .setSigningKey(secret.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Long idClaim = claims.get(ID_CLAIM_JWT, Long.class);
            System.out.println(idClaim);
            return idClaim != null ? idClaim : RETURN_FALSE_CLAIM_JWT;
        } catch (Exception e) {
            System.err.println(ERROR_EXTRACTING_ID_FROM_JWT + e.getMessage());
            return 0L;
        }
    }
}
