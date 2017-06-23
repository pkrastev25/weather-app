package com.petar.weather.util;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by User on 23.6.2017 г..
 */

public class FormatUtil {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd/", Locale.getDefault());

    public static String formatCoordinates(Location location) {
        String latitude = String.valueOf((float) location.getLatitude());
        String longitude = String.valueOf((float) location.getLongitude());

        return latitude + "," + longitude;
    }

    public static String formatDate(Calendar calendar) {
        return DATE_FORMAT.format(calendar.getTime());
    }
}
