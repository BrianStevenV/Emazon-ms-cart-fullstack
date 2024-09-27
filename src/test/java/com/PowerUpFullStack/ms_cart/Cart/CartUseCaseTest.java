package com.PowerUpFullStack.ms_cart.Cart;

import com.PowerUpFullStack.ms_cart.domain.model.AmountStock;
import com.PowerUpFullStack.ms_cart.domain.model.Available;
import com.PowerUpFullStack.ms_cart.domain.model.Cart;
import com.PowerUpFullStack.ms_cart.domain.model.CartComplete;
import com.PowerUpFullStack.ms_cart.domain.model.CartDetails;
import com.PowerUpFullStack.ms_cart.domain.spi.ICartDetailsPersistencePort;
import com.PowerUpFullStack.ms_cart.domain.spi.ICartPersistencePort;
import com.PowerUpFullStack.ms_cart.domain.spi.IStockFeignClientPort;
import com.PowerUpFullStack.ms_cart.domain.spi.ISuppliesFeignClientPort;
import com.PowerUpFullStack.ms_cart.domain.usecase.CartUseCase;
import com.PowerUpFullStack.ms_cart.domain.usecase.utils.CartUseCaseUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CartUseCaseTest {
    @Mock
    private ICartPersistencePort cartPersistencePort;

    @Mock
    private ICartDetailsPersistencePort cartDetailsPersistencePort;

    @Mock
    private IStockFeignClientPort stockFeignClientPort;

    @Mock
    private ISuppliesFeignClientPort suppliesFeignClientPort;

    @Mock
    private CartUseCaseUtils cartUseCaseUtils;

    @InjectMocks
    private CartUseCase cartUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addProductToCart_createsNewCartWithProduct() {
        CartDetails cartDetails = new CartDetails();
        cartDetails.setProductId(1L);
        cartDetails.setAmount(2);

        when(cartUseCaseUtils.getIdFromAuthContext()).thenReturn(1L);
        when(cartPersistencePort.findCartByUserId(1L)).thenReturn(List.of());
        when(stockFeignClientPort.amountStockAvailable(any())).thenReturn(new Available(true));
        when(cartPersistencePort.findById(1L)).thenReturn(Optional.of(new Cart()));

        Cart result = cartUseCase.addProductToCart(cartDetails);

        assertNotNull(result);
        verify(cartPersistencePort).saveCart(any(Cart.class));
        verify(cartDetailsPersistencePort).saveCartDetails(any(CartDetails.class));
    }



    @Test
    void addProductToCart_throwsExceptionWhenStockUnavailable() {
        CartDetails cartDetails = new CartDetails();
        cartDetails.setProductId(1L);
        cartDetails.setAmount(2);

        when(cartUseCaseUtils.getIdFromAuthContext()).thenReturn(1L);
        when(cartPersistencePort.findCartByUserId(1L)).thenReturn(List.of());
        when(stockFeignClientPort.amountStockAvailable(any())).thenReturn(new Available(false));

        assertThrows(IllegalStateException.class, () -> cartUseCase.addProductToCart(cartDetails));
    }





    @Test
    void testMapToCartComplete() throws Exception {
        // Arrange
        Object[] row = {1L, 2L, 3L, 5, Timestamp.valueOf("2024-09-27 10:00:00"), Timestamp.valueOf("2024-09-27 10:00:00"), true, 1L};

        // Act
        Method method = CartUseCase.class.getDeclaredMethod("mapToCartComplete", Object[].class);
        method.setAccessible(true);
        CartComplete result = (CartComplete) method.invoke(cartUseCase, (Object) row);

        // Assert
        assertEquals(1L, result.getCartId());
        assertEquals(2L, result.getUserIdFromCart());
        assertEquals(3L, result.getCartDetailId());
        assertEquals(5, result.getAmount());
        assertEquals(Timestamp.valueOf("2024-09-27 10:00:00"), result.getCreatedAt());
        assertEquals(Timestamp.valueOf("2024-09-27 10:00:00"), result.getUpdatedAt());
        assertTrue(result.getActive());
        assertEquals(1L, result.getProductId());
    }

    @Test
    void testFindExistingProductInCart() throws Exception {
        // Arrange
        CartComplete cartComplete = new CartComplete();
        cartComplete.setProductId(1L);
        cartComplete.setAmount(2);
        cartComplete.setCartDetailId(1L);
        cartComplete.setActive(true);
        cartComplete.setCreatedAt(Timestamp.valueOf("2024-09-27 10:00:00"));
        cartComplete.setUpdatedAt(Timestamp.valueOf("2024-09-27 10:00:00"));

        // Act
        Method method = CartUseCase.class.getDeclaredMethod("findExistingProductInCart", List.class, Long.class);
        method.setAccessible(true);
        Optional<CartDetails> result = (Optional<CartDetails>) method.invoke(cartUseCase, Arrays.asList(cartComplete), 1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getProductId());
        assertEquals(2, result.get().getAmount());
    }

    @Test
    void testFindExistingProductInCart_NotFound() throws Exception {
        // Arrange
        CartComplete cartComplete = new CartComplete();
        cartComplete.setProductId(1L);
        cartComplete.setAmount(2);
        cartComplete.setCartDetailId(1L);
        cartComplete.setActive(true);
        cartComplete.setCreatedAt(Timestamp.valueOf("2024-09-27 10:00:00"));
        cartComplete.setUpdatedAt(Timestamp.valueOf("2024-09-27 10:00:00"));

        // Act
        Method method = CartUseCase.class.getDeclaredMethod("findExistingProductInCart", List.class, Long.class);
        method.setAccessible(true);
        Optional<CartDetails> result = (Optional<CartDetails>) method.invoke(cartUseCase, Collections.singletonList(cartComplete), 2L);

        // Assert
        assertFalse(result.isPresent());
    }


}
