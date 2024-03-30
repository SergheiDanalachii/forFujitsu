package org.example.tests;

import org.example.DeliveryPrice;
import org.example.DeliveryPriceRepository;
import org.example.DeliveryService;
import org.example.WeatherObservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeliveryServiceTest {

    @Mock
    private DeliveryPriceRepository deliveryPriceRepository;

    @Mock
    private WeatherObservationRepository weatherObservationRepository;

    private DeliveryService deliveryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        deliveryService = new DeliveryService(deliveryPriceRepository, weatherObservationRepository);
    }

    @Test
    void testCalculateBaseFee() {
        // Arrange
        String city = "TestCity";
        String vehicleType = "TestVehicle";
        double expectedPrice = 10.0;
        DeliveryPrice deliveryPrice = new DeliveryPrice(city, vehicleType, expectedPrice);
        when(deliveryPriceRepository.findByCityAndVehicleType(city, vehicleType)).thenReturn(deliveryPrice);

        // Act
        double actualPrice = deliveryService.calculateBaseFee(city, vehicleType);

        // Assert
        assertEquals(expectedPrice, actualPrice);
    }
}
