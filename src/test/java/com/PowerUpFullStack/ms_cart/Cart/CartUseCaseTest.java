package com.PowerUpFullStack.ms_cart.Cart;


import com.PowerUpFullStack.ms_cart.domain.exception.CartDetailNotAvailableInCartException;
import com.PowerUpFullStack.ms_cart.domain.exception.CartDetailsNotFoundException;
import com.PowerUpFullStack.ms_cart.domain.exception.CartNotFoundException;
import com.PowerUpFullStack.ms_cart.domain.exception.SupplyNextDateException;
import com.PowerUpFullStack.ms_cart.domain.model.AllCategories;
import com.PowerUpFullStack.ms_cart.domain.model.Available;
import com.PowerUpFullStack.ms_cart.domain.model.Cart;
import com.PowerUpFullStack.ms_cart.domain.model.CartDetailAndProduct;
import com.PowerUpFullStack.ms_cart.domain.model.CartDetails;
import com.PowerUpFullStack.ms_cart.domain.model.CustomPage;
import com.PowerUpFullStack.ms_cart.domain.model.FilterBy;
import com.PowerUpFullStack.ms_cart.domain.model.OperationType;
import com.PowerUpFullStack.ms_cart.domain.model.Product;
import com.PowerUpFullStack.ms_cart.domain.model.SortDirection;
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
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.argThat;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
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

    private CartUseCase cartUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cartUseCase = new CartUseCase(
                cartPersistencePort,
                cartDetailsPersistencePort,
                stockFeignClientPort,
                suppliesFeignClientPort,
                cartUseCaseUtils
        );
    }

    @Test
    void addProductToCart_NewCart_Success() {
        // Arrange
        long userId = 1L;
        CartDetails cartDetails = new CartDetails();
        cartDetails.setProductId(1L);
        cartDetails.setAmount(2);

        when(cartUseCaseUtils.getIdFromAuthContext()).thenReturn(userId);
        when(cartPersistencePort.findCartEntity(userId))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(new Cart()));
        when(stockFeignClientPort.amountStockAvailable(any())).thenReturn(new Available(true));

        // Act
        cartUseCase.addProductToCart(cartDetails, OperationType.ADD);

        // Assert
        verify(cartPersistencePort).saveCart(any(Cart.class));
        verify(cartDetailsPersistencePort).saveCartDetails(any(CartDetails.class));
    }

    @Test
    void addProductToCart_ExistingCart_NewProduct_Success() {
        // Arrange
        long userId = 1L;
        long cartId = 1L;
        long productId = 1L;
        CartDetails cartDetails = new CartDetails();
        cartDetails.setProductId(productId);
        cartDetails.setAmount(2);

        Cart existingCart = new Cart();
        existingCart.setId(cartId);

        List<CartDetails> existingProducts = Arrays.asList(
                new CartDetails(2L, 5, LocalDateTime.now(), LocalDateTime.now(), true, productId, cartId)
        );

        when(cartUseCaseUtils.getIdFromAuthContext()).thenReturn(userId);
        when(cartPersistencePort.findCartEntity(userId)).thenReturn(Optional.of(existingCart));
        when(cartDetailsPersistencePort.findByCartIdAndProductId(cartId, productId)).thenReturn(Optional.empty());
        when(cartDetailsPersistencePort.findByCartIdListCartDetails(cartId)).thenReturn(existingProducts);
        when(stockFeignClientPort.amountStockAvailable(any())).thenReturn(new Available(true));

        Map<Long, List<Long>> categoriesMap = new HashMap<>();
        categoriesMap.put(1L, Arrays.asList(1L, 2L));
        categoriesMap.put(2L, Arrays.asList(3L));
        AllCategories allCategories = new AllCategories(categoriesMap);
        when(stockFeignClientPort.getCategoriesNames(any())).thenReturn(allCategories);

        // Act
        cartUseCase.addProductToCart(cartDetails, OperationType.ADD);

        // Assert
        verify(cartDetailsPersistencePort).saveCartDetails(argThat(cd ->
                cd.getCartId() == cartId &&
                        cd.getProductId() == productId &&
                        cd.getAmount() == cartDetails.getAmount() &&
                        cd.getActive()
        ));
        verify(cartPersistencePort).saveCart(existingCart);
        verify(cartUseCaseUtils).setUpdateTimestamp(existingCart);
        verify(cartUseCaseUtils).setCreationTimestamp(any(CartDetails.class));
    }

    @Test
    void addProductToCart_ExistingCart_ExistingProduct_Success() {
        // Arrange
        long userId = 1L;
        long cartId = 1L;
        CartDetails cartDetails = new CartDetails();
        cartDetails.setProductId(1L);
        cartDetails.setAmount(2);

        Cart existingCart = new Cart();
        existingCart.setId(cartId);

        CartDetails existingCartDetails = new CartDetails();
        existingCartDetails.setProductId(1L);
        existingCartDetails.setAmount(3);
        existingCartDetails.setActive(true);

        when(cartUseCaseUtils.getIdFromAuthContext()).thenReturn(userId);
        when(cartPersistencePort.findCartEntity(userId)).thenReturn(Optional.of(existingCart));
        when(cartDetailsPersistencePort.findByCartIdAndProductId(cartId, 1L)).thenReturn(Optional.of(existingCartDetails));
        when(stockFeignClientPort.amountStockAvailable(any())).thenReturn(new Available(true));

        // Act
        cartUseCase.addProductToCart(cartDetails, OperationType.ADD);

        // Assert
        verify(cartDetailsPersistencePort).saveCartDetails(existingCartDetails);
        verify(cartPersistencePort).saveCart(existingCart);
        assertEquals(5, existingCartDetails.getAmount());
    }

    @Test
    void addProductToCart_StockNotAvailable_ThrowsException() {
        // Arrange
        long userId = 1L;
        CartDetails cartDetails = new CartDetails();
        cartDetails.setProductId(1L);
        cartDetails.setAmount(2);

        when(cartUseCaseUtils.getIdFromAuthContext()).thenReturn(userId);
        when(cartPersistencePort.findCartEntity(userId)).thenReturn(Optional.empty());
        when(stockFeignClientPort.amountStockAvailable(any())).thenReturn(new Available(false));
        when(suppliesFeignClientPort.getNextDateSupply(anyLong())).thenReturn("2023-10-10");

        // Act & Assert
        assertThrows(SupplyNextDateException.class, () -> cartUseCase.addProductToCart(cartDetails, OperationType.ADD));
    }


    @Test
    void removeProductFromCart_removesProductSuccessfully() {
        long productId = 1L;
        long userId = 1L;
        Cart cart = new Cart();
        cart.setId(1L);
        CartDetails cartDetails = new CartDetails();
        cartDetails.setProductId(productId);
        cartDetails.setCartId(cart.getId());

        when(cartUseCaseUtils.getIdFromAuthContext()).thenReturn(userId);
        when(cartPersistencePort.findCartEntity(userId)).thenReturn(Optional.of(cart));
        when(cartDetailsPersistencePort.findByCartIdAndProductId(cart.getId(), productId)).thenReturn(Optional.of(cartDetails));

        cartUseCase.removeProductFromCart(productId);

        verify(cartDetailsPersistencePort).deleteCartDetailsByCartIdAndProductId(cart.getId(), productId);
    }

    @Test
    void removeProductFromCart_throwsCartNotFoundException() {
        long productId = 1L;
        long userId = 1L;

        when(cartUseCaseUtils.getIdFromAuthContext()).thenReturn(userId);
        when(cartPersistencePort.findCartEntity(userId)).thenReturn(Optional.empty());

        assertThrows(CartNotFoundException.class, () -> cartUseCase.removeProductFromCart(productId));
    }

    @Test
    void removeProductFromCart_throwsCartDetailsNotFoundException() {
        long productId = 1L;
        long userId = 1L;
        Cart cart = new Cart();
        cart.setId(1L);

        when(cartUseCaseUtils.getIdFromAuthContext()).thenReturn(userId);
        when(cartPersistencePort.findCartEntity(userId)).thenReturn(Optional.of(cart));
        when(cartDetailsPersistencePort.findByCartIdAndProductId(cart.getId(), productId)).thenReturn(Optional.empty());

        assertThrows(CartDetailsNotFoundException.class, () -> cartUseCase.removeProductFromCart(productId));
    }

    @Test
    void getPaginationCartByAscAndDescByProductNameAndBrandNameAndCategoryName_throwsCartNotFoundException() {
        long userId = 1L;
        SortDirection sortDirection = SortDirection.ASC;
        FilterBy filterBy = FilterBy.PRODUCT;

        when(cartUseCaseUtils.getIdFromAuthContext()).thenReturn(userId);
        when(cartPersistencePort.findCartEntity(userId)).thenReturn(Optional.empty());

        assertThrows(CartNotFoundException.class, () -> cartUseCase.getPaginationCartByAscAndDescByProductNameAndBrandNameAndCategoryName(sortDirection, filterBy));
    }

    @Test
    void getPaginationCartByAscAndDescByProductNameAndBrandNameAndCategoryName_throwsCartDetailNotAvailableInCartException() {
        long userId = 1L;
        long cartId = 1L;
        SortDirection sortDirection = SortDirection.ASC;
        FilterBy filterBy = FilterBy.PRODUCT;

        Cart cart = new Cart();
        cart.setId(cartId);

        CartDetails cartDetails = new CartDetails();
        cartDetails.setProductId(1L);
        cartDetails.setAmount(2);
        cartDetails.setActive(false);

        CustomPage<CartDetails> cartDetailsPage = new CustomPage<>(List.of(cartDetails), 0, 10, 1, 1, true, true);

        when(cartUseCaseUtils.getIdFromAuthContext()).thenReturn(userId);
        when(cartPersistencePort.findCartEntity(userId)).thenReturn(Optional.of(cart));
        when(cartDetailsPersistencePort.getPaginationCartDetails(cartId)).thenReturn(cartDetailsPage);

        assertThrows(CartDetailNotAvailableInCartException.class, () -> cartUseCase.getPaginationCartByAscAndDescByProductNameAndBrandNameAndCategoryName(sortDirection, filterBy));
    }

}
