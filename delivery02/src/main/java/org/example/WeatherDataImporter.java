package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//Component responsible for importing weather data at scheduled intervals.
@Component
public class WeatherDataImporter {

    @Autowired
    private WeatherObservationRepository weatherObservationRepository;
    private static final Logger logger = LoggerFactory.getLogger(WeatherDataImporter.class);


    /**
     * Scheduled method to import weather data from a portal.
     * Runs  every hour at 15 minutes past the hour.
     */
    @Scheduled(cron = "0 15 * * * *")
    public void importWeatherData(){
        try{
            // Extracting new weather data from the portal
            WeatherObservation tallinnObservation = new WeatherObservation("Tallinn-Harku");
            WeatherObservation tartuObservation = new WeatherObservation("Tartu-Tõravere");
            WeatherObservation parnuObservation = new WeatherObservation("Pärnu");

            // Saving new data to the database
            weatherObservationRepository.save(tallinnObservation);
            weatherObservationRepository.save(tartuObservation);
            weatherObservationRepository.save(parnuObservation);

        }catch (Exception e){
            e.printStackTrace();
            logger.error("An error occurred while importing weather data: " + e.getMessage());
        }
    }
}