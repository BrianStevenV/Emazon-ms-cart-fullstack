package com.PowerUpFullStack.ms_cart.infrastructure.security;

import com.PowerUpFullStack.ms_cart.infrastructure.security.jwt.JwtAuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.PowerUpFullStack.ms_cart.infrastructure.security.jwt.utils.JwtMethodUtils.getIdFromToken;
import static com.PowerUpFullStack.ms_cart.infrastructure.security.jwt.utils.JwtMethodUtils.getRoleFromToken;
import static com.PowerUpFullStack.ms_cart.infrastructure.security.utils.ConstantsSecurity.SWAGGER_UI;
import static com.PowerUpFullStack.ms_cart.infrastructure.security.utils.ConstantsSecurity.SWAGGER_UI_HTML;
import static com.PowerUpFullStack.ms_cart.infrastructure.security.utils.ConstantsSecurity.V3_API_DOCS;
import static com.PowerUpFullStack.ms_cart.infrastructure.security.utils.SecurityUtils.getToken;
import static com.PowerUpFullStack.ms_cart.infrastructure.security.utils.SecurityUtils.isExcludedPrefixRecursively;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private List<String> excludedPrefixes = Arrays.asList(SWAGGER_UI_HTML, SWAGGER_UI, V3_API_DOCS);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String token = getToken(request);
            System.out.println(token);
            if(token != null){

                Authentication authentication = new JwtAuthenticationToken(token, getRoleFromToken(token), getIdFromToken(token));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }   catch (Exception e){
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String currentRoute = request.getServletPath();
        return isExcludedPrefixRecursively(currentRoute, excludedPrefixes);
    }
}
