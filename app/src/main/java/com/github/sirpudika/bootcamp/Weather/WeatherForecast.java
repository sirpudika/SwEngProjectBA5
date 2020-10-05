package com.github.sirpudika.bootcamp.Weather;


import androidx.annotation.NonNull;

public class WeatherForecast {
    private final WeatherReport[] reports;

    /**
     * Instantiate a forecast
     *
     * @param reports three weather reports, one per day.
     *                reports[0] must be today's report
     *                reports[1] must be tomorrow's report
     *                reports[2] must be the report of the day after tomorrow
     */
    WeatherForecast(WeatherReport[] reports) {
        if (reports.length < 3) {
            throw new IllegalArgumentException("reports array must contain at least 3 elements.");
        }

        this.reports = reports;
    }

    public enum Day {
        TODAY, TOMORROW, AFTER_TOMORROW;
    }

    /**
     * Get the weather report for a specific day
     *
     * @param offset the day for which you need the report
     * @return the weather report for that day
     */
    public WeatherReport getWeatherReport(@NonNull Day offset) {
        return this.reports[offset.ordinal()];
    }

}
