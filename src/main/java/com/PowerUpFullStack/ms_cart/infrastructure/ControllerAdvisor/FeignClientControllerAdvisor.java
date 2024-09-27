package com.PowerUpFullStack.ms_cart.infrastructure.ControllerAdvisor;

import com.PowerUpFullStack.ms_cart.infrastructure.configuration.feign.exceptions.FeignBadRequestException;
import com.PowerUpFullStack.ms_cart.infrastructure.configuration.feign.exceptions.FeignForbiddenException;
import com.PowerUpFullStack.ms_cart.infrastructure.configuration.feign.exceptions.FeignInternalServerErrorException;
import com.PowerUpFullStack.ms_cart.infrastructure.configuration.feign.exceptions.FeignNotFoundException;
import com.PowerUpFullStack.ms_cart.infrastructure.configuration.feign.exceptions.FeignUnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.PowerUpFullStack.ms_cart.infrastructure.ControllerAdvisor.utils.ConstantsControllerAdvisor.FEIGN_CLIENT_BAD_REQUEST_EXCEPTION_MESSAGE;
import static com.PowerUpFullStack.ms_cart.infrastructure.ControllerAdvisor.utils.ConstantsControllerAdvisor.FEIGN_CLIENT_FORBIDDEN_EXCEPTION_MESSAGE;
import static com.PowerUpFullStack.ms_cart.infrastructure.ControllerAdvisor.utils.ConstantsControllerAdvisor.FEIGN_CLIENT_INTERNAL_SERVER_ERROR_EXCEPTION_MESSAGE;
import static com.PowerUpFullStack.ms_cart.infrastructure.ControllerAdvisor.utils.ConstantsControllerAdvisor.FEIGN_CLIENT_NOT_FOUND_EXCEPTION_MESSAGE;
import static com.PowerUpFullStack.ms_cart.infrastructure.ControllerAdvisor.utils.ConstantsControllerAdvisor.FEIGN_CLIENT_UNAUTHORIZED_EXCEPTION_MESSAGE;

@ControllerAdvice
public class FeignClientControllerAdvisor {

    @ExceptionHandler(FeignBadRequestException.class)
    public ResponseEntity<String> handleFeignBadRequest(FeignBadRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(FEIGN_CLIENT_BAD_REQUEST_EXCEPTION_MESSAGE);
    }

    @ExceptionHandler(FeignUnauthorizedException.class)
    public ResponseEntity<String> handleFeignUnauthorized(FeignUnauthorizedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(FEIGN_CLIENT_UNAUTHORIZED_EXCEPTION_MESSAGE);
    }

    @ExceptionHandler(FeignForbiddenException.class)
    public ResponseEntity<String> handleFeignForbidden(FeignForbiddenException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(FEIGN_CLIENT_FORBIDDEN_EXCEPTION_MESSAGE);
    }

    @ExceptionHandler(FeignNotFoundException.class)
    public ResponseEntity<String> handleFeignNotFound(FeignNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(FEIGN_CLIENT_NOT_FOUND_EXCEPTION_MESSAGE);
    }

    @ExceptionHandler(FeignInternalServerErrorException.class)
    public ResponseEntity<String> handleFeignInternalServerError(FeignInternalServerErrorException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FEIGN_CLIENT_INTERNAL_SERVER_ERROR_EXCEPTION_MESSAGE);
    }
}
