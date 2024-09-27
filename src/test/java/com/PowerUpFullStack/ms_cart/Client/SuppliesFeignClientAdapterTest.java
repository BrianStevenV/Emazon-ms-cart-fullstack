package com.PowerUpFullStack.ms_cart.Client;

import com.PowerUpFullStack.ms_cart.infrastructure.out.client.adapter.SuppliesFeignClientAdapter;
import com.PowerUpFullStack.ms_cart.infrastructure.out.client.feing.port.ISuppliesFeignClientExternalPort;
import com.PowerUpFullStack.ms_cart.infrastructure.out.client.mapper.ISuppliesFeignClientMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class SuppliesFeignClientAdapterTest {
    @Mock
    private ISuppliesFeignClientExternalPort suppliesFeignClientExternalPort;

    @InjectMocks
    private SuppliesFeignClientAdapter suppliesFeignClientAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getNextDateSupply_returnsDateSuccessfully() {
        long productId = 1L;
        String expectedDate = "2023-10-01";
        when(suppliesFeignClientExternalPort.getNextDateSupply(productId)).thenReturn(expectedDate);

        String result = suppliesFeignClientAdapter.getNextDateSupply(productId);

        assertEquals(expectedDate, result);
    }

    @Test
    void getNextDateSupply_handlesNullResponse() {
        long productId = 1L;
        when(suppliesFeignClientExternalPort.getNextDateSupply(productId)).thenReturn(null);

        String result = suppliesFeignClientAdapter.getNextDateSupply(productId);

        assertNull(result);
    }

    @Test
    void getNextDateSupply_handlesEmptyResponse() {
        long productId = 1L;
        when(suppliesFeignClientExternalPort.getNextDateSupply(productId)).thenReturn("");

        String result = suppliesFeignClientAdapter.getNextDateSupply(productId);

        assertEquals("", result);
    }
}
