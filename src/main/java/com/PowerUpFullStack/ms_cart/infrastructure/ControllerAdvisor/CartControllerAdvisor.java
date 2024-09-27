package com.PowerUpFullStack.ms_cart.infrastructure.ControllerAdvisor;

import com.PowerUpFullStack.ms_cart.domain.exception.CartNotFoundException;
import com.PowerUpFullStack.ms_cart.domain.exception.ProductCategoryInvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

import static com.PowerUpFullStack.ms_cart.infrastructure.ControllerAdvisor.utils.ConstantsControllerAdvisor.CART_NOT_FOUND_EXCEPTION_MESSAGE;
import static com.PowerUpFullStack.ms_cart.infrastructure.ControllerAdvisor.utils.ConstantsControllerAdvisor.PRODUCT_CATEGORY_INVALID_EXCEPTION_MESSAGE;
import static com.PowerUpFullStack.ms_cart.infrastructure.ControllerAdvisor.utils.ConstantsControllerAdvisor.RESPONSE_ERROR_MESSAGE;

@ControllerAdvice
public class CartControllerAdvisor {
    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCartNotFoundException(CartNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE, CART_NOT_FOUND_EXCEPTION_MESSAGE));
    }

    @ExceptionHandler(ProductCategoryInvalidException.class)
    public ResponseEntity<Map<String, String>> handleProductCategoryInvalidException(ProductCategoryInvalidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE, PRODUCT_CATEGORY_INVALID_EXCEPTION_MESSAGE));
    }

}
