package com.PowerUpFullStack.ms_cart.Cart;

import com.PowerUpFullStack.ms_cart.domain.model.Cart;
import com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.adapters.CartMySqlAdapter;
import com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.entities.CartEntity;
import com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.mapper.ICartEntityMapper;
import com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.repositories.ICartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    }

    @Test
    void saveCart_savesCartSuccessfully() {
        Cart cart = new Cart();
        when(cartEntityMapper.toCartEntity(cart)).thenReturn(new CartEntity());

        cartMySqlAdapter.saveCart(cart);

        verify(cartRepository).save(any(CartEntity.class));
    }

    @Test
    void findCartByUserId_returnsEmptyListWhenNoCartFound() {
        long userId = 1L;
        when(cartRepository.findByUserId(userId)).thenReturn(List.of());

        List<Object[]> results = cartMySqlAdapter.findCartByUserId(userId);

        assertTrue(results.isEmpty());
    }

    @Test
    void findById_returnsCartWhenFound() {
        long cartId = 1L;
        CartEntity cartEntity = new CartEntity();
        Cart cart = new Cart();
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cartEntity));
        when(cartEntityMapper.toCart(cartEntity)).thenReturn(cart);

        Optional<Cart> result = cartMySqlAdapter.findById(cartId);

        assertTrue(result.isPresent());
        assertEquals(cart, result.get());
    }

    @Test
    void findById_returnsEmptyWhenCartNotFound() {
        long cartId = 1L;
        when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

        Optional<Cart> result = cartMySqlAdapter.findById(cartId);

        assertTrue(result.isEmpty());
    }
}
