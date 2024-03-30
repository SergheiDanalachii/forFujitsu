package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Controller class for handling operations related to delivery prices.
@RestController
@RequestMapping("/delivery-prices")
public class DeliveryPriceController{

    @Autowired
    private DeliveryPriceRepository deliveryPriceRepository;

    /**
     * Adds a new delivery entry.
     * @param request The request containing the delivery price information.
     * @return ResponseEntity containing a success message if the delivery entry is added successfully.
     */
    @PostMapping("/add")
    public ResponseEntity<String> addDeliveryPrice(@RequestBody DeliveryPrice request){
        try{
            validateDeliveryPriceRequest(request);

            DeliveryPrice savedPrice = deliveryPriceRepository.save(request);
            return ResponseEntity.ok("Delivery price added successfully with ID: " + savedPrice.getId());
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body("Error adding delivery price: " + e.getMessage());
        }
    }

    /**
     * Updates an existing delivery price.
     * @param request The request containing the updated delivery price information.
     * @return ResponseEntity containing a success message if the delivery price is updated successfully.
     */
    @PostMapping("/update")
    public ResponseEntity<String> updateDeliveryPrice(@RequestBody DeliveryPrice request){
        try{
            validateDeliveryPriceRequest(request);

            DeliveryPrice existingPrice=deliveryPriceRepository.findByCityAndVehicleType(request.getCity(), request.getVehicleType());
            if (existingPrice!=null) {
                existingPrice.setPrice(request.getPrice());
                deliveryPriceRepository.save(existingPrice);
                return ResponseEntity.ok("Delivery price updated successfully for city: "+request.getCity()+" and vehicle type: "+request.getVehicleType());
            }else{
                return ResponseEntity.notFound().build();
            }
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body("Error updating delivery price: "+e.getMessage());
        }
    }

    /**
     * Validates the delivery price request.
     * @param request The delivery price request to validate.
     * @throws IllegalArgumentException if the request is invalid.
     */
    private void validateDeliveryPriceRequest(DeliveryPrice request) {
        if(request == null){
            throw new IllegalArgumentException("Delivery price request cannot be null");
        }
        if(request.getCity()==null || request.getVehicleType()==null || request.getPrice()==0.0){
            throw new IllegalArgumentException("City, vehicle type, and price are required");
        }
    }
}