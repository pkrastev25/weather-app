package com.petar.weather.util;

import android.location.Location;

import com.petar.weather.app.Constants;
import com.petar.weather.logic.models.AForecast;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Helper class for manipulating all formats inside the application.
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 23.6.2017
 */
public class FormatUtil {

    /**
     * A superset of {@link Constants#API_DATE_REQUEST_FORMAT}, takes into consideration the {@link Locale}.
     */
    private static final DateFormat DATE_REQUEST_FORMAT = new SimpleDateFormat(Constants.API_DATE_REQUEST_FORMAT, Locale.getDefault());

    /**
     * A superset of {@link Constants#FORMAT_DATE}, takes into consideration the {@link Locale}.
     */
    private static final DateTimeFormatter DATE_SHOW_FORMAT = DateTimeFormat.forStyle(Constants.FORMAT_DATE).withLocale(Locale.getDefault());

    /**
     * A superset of {@link Constants#FORMAT_TIME}, takes into consideration the {@link Locale}.
     */
    private static final DateTimeFormatter TIME_SHOW_FORMAT = DateTimeFormat.forStyle(Constants.FORMAT_TIME).withLocale(Locale.getDefault());

    /**
     * Formats the coordinates of the location, specified by {@link Constants#FORMAT_COORDINATES}.
     *
     * @param location Location to be formatted
     * @return Formatted coordinates
     */
    public static String formatCoordinates(Location location) {
        String latitude = String.valueOf((float) location.getLatitude());
        String longitude = String.valueOf((float) location.getLongitude());

        return String.format(
                Constants.FORMAT_COORDINATES,
                latitude,
                longitude
        );
    }

    /**
     * Formats the calendar into a string, specified by {@link #DATE_REQUEST_FORMAT}.
     *
     * @param calendar Calendar to be formatted
     * @return Formatted date request
     */
    public static String formatDateRequest(Calendar calendar) {
        return DATE_REQUEST_FORMAT.format(calendar.getTime());
    }

    /**
     * Formats the forecast date into a string, specified by {@link Constants#FORMAT_TIME_AND_DATE}.
     *
     * @param forecast Forecast to be formatted
     * @return Formatted date
     */
    public static String formatForecastDate(AForecast forecast) {
        long applicableDate = TimeUtil.convertDateFromISOString(forecast.getApplicableDate());
        long created = TimeUtil.convertDateFromISOString(forecast.getCreatedDate());

        return String.format(
                Constants.FORMAT_TIME_AND_DATE,
                TIME_SHOW_FORMAT.print(created),
                DATE_SHOW_FORMAT.print(applicableDate)
        );
    }

    /**
     * Formats the distance into a string, specified by {@link Constants#FORMAT_DISTANCE_M}
     * or {@link Constants#FORMAT_DISTANCE_KM}.
     *
     * @param distance Distance to be formatted
     * @return Formatted distance
     */
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

    /**
     * Formats the wind speed into a string, specified by {@link Constants#FORMAT_WIND_SPEED}.
     *
     * @param windSpeed Wind speed to be formatted
     * @return Formatted wind speed
     */
    public static String formatWindSpeed(double windSpeed) {
        return String.format(
                Constants.FORMAT_WIND_SPEED,
                String.format(Locale.getDefault(), Constants.FORMAT_DOUBLE_VALUES_PRECISION, windSpeed)
        );
    }

    /**
     * Formats the wind direction into a string, specified by {@link Constants#FORMAT_WIND_DIRECTION}.
     *
     * @param windDirection Wind direction to be formatted
     * @return Formatted wind direction
     */
    public static String formatWindDirection(double windDirection) {
        return String.format(
                Constants.FORMAT_WIND_DIRECTION,
                String.format(Locale.getDefault(), Constants.FORMAT_DOUBLE_VALUES_PRECISION, windDirection)
        );
    }

    /**
     * Formats the temperature into a string, specified by {@link Constants#FORMAT_TEMPERATURE_C}.
     *
     * @param temp Temperature to be formatted
     * @return Formatted temperature
     */
    public static String formatTemperature(double temp) {
        return String.format(
                Constants.FORMAT_TEMPERATURE_C,
                String.valueOf(Math.round(temp))
        );
    }

    /**
     * Formats the minimum and maximum temperature into a string according to
     * {@link Constants#FORMAT_MIN_MAX_TEMPERATURE_C}.
     *
     * @param min Formatted minimum temperature according to {@link Constants#FORMAT_TEMPERATURE_C}
     * @param max Formatted maximum temperature according to {@link Constants#FORMAT_TEMPERATURE_C}
     * @return Formatted temperature
     */
    public static String formatMinMaxTemperature(double min, double max) {
        return String.format(
                Constants.FORMAT_MIN_MAX_TEMPERATURE_C,
                formatTemperature(min),
                formatTemperature(max)
        );
    }

    /**
     * Formats the air pressure into a string, specified by {@link Constants#FORMAT_AIR_PRESSURE}.
     *
     * @param airPressure Air pressure to be formatted
     * @return Formatted air pressure
     */
    public static String formatAirPressure(double airPressure) {
        return String.format(
                Constants.FORMAT_AIR_PRESSURE,
                String.format(Locale.getDefault(), Constants.FORMAT_DOUBLE_VALUES_PRECISION, airPressure)
        );
    }

    /**
     * Formats the humidity into a string, specified by {@link Constants#FORMAT_HUMIDITY}.
     *
     * @param humidity Humidity to be formatted
     * @return Formatted humidity
     */
    public static String formatHumidity(double humidity) {
        return String.format(
                Constants.FORMAT_HUMIDITY,
                String.format(Locale.getDefault(), Constants.FORMAT_DOUBLE_VALUES_PRECISION, humidity)
        );
    }
}
