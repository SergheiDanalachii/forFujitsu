package org.example;

// Represents a delivery request containing information about the city and vehicle type.
public class DeliveryRequest {
    private String city;
    private String vehicleType;

    /**
     * Retrieves the city from the delivery request.
     * @return The city.
     */
    public String getCity() {
        return city;
    }

    /**
     * Retrieves the vehicle type from the delivery request.
     * @return The vehicle type.
     */
    public String getVehicleType() {
        return vehicleType;
    }
}
