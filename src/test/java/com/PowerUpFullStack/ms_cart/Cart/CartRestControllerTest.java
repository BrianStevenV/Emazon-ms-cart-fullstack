package com.PowerUpFullStack.ms_cart.Cart;

import com.PowerUpFullStack.ms_cart.application.dto.request.CartDetailsRequestDto;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static com.PowerUpFullStack.ms_cart.infrastructure.input.rest.ConstantsCartController.CART_REST_CONTROLLER_BASE_PATH;
import static com.PowerUpFullStack.ms_cart.infrastructure.input.rest.ConstantsCartController.CART_REST_CONTROLLER_POST_ADD_PRODUCT_TO_CART;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CartRestControllerTest {
    @Mock
    private ICartHandler cartHandler;

    @InjectMocks
    private CartRestController cartRestController;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(cartRestController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testAddProductToCart_CreatesCartSuccessfully() throws Exception {
        // Arrange
        CartDetailsRequestDto requestDto = new CartDetailsRequestDto(15, 1L);


        CartResponseDto responseDto = new CartResponseDto(
                "Product Name",
                19.99,
                1,
                LocalDateTime.now(),
                LocalDateTime.now(),
                1L,
                1L
        );
        doReturn(responseDto).when(cartHandler).addProductToCart(any(CartDetailsRequestDto.class));


        // Act & Assert
        mockMvc.perform(post(CART_REST_CONTROLLER_BASE_PATH + CART_REST_CONTROLLER_POST_ADD_PRODUCT_TO_CART)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated());


        ArgumentCaptor<CartDetailsRequestDto> argumentCaptor = ArgumentCaptor.forClass(CartDetailsRequestDto.class);
        verify(cartHandler).addProductToCart(argumentCaptor.capture());

    }

}
