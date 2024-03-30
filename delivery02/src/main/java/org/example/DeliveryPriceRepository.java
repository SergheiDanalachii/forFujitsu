package org.example;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//  Repository interface for accessing and managing DeliveryPrice entities.
@Repository
public interface DeliveryPriceRepository extends JpaRepository<DeliveryPrice, Long> {

    /**
     * Retrieves a DeliveryPrice entity by city and vehicle type.
     * @param city The city for which to retrieve the delivery price.
     * @param vehicleType The type of vehicle for which to retrieve the delivery price.
     * @return The DeliveryPrice entity if found, null otherwise.
     */
    DeliveryPrice findByCityAndVehicleType(String city, String vehicleType);
}
