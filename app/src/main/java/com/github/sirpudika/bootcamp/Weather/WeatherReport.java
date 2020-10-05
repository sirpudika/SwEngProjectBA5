package com.github.sirpudika.bootcamp.Weather;

public final class WeatherReport {
    public final double averageTemperature;
    public final double minimumTemperature;
    public final double maximalTemperature;

    public final String weatherType;
    public final String weatherIcon;

    public WeatherReport(double averageTemperature, double minimumTemperature, double maximalTemperature, String weatherType, String weatherIcon) {
        this.averageTemperature = averageTemperature;
        this.minimumTemperature = minimumTemperature;
        this.maximalTemperature = maximalTemperature;
        this.weatherType = weatherType;
        this.weatherIcon = weatherIcon;
    }
}
