package com.github.sirpudika.bootcamp;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.IOException;

import com.github.sirpudika.bootcamp.GeoCoding.Address;
import com.github.sirpudika.bootcamp.GeoCoding.AndroidGeocodingService;
import com.github.sirpudika.bootcamp.GeoCoding.GeocodingService;
import com.github.sirpudika.bootcamp.Location.AndroidLocationService;
import com.github.sirpudika.bootcamp.Location.Location;
import com.github.sirpudika.bootcamp.Location.LocationService;
import com.github.sirpudika.bootcamp.Weather.OpenWeatherMapWeatherService;
import com.github.sirpudika.bootcamp.Weather.WeatherForecast;
import com.github.sirpudika.bootcamp.Weather.WeatherReport;
import com.github.sirpudika.bootcamp.Weather.WeatherService;

public class WeatherActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;

    private LocationService mLocationService;
    private WeatherService mWeatherService;
    private GeocodingService mGeocodingService;
    private TextView mWeatherView;
    private Switch mWeatherUseGps;
    private TextView mWeatherCityName;
    private Button mWeatherQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        mWeatherView = findViewById(R.id.weatherConditionTextView);
        mWeatherUseGps = findViewById(R.id.weatherUseGps);
        mWeatherCityName = findViewById(R.id.weatherCityName);
        mWeatherQuery = findViewById(R.id.weatherQuery);

        // Create weather and location services.
        Criteria criteria = new Criteria();
        criteria.setPowerRequirement(Criteria.POWER_MEDIUM);

        // In an actual app, we will prefer using dependency injection to get these services, as it
        // makes it way easier to test the app, and allows for quick implementation swapping.
        // You'll learn more about that in the testing step of the project!
        mLocationService = AndroidLocationService.buildFromContextAndCriteria(this, criteria);
        mWeatherService = OpenWeatherMapWeatherService.buildFromContext(this);
        mGeocodingService = AndroidGeocodingService.fromContext(this);

        // Initially clear the forecast text.
        mWeatherView.setText("");

        mWeatherQuery.setOnClickListener(v -> {
            // Load the weather on button click.
            loadWeather();
        });

        // Disable the city name text field whenever we use the GPS.
        mWeatherUseGps.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mWeatherCityName.setEnabled(!isChecked);
        });
    }

    private void displayWeather(WeatherForecast forecast, Address address) {
        Log.i("RR", "Retuened");

        mWeatherView.setText(getString(R.string.weather_forecast_format,
                address.toString(", "),
                formatWeatherReport(forecast.getWeatherReport(WeatherForecast.Day.TODAY)),
                formatWeatherReport(forecast.getWeatherReport(WeatherForecast.Day.TOMORROW)),
                formatWeatherReport(forecast.getWeatherReport(WeatherForecast.Day.AFTER_TOMORROW))
        ));
    }

    private String formatWeatherReport(WeatherReport report) {
        return getString(R.string.weather_report_format, report.averageTemperature, report.minimumTemperature, report.maximalTemperature, report.weatherType);
    }

    private void loadWeather() {
        mWeatherView.setText(R.string.weather_loading);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);
            return;
        }


        try {
            Location loc;
            if(mWeatherUseGps.isChecked()) {
                // Here we use the location service to query our current location.
                loc = mLocationService.getLocation();
            } else {
                // Here we use the given city name as our query location
                String cityName = mWeatherCityName.getText().toString();
                loc = mGeocodingService.addressToLocation(cityName);
            }

            WeatherForecast forecast = mWeatherService.getForecast(loc);
            Address address = mGeocodingService.locationToAddress(loc);
            displayWeather(forecast, address);
        } catch (IOException e) {
            Log.e("WeatherActivity", "Error when retrieving forecast.", e);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            loadWeather();
            // We immediately call back the function, if the permission was not granted the prompt will be shown again
            // This is a dirty solution indeed, in the real world one would display an error message and the app
            // would work in a degraded way.
        }
    }
}
