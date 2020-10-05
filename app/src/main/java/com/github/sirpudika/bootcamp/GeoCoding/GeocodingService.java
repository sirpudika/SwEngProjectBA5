package com.github.sirpudika.bootcamp.GeoCoding;

import com.github.sirpudika.bootcamp.Location.Location;

import androidx.annotation.NonNull;

import java.io.IOException;

public interface GeocodingService {
    Address locationToAddress(@NonNull Location location) throws IOException;

    Location addressToLocation(@NonNull String address) throws IOException;
}
