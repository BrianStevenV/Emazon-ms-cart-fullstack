package com.PowerUpFullStack.ms_cart.infrastructure.input.rest;

import com.PowerUpFullStack.ms_cart.application.dto.request.CartDetailsRequestDto;
import com.PowerUpFullStack.ms_cart.application.dto.response.CartResponseDto;
import com.PowerUpFullStack.ms_cart.application.handler.ICartHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.PowerUpFullStack.ms_cart.infrastructure.documentation.OpenApiConstants.APPLICATION_JSON;
import static com.PowerUpFullStack.ms_cart.infrastructure.documentation.OpenApiConstants.CODE_201;
import static com.PowerUpFullStack.ms_cart.infrastructure.documentation.OpenApiConstants.CODE_409;
import static com.PowerUpFullStack.ms_cart.infrastructure.documentation.OpenApiConstants.DESCRIPTION_CREATE_CART_201;
import static com.PowerUpFullStack.ms_cart.infrastructure.documentation.OpenApiConstants.DESCRIPTION_CREATE_CART_409;
import static com.PowerUpFullStack.ms_cart.infrastructure.documentation.OpenApiConstants.SCHEMAS_ERROR;
import static com.PowerUpFullStack.ms_cart.infrastructure.documentation.OpenApiConstants.SCHEMAS_MAP;
import static com.PowerUpFullStack.ms_cart.infrastructure.documentation.OpenApiConstants.SECURITY_REQUIREMENT;
import static com.PowerUpFullStack.ms_cart.infrastructure.documentation.OpenApiConstants.SUMMARY_CREATE_CART;
import static com.PowerUpFullStack.ms_cart.infrastructure.input.rest.ConstantsCartController.CART_REST_CONTROLLER_BASE_PATH;
import static com.PowerUpFullStack.ms_cart.infrastructure.input.rest.ConstantsCartController.CART_REST_CONTROLLER_POST_ADD_PRODUCT_TO_CART;

@RestController
@RequiredArgsConstructor
@RequestMapping(CART_REST_CONTROLLER_BASE_PATH)
public class CartRestController {
    private final ICartHandler cartHandler;

    @Operation(summary = SUMMARY_CREATE_CART,
            responses = {
                    @ApiResponse(responseCode = CODE_201, description = DESCRIPTION_CREATE_CART_201,
                            content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(ref = SCHEMAS_MAP))),
                    @ApiResponse(responseCode = CODE_409, description = DESCRIPTION_CREATE_CART_409,
                            content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(ref = SCHEMAS_ERROR)))})
    @PostMapping(CART_REST_CONTROLLER_POST_ADD_PRODUCT_TO_CART)
    @SecurityRequirement(name = SECURITY_REQUIREMENT)
    public ResponseEntity<CartResponseDto> addProductToCart(@Valid @RequestBody CartDetailsRequestDto cartDetailsRequestDto) {
        cartHandler.addProductToCart(cartDetailsRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
