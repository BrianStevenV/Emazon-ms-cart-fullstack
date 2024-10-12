package com.PowerUpFullStack.ms_cart.infrastructure.input.rest;

import com.PowerUpFullStack.ms_cart.application.dto.request.AddProductToCartRequestDto;
import com.PowerUpFullStack.ms_cart.application.dto.request.FilterByRequestDto;
import com.PowerUpFullStack.ms_cart.application.dto.request.SortDirectionRequestDto;
import com.PowerUpFullStack.ms_cart.application.dto.response.PaginationResponseDto;
import com.PowerUpFullStack.ms_cart.application.dto.response.ProductResponseDto;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.PowerUpFullStack.ms_cart.infrastructure.documentation.OpenApiConstants.APPLICATION_JSON;
import static com.PowerUpFullStack.ms_cart.infrastructure.documentation.OpenApiConstants.CODE_201;
import static com.PowerUpFullStack.ms_cart.infrastructure.documentation.OpenApiConstants.CODE_409;
import static com.PowerUpFullStack.ms_cart.infrastructure.documentation.OpenApiConstants.DESCRIPTION_CREATE_CART_201;
import static com.PowerUpFullStack.ms_cart.infrastructure.documentation.OpenApiConstants.DESCRIPTION_CREATE_CART_409;
import static com.PowerUpFullStack.ms_cart.infrastructure.documentation.OpenApiConstants.DESCRIPTION_GET_PAGINATION_CART_200;
import static com.PowerUpFullStack.ms_cart.infrastructure.documentation.OpenApiConstants.DESCRIPTION_GET_PAGINATION_CART_404;
import static com.PowerUpFullStack.ms_cart.infrastructure.documentation.OpenApiConstants.DESCRIPTION_REMOVE_CART_201;
import static com.PowerUpFullStack.ms_cart.infrastructure.documentation.OpenApiConstants.DESCRIPTION_REMOVE_CART_409;
import static com.PowerUpFullStack.ms_cart.infrastructure.documentation.OpenApiConstants.SCHEMAS_ERROR;
import static com.PowerUpFullStack.ms_cart.infrastructure.documentation.OpenApiConstants.SCHEMAS_MAP;
import static com.PowerUpFullStack.ms_cart.infrastructure.documentation.OpenApiConstants.SECURITY_REQUIREMENT;
import static com.PowerUpFullStack.ms_cart.infrastructure.documentation.OpenApiConstants.SUMMARY_CREATE_CART;
import static com.PowerUpFullStack.ms_cart.infrastructure.documentation.OpenApiConstants.SUMMARY_GET_PAGINATION_CART;
import static com.PowerUpFullStack.ms_cart.infrastructure.documentation.OpenApiConstants.SUMMARY_REMOVE_CART;
import static com.PowerUpFullStack.ms_cart.infrastructure.input.rest.ConstantsCartController.CART_REST_CONTROLLER_BASE_PATH;
import static com.PowerUpFullStack.ms_cart.infrastructure.input.rest.ConstantsCartController.CART_REST_CONTROLLER_GET_PAGINATION_CART;
import static com.PowerUpFullStack.ms_cart.infrastructure.input.rest.ConstantsCartController.CART_REST_CONTROLLER_PATH_VARIABLE_PRODUCT_ID;
import static com.PowerUpFullStack.ms_cart.infrastructure.input.rest.ConstantsCartController.CART_REST_CONTROLLER_POST_ADD_PRODUCT_TO_CART;
import static com.PowerUpFullStack.ms_cart.infrastructure.input.rest.ConstantsCartController.CART_REST_CONTROLLER_POST_REMOVE_PRODUCT_TO_CART;

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
    public ResponseEntity<Void> addProductToCart(@Valid @RequestBody AddProductToCartRequestDto addProductToCartRequestDto){ {
        cartHandler.addProductToCart(addProductToCartRequestDto);
    }
        return new ResponseEntity<>(HttpStatus.CREATED);
    };

    @Operation(summary = SUMMARY_GET_PAGINATION_CART,
            responses = {
                    @ApiResponse(responseCode = CODE_201, description = DESCRIPTION_GET_PAGINATION_CART_200,
                            content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(ref = SCHEMAS_MAP))),
                    @ApiResponse(responseCode = CODE_409, description = DESCRIPTION_GET_PAGINATION_CART_404,
                            content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(ref = SCHEMAS_ERROR)))})
    @PostMapping(CART_REST_CONTROLLER_POST_REMOVE_PRODUCT_TO_CART)
    @SecurityRequirement(name = SECURITY_REQUIREMENT)
    public ResponseEntity<Void> removeProductFromCart(@PathVariable(CART_REST_CONTROLLER_PATH_VARIABLE_PRODUCT_ID) long productId){
        cartHandler.removeProductFromCart(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = SUMMARY_REMOVE_CART,
            responses = {
                    @ApiResponse(responseCode = CODE_201, description = DESCRIPTION_REMOVE_CART_201,
                            content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(ref = SCHEMAS_MAP))),
                    @ApiResponse(responseCode = CODE_409, description = DESCRIPTION_REMOVE_CART_409,
                            content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(ref = SCHEMAS_ERROR)))})
    @GetMapping(CART_REST_CONTROLLER_GET_PAGINATION_CART)
    @SecurityRequirement(name = SECURITY_REQUIREMENT)
    public PaginationResponseDto<ProductResponseDto> getPaginationCartByAscAndDescByNameProduct(@Valid
                                                                                             @RequestParam(defaultValue = "ASC")
                                                                                             SortDirectionRequestDto
                                                                                                         sortDirectionRequestDto,
                                                                                                @RequestParam(defaultValue = "PRODUCT")
                                                                                             FilterByRequestDto
                                                                                                     filterByRequestDto){
        return cartHandler.getPaginationCartByAscAndDescByProductNameAndBrandNameAndCategoryName(sortDirectionRequestDto, filterByRequestDto);
    }
}
