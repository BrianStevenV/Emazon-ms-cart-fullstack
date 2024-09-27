package com.PowerUpFullStack.ms_cart.infrastructure.configuration.feign;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FeignExceptionMessage {
    private Map<String, String> errors;
    private int status;
}
