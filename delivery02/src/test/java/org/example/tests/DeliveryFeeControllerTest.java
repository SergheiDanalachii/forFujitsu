package org.example.tests;

import org.example.NetworkException;
import org.example.WeatherObservation;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WeatherObservationTest {

    @Test
    void testFetchWeatherData() {
        try {
            WeatherObservation weatherObservation = new WeatherObservation("Tallinn-Harku");
            assertNotNull(weatherObservation);
            assertNotNull(weatherObservation.getAirTemperature());
            assertNotNull(weatherObservation.getWindSpeed());
            assertNotNull(weatherObservation.getPhenomenon());
        } catch (ParserConfigurationException | NetworkException | SAXException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }
}
