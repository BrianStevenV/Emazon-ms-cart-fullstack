package com.PowerUpFullStack.ms_cart.Cart;

import com.PowerUpFullStack.ms_cart.domain.model.Cart;
import com.PowerUpFullStack.ms_cart.domain.model.CartDetails;
import com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.adapters.CartMySqlAdapter;
import com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.entities.CartDetailsEntity;
import com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.entities.CartEntity;
import com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.mapper.ICartEntityMapper;
import com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.repositories.ICartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestPropertySource(locations = "classpath:application-dev.yml")
@SpringBootTest
public class CartMySqlAdapterTest {
    @Mock
    private ICartRepository cartRepository;

    @Mock
    private ICartEntityMapper cartEntityMapper;

    @InjectMocks
    private CartMySqlAdapter cartMySqlAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cartMySqlAdapter = new CartMySqlAdapter(cartRepository, cartEntityMapper);
    }

    @Test
    void saveCart_savesCartSuccessfully() {

        List<CartDetailsEntity> cartDetailsEntityList = List.of(new CartDetailsEntity(1L, 5, LocalDateTime.now(), LocalDateTime.now(), true, 1L, 1L));
        List<CartDetails> cartDetailsList = List.of(new CartDetails(1L, 5, LocalDateTime.now(), LocalDateTime.now(), true, 1L, 1L));
        Cart cart = new Cart(1L, LocalDateTime.now(), LocalDateTime.now(), 1L, cartDetailsList);
        CartEntity cartEntity = new CartEntity(1L, LocalDateTime.now(), LocalDateTime.now(), 1L, cartDetailsEntityList);
        when(cartEntityMapper.toCartEntity(cart)).thenReturn(cartEntity);

        cartMySqlAdapter.saveCart(cart);

        verify(cartRepository).save(cartEntity);
    }

    @Test
    void findCartEntity_returnsCartWhenFound() {
        long userId = 1L;
        CartEntity cartEntity = new CartEntity();
        Cart cart = new Cart();
        when(cartRepository.findCartEntity(userId)).thenReturn(Optional.of(cartEntity));
        when(cartEntityMapper.toCart(cartEntity)).thenReturn(cart);

        Optional<Cart> result = cartMySqlAdapter.findCartEntity(userId);

        assertTrue(result.isPresent());
        assertEquals(cart, result.get());
    }

    @Test
    void findCartEntity_returnsEmptyWhenCartNotFound() {
        long userId = 1L;
        when(cartRepository.findCartEntity(userId)).thenReturn(Optional.empty());

        Optional<Cart> result = cartMySqlAdapter.findCartEntity(userId);

        assertTrue(result.isEmpty());
    }

}
