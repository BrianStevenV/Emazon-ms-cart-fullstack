package com.PowerUpFullStack.ms_cart.Cart;

import com.PowerUpFullStack.ms_cart.domain.model.CartDetails;
import com.PowerUpFullStack.ms_cart.domain.model.CustomPage;
import com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.adapters.CartDetailsEntityMySqlAdapter;
import com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.entities.CartDetailsEntity;
import com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.mapper.ICartDetailsEntityMapper;
import com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.repositories.ICartDetailsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.adapters.utils.ConstantsJpaAdapters.PAGINATION_PAGE_NUMBER;
import static com.PowerUpFullStack.ms_cart.infrastructure.out.jpa.adapters.utils.ConstantsJpaAdapters.PAGINATION_PAGE_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestPropertySource(locations = "classpath:application-dev.yml")
@SpringBootTest
public class CartDetailsMySqlAdapterTest {

    @Mock
    private ICartDetailsRepository cartDetailsRepository;
    @Mock
    private ICartDetailsEntityMapper cartDetailsEntityMapper;

    @InjectMocks
    private CartDetailsEntityMySqlAdapter cartDetailsEntityMySqlAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cartDetailsEntityMySqlAdapter = new CartDetailsEntityMySqlAdapter(cartDetailsRepository, cartDetailsEntityMapper);
    }

    @Test
    void saveCartDetails_savesCartDetailsSuccessfully() {
        CartDetails cartDetails = new CartDetails();
        CartDetailsEntity cartDetailsEntity = new CartDetailsEntity();
        when(cartDetailsEntityMapper.toCartDetailsEntity(cartDetails)).thenReturn(cartDetailsEntity);

        cartDetailsEntityMySqlAdapter.saveCartDetails(cartDetails);

        verify(cartDetailsRepository).save(cartDetailsEntity);
    }

    @Test
    void findByCartIdListCartDetails_returnsListOfCartDetails() {
        long cartId = 1L;
        List<CartDetailsEntity> cartDetailsEntities = List.of(new CartDetailsEntity());
        List<CartDetails> cartDetailsList = List.of(new CartDetails());
        when(cartDetailsRepository.findAllByCartId(cartId)).thenReturn(cartDetailsEntities);
        when(cartDetailsEntityMapper.toCartDetailsList(cartDetailsEntities)).thenReturn(cartDetailsList);

        List<CartDetails> result = cartDetailsEntityMySqlAdapter.findByCartIdListCartDetails(cartId);

        assertEquals(cartDetailsList, result);
    }

    @Test
    void findByCartIdAndProductId_returnsCartDetailsWhenFound() {
        long cartId = 1L;
        long productId = 1L;
        CartDetailsEntity cartDetailsEntity = new CartDetailsEntity();
        CartDetails cartDetails = new CartDetails();
        when(cartDetailsRepository.findByCartIdAndProductId(cartId, productId)).thenReturn(Optional.of(cartDetailsEntity));
        when(cartDetailsEntityMapper.toCartDetails(cartDetailsEntity)).thenReturn(cartDetails);

        Optional<CartDetails> result = cartDetailsEntityMySqlAdapter.findByCartIdAndProductId(cartId, productId);

        assertTrue(result.isPresent());
        assertEquals(cartDetails, result.get());
    }

    @Test
    void findByCartIdAndProductId_returnsEmptyWhenNotFound() {
        long cartId = 1L;
        long productId = 1L;
        when(cartDetailsRepository.findByCartIdAndProductId(cartId, productId)).thenReturn(Optional.empty());

        Optional<CartDetails> result = cartDetailsEntityMySqlAdapter.findByCartIdAndProductId(cartId, productId);

        assertTrue(result.isEmpty());
    }

    @Test
    void deleteCartDetailsByCartIdAndProductId_deletesCartDetailsSuccessfully() {
        long cartId = 1L;
        long productId = 1L;

        cartDetailsEntityMySqlAdapter.deleteCartDetailsByCartIdAndProductId(cartId, productId);

        verify(cartDetailsRepository).disableCartDetailByCartIdAndProductId(cartId, productId);
    }

    @Test
    void enableCartDetailByCartIdAndProductId_enablesCartDetailsSuccessfully() {
        long cartId = 1L;
        long productId = 1L;

        cartDetailsEntityMySqlAdapter.enableCartDetailByCartIdAndProductId(cartId, productId);

        verify(cartDetailsRepository).enableCartDetailByCartIdAndProductId(cartId, productId);
    }

    @Test
    void getPaginationCartDetails_returnsCustomPageSuccessfully() {
        long cartId = 1L;
        Pageable pageable = PageRequest.of(PAGINATION_PAGE_NUMBER, PAGINATION_PAGE_SIZE);
        CartDetailsEntity cartDetailsEntity = new CartDetailsEntity();
        Page<CartDetailsEntity> cartDetailsPage = new PageImpl<>(List.of(cartDetailsEntity), pageable, 1);

        when(cartDetailsRepository.findAllByCartId(pageable, cartId)).thenReturn(cartDetailsPage);
        when(cartDetailsEntityMapper.toCartDetails(cartDetailsEntity)).thenReturn(new CartDetails());

        CustomPage<CartDetails> result = cartDetailsEntityMySqlAdapter.getPaginationCartDetails(cartId);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
    }

    @Test
    void getPaginationCartDetails_returnsNullWhenPageIsEmpty() {
        long cartId = 1L;
        Pageable pageable = PageRequest.of(PAGINATION_PAGE_NUMBER, PAGINATION_PAGE_SIZE);
        Page<CartDetailsEntity> cartDetailsPage = Page.empty(pageable);

        when(cartDetailsRepository.findAllByCartId(pageable, cartId)).thenReturn(cartDetailsPage);

        CustomPage<CartDetails> result = cartDetailsEntityMySqlAdapter.getPaginationCartDetails(cartId);

        assertNull(result);
    }
}
