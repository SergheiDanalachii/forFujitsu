package org.example.tests;

import org.example.WeatherDataImporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.example.WeatherObservationRepository;


public class WeatherDataImporterTest {

    @Mock
    private WeatherObservationRepository weatherObservationRepository;

    @InjectMocks
    private WeatherDataImporter importer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testImportWeatherData() {

        importer.importWeatherData();

    }
}
