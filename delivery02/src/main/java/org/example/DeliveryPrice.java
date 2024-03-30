package org.example;

import javax.persistence.*;

// Represents the price of delivery for a specific city and vehicle type.
@Entity
@Table(name = "DELIVERY_PRICE")
public class DeliveryPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CITY")
    private String city;

    @Column(name = "VEHICLE_TYPE")
    private String vehicleType;

    @Column(name = "PRICE")
    private Double price;

    /**
     * Constructs a DeliveryPrice object with the specified city, vehicle type, and price.
     * @param city The city for which the delivery price is defined.
     * @param vehicleType The type of vehicle for which the delivery price is defined.
     * @param price The price of delivery.
     */
    public DeliveryPrice(String city, String vehicleType, Double price) {
        this.city=city;
        this.vehicleType=vehicleType;
        this.price=price;
    }

    // Default constructor.
    public DeliveryPrice() {
    }

    /**
     * Gets the price of delivery.
     * @return The price of delivery. Returns 0.0 if the price is null.
     */
    public double getPrice() {
        return price!=null ? price:-1;
    }

    /**
     * Gets the city for which the delivery price is defined.
     * @return The city.
     */
    public String getCity() {
        return city;
    }

    /**
     * Gets the vehicle type for which the delivery price is defined.
     * @return The vehicle type.
     */
    public String getVehicleType() {
        return vehicleType;
    }

    /**
     * Sets the price of delivery.
     * @param price The price of delivery to set.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the ID of the delivery price.
     * @return The ID.
     */
    public Long getId() {
        return id;
    }
}
