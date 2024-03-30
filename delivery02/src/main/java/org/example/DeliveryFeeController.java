package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


//  Controller class for handling delivery fee calculation requests.
@RestController
public class DeliveryFeeController {

    @Autowired
    private DeliveryService deliveryService;

    /**
     * Handles requests to calculate the delivery fee.
     * @param city (String, optional) - The city for delivery.
     * @param vehicleType (String, optional) - The type of vehicle for delivery.
     * @param request (DeliveryRequest, optional) - The delivery request object.
     * @return A string containing information about the delivery fee.
     */

    @GetMapping("/calculateDeliveryFee")
    public String calculateDeliveryFee(@RequestParam(required = false) String city, @RequestParam(required = false) String vehicleType, @RequestBody(required = false) DeliveryRequest request) {
        try{
            if(request != null){
                return calculateDeliveryFeeByRequestBody(request);
            }else if(city != null && vehicleType != null){
                double deliveryFee = deliveryService.calculateDeliveryFee(city, vehicleType);
                return "Delivery fee for " + vehicleType + " in " + city + " is " + deliveryFee + " EUR";
            }else{
                return "Missing parameters";
            }
        }catch(Exception e) {
            //  Error handling, such as returning an error message
            return "Error calculating delivery fee: " + e.getMessage();
        }catch(DeliveryServiceException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Calculates the delivery fee based on the request body.
     * @param request (DeliveryRequest) - The delivery request object.
     * @return A string containing information about the delivery fee.
     * @throws DeliveryServiceException if an error occurs in the delivery service.
     */
    private String calculateDeliveryFeeByRequestBody(@RequestBody DeliveryRequest request) throws DeliveryServiceException {
        double deliveryFee=deliveryService.calculateDeliveryFee(request.getCity(), request.getVehicleType());
        return "Delivery fee for "+request.getVehicleType()+" in "+request.getCity()+" is " +deliveryFee+" EUR";
    }
}
