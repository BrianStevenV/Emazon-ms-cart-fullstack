package com.PowerUpFullStack.ms_cart.Cart;

import com.PowerUpFullStack.ms_cart.domain.exception.ObjectHasNotMethodException;
import com.PowerUpFullStack.ms_cart.domain.exception.ObjectMethodUpdateTimestampException;
import com.PowerUpFullStack.ms_cart.domain.model.Cart;
import com.PowerUpFullStack.ms_cart.domain.usecase.utils.CartUseCaseUtils;
import com.PowerUpFullStack.ms_cart.infrastructure.security.IAuthContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@TestPropertySource(locations = "classpath:application-dev.yml")
@SpringBootTest
public class CartUseCaseUtilsTest {
    @Mock
    private IAuthContext authContext;

    @InjectMocks
    private CartUseCaseUtils cartUseCaseUtils;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cartUseCaseUtils = new CartUseCaseUtils(authContext);
    }

    @Test
    void setCreationTimestamp_setsTimestampsSuccessfully() {
        Cart cart = new Cart();
        cartUseCaseUtils.setCreationTimestamp(cart);

        assertNotNull(cart.getCreatedAt());
        assertNotNull(cart.getUpdatedAt());
    }

    @Test
    void setCreationTimestamp_throwsExceptionWhenMethodNotFound() {
        Object invalidObject = new Object();

        assertThrows(ObjectHasNotMethodException.class, () -> cartUseCaseUtils.setCreationTimestamp(invalidObject));
    }

    @Test
    void setUpdateTimestamp_setsUpdatedAtSuccessfully() {
        Cart cart = new Cart();
        cartUseCaseUtils.setUpdateTimestamp(cart);

        assertNotNull(cart.getUpdatedAt());
    }

    @Test
    void setUpdateTimestamp_throwsExceptionWhenMethodNotFound() {
        Object invalidObject = new Object();

        assertThrows(ObjectMethodUpdateTimestampException.class, () -> cartUseCaseUtils.setUpdateTimestamp(invalidObject));
    }

    @Test
    void getIdFromAuthContext_returnsAuthenticationId() {
        long expectedId = 1L;
        when(authContext.getAuthenticationId()).thenReturn(expectedId);

        long actualId = cartUseCaseUtils.getIdFromAuthContext();

        assertEquals(expectedId, actualId);
    }

}
