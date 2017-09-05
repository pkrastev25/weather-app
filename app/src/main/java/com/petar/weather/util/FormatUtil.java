package com.petar.weather.util;

import android.location.Location;

import com.petar.weather.app.Constants;
import com.petar.weather.logic.models.AForecast;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by User on 23.6.2017 г..
 */

public class FormatUtil {

    private static final DateFormat DATE_REQUEST_FORMAT = new SimpleDateFormat(Constants.API_DATE_REQUEST_FORMAT, Locale.getDefault());
    private static final DateFormat DATE_SHOW_FORMAT = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
    private static final DateFormat TIME_SHOW_FORMAT = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault());

    public static String coordinatesFormat(Location location) {
        String latitude = String.valueOf((float) location.getLatitude());
        String longitude = String.valueOf((float) location.getLongitude());

        return latitude + "," + longitude;
    }

    public static String dateRequestFormat(Calendar calendar) {
        return DATE_REQUEST_FORMAT.format(calendar.getTime());
    }

    public static String forecastDateFormat(AForecast forecast) {
        DateTime applicableDate = new DateTime(forecast.getApplicableDate());
        DateTime created = new DateTime(forecast.getCreatedDate());

        return TIME_SHOW_FORMAT.format(created.toDate()) + " " + DATE_SHOW_FORMAT.format(applicableDate.toDate());
    }

    public static String formatDistance(int distance) {
        String formattedDistance = "";

        if (distance != 0) {
            formattedDistance = distance + "m";
        }

        return formattedDistance;
    }

    public static String formatTemperature(double temp) {
        return String.valueOf(Math.round(temp));
    }
}
