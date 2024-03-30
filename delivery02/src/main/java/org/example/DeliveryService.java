package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//Service responsible for calculating delivery costs.
@Service
public class DeliveryService {

    private final DeliveryPriceRepository deliveryPriceRepository;
    private final WeatherObservationRepository weatherObservationRepository;

    private static final Logger logger=LoggerFactory.getLogger(DeliveryService.class);

    /**
     * Constructor for the DeliveryService class.
     *
     * @param deliveryPriceRepository      The repository for delivery prices.
     * @param weatherObservationRepository The repository for weather observations.
     */
    @Autowired
    public DeliveryService(DeliveryPriceRepository deliveryPriceRepository, WeatherObservationRepository weatherObservationRepository) {
        this.deliveryPriceRepository=deliveryPriceRepository;
        this.weatherObservationRepository=weatherObservationRepository;
    }

    /**
     * Calculates the delivery fee.
     *
     * @param city        The delivery city.
     * @param vehicleType The type of vehicle.
     * @return The delivery fee.
     * @throws DeliveryServiceException If an error occurs during calculation.
     */
    public double calculateDeliveryFee(String city, String vehicleType) throws DeliveryServiceException {
        try{
            WeatherObservation latestWeather=weatherObservationRepository.findTopByStationNameOrderByObservationTimestampDesc(city);
            if(latestWeather!=null){
                double airTemperature=latestWeather.getAirTemperature();
                double windSpeed=latestWeather.getWindSpeed();
                String weatherPhenomenon=latestWeather.getPhenomenon();

                double baseFee=calculateBaseFee(city, vehicleType);
                double additionalFees=calculateAdditionalFees(vehicleType, airTemperature, windSpeed, weatherPhenomenon);

                return baseFee+additionalFees;
            }else{
                throw new DeliveryServiceException("No weather data found for city: "+city);
            }
        }catch(Exception e) {
            logger.error("Error occurred while calculating delivery fee: "+e.getMessage(), e);
            throw new DeliveryServiceException("Failed to calculate delivery fee. Please try again later.", e);
        }
    }

    /**
     * Calculates the base delivery fee.
     *
     * @param city        The delivery city.
     * @param vehicleType The type of vehicle.
     * @return The base delivery fee.
     * @throws DeliveryPriceNotFoundException If no delivery price is found for the specified city and vehicle type.
     */
    public double calculateBaseFee(String city, String vehicleType) throws DeliveryPriceNotFoundException{
        DeliveryPrice deliveryPrice=deliveryPriceRepository.findByCityAndVehicleType(city, vehicleType);
        if(deliveryPrice!=null) {
            return deliveryPrice.getPrice();
        } else {
            String errorMessage = "Delivery price not found for city: "+city+" and vehicle type: "+vehicleType;
            logger.error(errorMessage);
            throw new DeliveryPriceNotFoundException(errorMessage);
        }
    }

    /**
     * Calculates additional delivery fees based on weather conditions and vehicle type.
     *
     * @param vehicleType       The type of vehicle.
     * @param airTemperature    The air temperature.
     * @param windSpeed         The wind speed.
     * @param weatherPhenomenon The weather phenomenon.
     * @return The additional delivery fees.
     */
    private double calculateAdditionalFees(String vehicleType, double airTemperature, double windSpeed, String weatherPhenomenon) {
        double additionalFees = 0.0;
        if((vehicleType.equals("Scooter") || vehicleType.equals("Bike")) && airTemperature < -10) {
            additionalFees += 1.0;
        }else if((vehicleType.equals("Scooter") || vehicleType.equals("Bike")) && airTemperature >= -10 && airTemperature < 0) {
            additionalFees += 0.5;
        }
        if(vehicleType.equals("Bike") && windSpeed >= 10 && windSpeed <= 20) {
            additionalFees += 0.5;
        }else if(vehicleType.equals("Bike") && windSpeed > 20) {
            logger.warn("Usage of selected vehicle type is forbidden due to high wind speed");
        }
        if((vehicleType.equals("Scooter") || vehicleType.equals("Bike")) && (weatherPhenomenon.equals("Snow") || weatherPhenomenon.equals("Sleet"))) {
            additionalFees += 1.0;
        }else if((vehicleType.equals("Scooter") || vehicleType.equals("Bike")) && weatherPhenomenon.equals("Rain")) {
            additionalFees += 0.5;
        }else if((vehicleType.equals("Scooter") || vehicleType.equals("Bike")) && (weatherPhenomenon.equals("Glaze") || weatherPhenomenon.equals("Hail") || weatherPhenomenon.equals("Thunder"))) {
            logger.warn("Usage of selected vehicle type is forbidden due to adverse weather conditions");
        }
        return additionalFees;
    }
}
