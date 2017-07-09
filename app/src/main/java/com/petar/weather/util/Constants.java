package com.petar.weather.util;

/**
 * Created by User on 22.6.2017 г..
 */

public class Constants {

    // API
    public static final String API_BASE_URL = "https://www.metaweather.com";
    public static final String API_IMAGE_PNG = "/static/img/weather/png/{type}.png";

    // Permissions
    public static final int REQUEST_CODE_ACCESS_FINE_LOCATION = 0;

    // Location
    public static final int MIN_TIME_LOCATION_UPDATE = 0;
    public static final int MIN_DISTANCE_LOCATION_UPDATE = 0;

    // Forecast
    public static final int FORECASTS_FOR_A_DAY = 8;

    // View pager for fragments
    public static final int VIEW_PAGER_FRAGMENT_COUNT = 3;
    public static final int HOURLY_FORECAST_FRAGMENT_POSITION = 0;
    public static final int DAILY_FORECAST_FRAGMENT_POSITION = 1;

    // Bundle keys
    public static final String FORECAST_DETAILS_KEY = "forecast.details.key";
}
