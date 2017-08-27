package com.petar.weather.util;

/**
 * Created by User on 22.6.2017 г..
 */

public class Constants {

    // API
    public static final String API_BASE_URL = "https://www.metaweather.com";
    public static final String API_IMAGE_PNG = "/static/img/weather/png/%s.png";
    public static final String API_DATE_REQUEST_FORMAT = "yyyy/MM/dd/";
    public static final long API_REQUEST_TIMEOUT_IN_SECONDS = 20;

    // Permissions
    public static final int REQUEST_CODE_ACCESS_FINE_LOCATION = 0;

    // Location
    public static final int MIN_TIME_LOCATION_UPDATE = 0;
    public static final int MIN_DISTANCE_LOCATION_UPDATE = 0;

    // Forecast
    public static final int FORECASTS_FOR_A_DAY = 8;
    public static final long FORECAST_WEEKLY_TIMESTAMP = Long.MAX_VALUE;

    // Forecast recycler items
    public static final int FORECAST_RECYCLER_ITEM = 0;
    public static final int FORECAST_LOADING_RECYCLER_ITEM = 1;
    public static final int LOCATION_RECYCLER_ITEM = 2;

    // View pager for fragments
    public static final int VIEW_PAGER_FRAGMENT_COUNT = 3;
    public static final int HOURLY_FORECAST_FRAGMENT_POSITION = 0;
    public static final int DAILY_FORECAST_FRAGMENT_POSITION = 1;

    // Bundle keys
    public static final String FORECAST_DETAILS_KEY = "forecast.details.key";
    public static final String LOCATION_FROM_SEARCH_KEY = "location.from.search.key";

    // DB specific
    public static final String DB_NAME = "com.petar.weather.persistence";
    public static final int DB_FORECAST_SIZE = 5;

    // DB keys
    public static final long DB_CURRENT_LOCATION_KEY = 0;
    public static final long DB_WEEKLY_FORECAST_KEY = Long.MAX_VALUE;

    // Time offsets
    public static final int OFFSET_MINUTES_FOR_LOCATION = 30;
    public static final int OFFSET_HOURS_FOR_FORECAST = 3;
    public static final int OFFSET_DAYS_FOR_FORECAST = 6;
    public static final int OFFSET_DAYS_FOR_WEEKLY_FORECAST = 1;
}
