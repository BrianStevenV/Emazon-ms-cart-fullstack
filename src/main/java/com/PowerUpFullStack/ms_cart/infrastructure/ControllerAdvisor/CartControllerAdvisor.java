package com.PowerUpFullStack.ms_cart.infrastructure.ControllerAdvisor;

import com.PowerUpFullStack.ms_cart.domain.exception.CartDetailsNotFoundException;
import com.PowerUpFullStack.ms_cart.domain.exception.CartNotFoundException;
import com.PowerUpFullStack.ms_cart.domain.exception.ObjectHasNotMethodException;
import com.PowerUpFullStack.ms_cart.domain.exception.ObjectMethodUpdateTimestampException;
import com.PowerUpFullStack.ms_cart.domain.exception.OperationTypeNotPermissionException;
import com.PowerUpFullStack.ms_cart.domain.exception.ProductCategoryInvalidException;
import com.PowerUpFullStack.ms_cart.domain.exception.SupplyNextDateException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.PowerUpFullStack.ms_cart.infrastructure.ControllerAdvisor.utils.ConstantsControllerAdvisor.CART_NOT_FOUND_EXCEPTION_MESSAGE;
import static com.PowerUpFullStack.ms_cart.infrastructure.ControllerAdvisor.utils.ConstantsControllerAdvisor.OBJECT_HAS_NOT_METHOD_EXCEPTION_MESSAGE;
import static com.PowerUpFullStack.ms_cart.infrastructure.ControllerAdvisor.utils.ConstantsControllerAdvisor.OBJECT_METHOD_UPDATE_TIMESTAMP_EXCEPTION_MESSAGE;
import static com.PowerUpFullStack.ms_cart.infrastructure.ControllerAdvisor.utils.ConstantsControllerAdvisor.OPERATION_TYPE_NOT_PERMISSION_EXCEPTION_MESSAGE;
import static com.PowerUpFullStack.ms_cart.infrastructure.ControllerAdvisor.utils.ConstantsControllerAdvisor.PRODUCT_CATEGORY_INVALID_EXCEPTION_MESSAGE;
import static com.PowerUpFullStack.ms_cart.infrastructure.ControllerAdvisor.utils.ConstantsControllerAdvisor.PRODUCT_NOT_FOUND_IN_DATABASE_EXCEPTION_MESSAGE;
import static com.PowerUpFullStack.ms_cart.infrastructure.ControllerAdvisor.utils.ConstantsControllerAdvisor.RESPONSE_ERROR_MESSAGE;

@ControllerAdvice
public class CartControllerAdvisor {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errorMessages = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                errorMessages.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
            } else {
                errorMessages.add(error.getDefaultMessage());
            }
        }
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            errors.put(propertyPath, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


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

    @ExceptionHandler(ObjectHasNotMethodException.class)
    public ResponseEntity<Map<String, String>> handleObjectHasNotMethodException(ObjectHasNotMethodException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE, OBJECT_HAS_NOT_METHOD_EXCEPTION_MESSAGE));
    }

    @ExceptionHandler(ObjectMethodUpdateTimestampException.class)
    public ResponseEntity<Map<String, String>> handleObjectMethodUpdateTimestampException(ObjectMethodUpdateTimestampException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE, OBJECT_METHOD_UPDATE_TIMESTAMP_EXCEPTION_MESSAGE));
    }

    @ExceptionHandler(CartDetailsNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCartDetailsNotFoundException(CartDetailsNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE,PRODUCT_NOT_FOUND_IN_DATABASE_EXCEPTION_MESSAGE ));
    }

    @ExceptionHandler(SupplyNextDateException.class)
    public ResponseEntity<Map<String, String>> handleSupplyNextDateException(SupplyNextDateException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE, ex.getMessage()));
    }

    @ExceptionHandler(OperationTypeNotPermissionException.class)
    public ResponseEntity<Map<String, String>> handleOperationTypeNotPermissionException(OperationTypeNotPermissionException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE, OPERATION_TYPE_NOT_PERMISSION_EXCEPTION_MESSAGE));
    }

}
