package org.example;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Repository interface for WeatherObservation entity.
@Repository
public interface WeatherObservationRepository extends JpaRepository<WeatherObservation, Long> {

    /**
     * Retrieves the latest weather observation for a given station name.
     * @param stationName The name of the weather station.
     * @return The latest weather observation.
     */
    WeatherObservation findTopByStationNameOrderByObservationTimestampDesc(String stationName);
}