package org.example;

import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.persistence.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Timestamp;

// Entity class representing weather observations.
@Entity
@Table(name = "WEATHER_OBSERVATION")
public class WeatherObservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "STATION_NAME")
    private String stationName;
    @Column(name = "WMO_CODE")
    private int wmoCode;
    @Column(name = "AIR_TEMPERATURE")
    private double airTemperature;
    @Column(name = "WIND_SPEED")
    private double windSpeed;
    @Column(name = "PHENOMENON")
    private String phenomenon;
    @Column(name = "OBSERVATION_TIMESTAMP")
    private Timestamp observationTimestamp;

    private static final String URL_WEATHER_SOURCE = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";

    //Default constructor.
    public WeatherObservation() {
    }

    /**
     * Parameterized constructor to initialize weather observation for a specific station.
     * @param _stationName The name of the weather station.
     * @throws ParserConfigurationException If a DocumentBuilder cannot be created.
     * @throws NetworkException If there's an error fetching weather data from the source.
     * @throws SAXException If there's an error parsing the XML data.
     */
    public WeatherObservation(String _stationName) throws ParserConfigurationException, NetworkException, SAXException {
        stationName=_stationName;
        fetchWeatherData();
    }

    /**
     * Overrides toString() method to provide a string representation of the weather observation.
     * @return A string containing weather observation details.
     */
    @Override
    public String toString(){
        return "Weather Observation for " + stationName + " at " + observationTimestamp + ":\n" +
                "WMO code: " + wmoCode + "\n" +
                "Air Temperature: " + airTemperature + " °C\n" +
                "Wind Speed: " + windSpeed + " m/s\n" +
                "Phenomenon: " + phenomenon + "\n";
    }

    /**
     * Fetches weather data from the source and populates the fields of the WeatherObservation object.
     * @throws ParserConfigurationException If a DocumentBuilder cannot be created.
     * @throws SAXException If there's an error parsing the XML data.
     * @throws NetworkException If there's an error fetching weather data from the source.
     */
    public void fetchWeatherData() throws ParserConfigurationException, SAXException, NetworkException {
        try{
            //Send an HTTP GET request to the Estonian Environment Agency's weather portal
            String xmlData = new RestTemplate().getForObject(URL_WEATHER_SOURCE, String.class);

            // Process the received XML response to extract weather data
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlData)));
            NodeList stationList = doc.getElementsByTagName("station");

            // Iterate over the list of weather stations and extract data
            for(int i = 0; i < stationList.getLength(); i++){
                Element station = (Element) stationList.item(i);
                String name = station.getElementsByTagName("name").item(0).getTextContent();

                if(stationName.equals(name)) {
                    wmoCode = Integer.parseInt(station.getElementsByTagName("wmocode").item(0).getTextContent());
                    airTemperature = Double.parseDouble(station.getElementsByTagName("airtemperature").item(0).getTextContent());
                    windSpeed = Double.parseDouble(station.getElementsByTagName("windspeed").item(0).getTextContent());
                    phenomenon = station.getElementsByTagName("phenomenon").item(0).getTextContent();

                    String timestampStr = station.getParentNode().getAttributes().getNamedItem("timestamp").getTextContent();
                    observationTimestamp = new Timestamp(Long.parseLong(timestampStr) * 1000);

                    return;
                }
            }

            //If data is not found, throw an IllegalArgumentException
            throw new IllegalArgumentException("Station " + stationName + " not found in XML");
        }catch(IOException e){
            throw new NetworkException("Error fetching weather data: " + e.getMessage(), e);
        }catch(ParserConfigurationException | SAXException e){
            throw e;
        }
    }

    /**
     * Getter for air temperature.
     * @return The air temperature.
     */
    public double getAirTemperature() {
        return airTemperature;
    }

    /**
     * Getter for wind speed.
     * @return The wind speed.
     */
    public double getWindSpeed() {
        return windSpeed;
    }

    /**
     * Getter for phenomenon.
     * @return The weather phenomenon.
     */
    public String getPhenomenon() {
        return phenomenon;
    }
}