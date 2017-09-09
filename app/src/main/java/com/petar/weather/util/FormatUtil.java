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

    public static String formatCoordinates(Location location) {
        String latitude = String.valueOf((float) location.getLatitude());
        String longitude = String.valueOf((float) location.getLongitude());

        return String.format(
                Constants.FORMAT_COORDINATES,
                latitude,
                longitude
        );
    }

    public static String formatDateRequest(Calendar calendar) {
        return DATE_REQUEST_FORMAT.format(calendar.getTime());
    }

    public static String formatForecastDate(AForecast forecast) {
        DateTime applicableDate = new DateTime(forecast.getApplicableDate());
        DateTime created = new DateTime(forecast.getCreatedDate());

        return String.format(
                Constants.FORMAT_TIME_AND_DATE,
                TIME_SHOW_FORMAT.format(created.toDate()),
                DATE_SHOW_FORMAT.format(applicableDate.toDate())
        );
    }

    public static String formatDistance(int distance) {
        if (distance == 0) {
            return "";
        }

        double result = distance / Constants.GENERAL_METERS_INTO_KILOMETER;

        if (result > 0) {
            return String.format(
                    Constants.FORMAT_DISTANCE_KM,
                    Math.round(result)
            );
        } else {
            return String.format(
                    Constants.FORMAT_DISTANCE_M,
                    distance
            );
        }
    }

    public static String formatTemperature(double temp) {
        return String.format(
                Constants.FORMAT_TEMPERATURE_C,
                String.valueOf(Math.round(temp))
        );
    }
}
