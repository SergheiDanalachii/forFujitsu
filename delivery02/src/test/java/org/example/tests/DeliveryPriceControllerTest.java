package org.example.tests;

import org.example.DeliveryPrice;
import org.example.DeliveryPriceController;
import org.example.DeliveryPriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DeliveryPriceControllerTest {

    @Mock
    private DeliveryPriceRepository deliveryPriceRepository;

    @InjectMocks
    private DeliveryPriceController deliveryPriceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddDeliveryPrice() {
        // Arrange
        DeliveryPrice request = new DeliveryPrice("TestCity", "TestVehicle", 10.0);
        when(deliveryPriceRepository.save(any())).thenReturn(request);

        // Act
        ResponseEntity<String> response = deliveryPriceController.addDeliveryPrice(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Delivery price added successfully with ID: null", response.getBody()); // ID null because it's mocked
    }

    @Test
    void testUpdateDeliveryPrice() {
        // Arrange
        DeliveryPrice request = new DeliveryPrice("TestCity", "TestVehicle", 10.0);
        when(deliveryPriceRepository.findByCityAndVehicleType(anyString(), anyString())).thenReturn(request);
        when(deliveryPriceRepository.save(any())).thenReturn(request);

        // Act
        ResponseEntity<String> response = deliveryPriceController.updateDeliveryPrice(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Delivery price updated successfully for city: TestCity and vehicle type: TestVehicle", response.getBody());
    }
}
