package com.petar.weather.util;

import android.location.Location;

/**
 * Created by User on 23.6.2017 г..
 */

public class FormatUtil {

    public static String formatCoordinates(Location location) {
        String latitude = String.valueOf((float) location.getLatitude());
        String longitude = String.valueOf((float) location.getLongitude());

        return latitude + "," + longitude;
    }
}
