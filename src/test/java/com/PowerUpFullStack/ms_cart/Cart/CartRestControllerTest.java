package com.PowerUpFullStack.ms_cart.Cart;

import com.PowerUpFullStack.ms_cart.application.dto.request.AddProductToCartRequestDto;
import com.PowerUpFullStack.ms_cart.application.dto.request.CartDetailsRequestDto;
import com.PowerUpFullStack.ms_cart.application.dto.request.FilterByRequestDto;
import com.PowerUpFullStack.ms_cart.application.dto.request.OperationTypeRequestDto;
import com.PowerUpFullStack.ms_cart.application.dto.request.SortDirectionRequestDto;
import com.PowerUpFullStack.ms_cart.application.dto.response.CartDetailsResponseDto;
import com.PowerUpFullStack.ms_cart.application.dto.response.CartResponseDto;
import com.PowerUpFullStack.ms_cart.application.handler.ICartHandler;
import com.PowerUpFullStack.ms_cart.infrastructure.input.rest.CartRestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.context.WebApplicationContext;

import static com.PowerUpFullStack.ms_cart.infrastructure.input.rest.ConstantsCartController.CART_REST_CONTROLLER_BASE_PATH;
import static com.PowerUpFullStack.ms_cart.infrastructure.input.rest.ConstantsCartController.CART_REST_CONTROLLER_GET_PAGINATION_CART;
import static com.PowerUpFullStack.ms_cart.infrastructure.input.rest.ConstantsCartController.CART_REST_CONTROLLER_POST_ADD_PRODUCT_TO_CART;

import static com.PowerUpFullStack.ms_cart.infrastructure.input.rest.ConstantsCartController.CART_REST_CONTROLLER_POST_REMOVE_PRODUCT_TO_CART;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Validated
@TestPropertySource(locations = "classpath:application-dev.yml")
@WebMvcTest(controllers = CartRestController.class)
public class CartRestControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private ICartHandler cartHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void addProductToCart_Success() throws Exception {

        // Arrange
        String addProductToCartRequestJson = "{\"cartDetailsRequestDto\":{\"amount\":2,\"productId\":1},\"operationTypeRequestDto\":\"ADD\"}";

        // Act & Assert
        mockMvc.perform(post(CART_REST_CONTROLLER_BASE_PATH + CART_REST_CONTROLLER_POST_ADD_PRODUCT_TO_CART)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addProductToCartRequestJson))
                .andExpect(status().isCreated());

        verify(cartHandler).addProductToCart(any(AddProductToCartRequestDto.class));
    }


    @Test
    void addProductToCart_ValidationError() throws Exception {

        // Arrange
        String addProductToCartRequestJson = "{\"cartDetailsRequestDto\":{\"amount\"null:,\"productId\":null},\"operationTypeRequestDto\":\"ADD\"}";
        // Act & Assert
        mockMvc.perform(post(CART_REST_CONTROLLER_BASE_PATH + CART_REST_CONTROLLER_POST_ADD_PRODUCT_TO_CART)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addProductToCartRequestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void removeProductFromCart_Success() throws Exception {
        long productId = 1L;

        mockMvc.perform(post(CART_REST_CONTROLLER_BASE_PATH + CART_REST_CONTROLLER_POST_REMOVE_PRODUCT_TO_CART, productId))
                .andExpect(status().isOk());

        verify(cartHandler).removeProductFromCart(productId);
    }

    @Test
    void removeProductFromCart_ValidationError() throws Exception {

        String test = "ValidationError";

        mockMvc.perform(post(CART_REST_CONTROLLER_BASE_PATH + CART_REST_CONTROLLER_POST_REMOVE_PRODUCT_TO_CART, test))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getPaginationCartByAscAndDescByNameProduct_Success() throws Exception {
        SortDirectionRequestDto sortDirectionRequestDto = SortDirectionRequestDto.ASC;
        FilterByRequestDto filterByRequestDto = FilterByRequestDto.PRODUCT;

        mockMvc.perform(get(CART_REST_CONTROLLER_BASE_PATH + CART_REST_CONTROLLER_GET_PAGINATION_CART)
                        .param("sortDirectionRequestDto", sortDirectionRequestDto.toString())
                        .param("filterByRequestDto", filterByRequestDto.toString()))
                .andExpect(status().isOk());

        verify(cartHandler).getPaginationCartByAscAndDescByProductNameAndBrandNameAndCategoryName(sortDirectionRequestDto, filterByRequestDto);
    }

    @Test
    void getPaginationCartByAscAndDescByNameProduct_ValidationError() throws Exception {
        String invalidSortDirection = "INVALID";
        String invalidFilterBy = "INVALID";

        mockMvc.perform(get(CART_REST_CONTROLLER_BASE_PATH + CART_REST_CONTROLLER_GET_PAGINATION_CART)
                        .param("sortDirectionRequestDto", invalidSortDirection)
                        .param("filterByRequestDto", invalidFilterBy))
                .andExpect(status().isBadRequest());
    }


}
