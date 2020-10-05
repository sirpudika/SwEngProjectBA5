package com.github.sirpudika.bootcamp.Weather;

import java.io.IOException;

import com.github.sirpudika.bootcamp.Location.Location;

public interface WeatherService {
    /**
     * Get the weather forecast at a given location.
     *
     * @param location the location for which you want to get the forecast
     * @return the weather forecast for the given location
     * @throws IOException if there is a network error of any kind
     */
    WeatherForecast getForecast(Location location) throws IOException;

}

