package com.PowerUpFullStack.ms_cart.infrastructure.security.utils;

import com.PowerUpFullStack.ms_cart.infrastructure.security.jwt.JwtAuthenticationToken;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;

import java.util.List;
import java.util.stream.Collectors;

import static com.PowerUpFullStack.ms_cart.infrastructure.security.utils.ConstantsSecurity.AUTHORIZATION_HEADER;
import static com.PowerUpFullStack.ms_cart.infrastructure.security.utils.ConstantsSecurity.AUTHORIZATION_HEADER_SUBSTRING;
import static com.PowerUpFullStack.ms_cart.infrastructure.security.utils.ConstantsSecurity.BEARER_TOKEN;
import static com.PowerUpFullStack.ms_cart.infrastructure.security.utils.ConstantsSecurity.DELIMETER_JOINING_AUTH_GET_AUTHORITIES;
import static com.PowerUpFullStack.ms_cart.infrastructure.security.utils.ConstantsSecurity.ERROR_EXTRACTING_ID_FROM_INFRASTRUCTURE_CONTEXT;
import static com.PowerUpFullStack.ms_cart.infrastructure.security.utils.ConstantsSecurity.PREFIX_RECURSIVE;
import static com.PowerUpFullStack.ms_cart.infrastructure.security.utils.ConstantsSecurity.PREFIX_RECURSIVE_NEXT;

public class SecurityUtils {

    private static AntPathMatcher pathMatcher = new AntPathMatcher();

    public static long getIdFromInfrastructure(){

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication instanceof JwtAuthenticationToken) {
                System.out.println("From authentication> "+ ((JwtAuthenticationToken) authentication).getUserId());
                return ((JwtAuthenticationToken) authentication).getUserId();
            }
        } catch (Exception e) {
            System.err.println(ERROR_EXTRACTING_ID_FROM_INFRASTRUCTURE_CONTEXT + e.getMessage());
        }
        return 0L;
    }

    public static String getRolesFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(DELIMETER_JOINING_AUTH_GET_AUTHORITIES));
    }

    public static String getToken(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION_HEADER);
        if (header != null && header.startsWith(BEARER_TOKEN)) {
            return header.substring(AUTHORIZATION_HEADER_SUBSTRING);
        }
        return null;
    }

    public static boolean isExcludedPrefixRecursively(String currentRoute, List<String> prefixes) {
        if (prefixes.isEmpty()) {
            return false;
        }

        String prefix = prefixes.get(PREFIX_RECURSIVE);
        if (pathMatcher.matchStart(prefix, currentRoute)) {
            return true;
        } else {

            return isExcludedPrefixRecursively(currentRoute, prefixes.subList(PREFIX_RECURSIVE_NEXT, prefixes.size()));
        }
    }
}
