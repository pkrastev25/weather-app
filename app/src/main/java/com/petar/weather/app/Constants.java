package com.petar.weather.app;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.regex.Pattern;

/**
 * Created by User on 22.6.2017 г..
 */

public class Constants {

    // API region
    public static final String API_BASE_URL = "https://www.metaweather.com";
    public static final String API_IMAGE_PNG = "/static/img/weather/png/%s.png";
    public static final String API_DATE_REQUEST_FORMAT = "yyyy/MM/dd/";
    public static final long API_REQUEST_TIMEOUT_IN_SECONDS = 20;
    // End of API region

    // FORMATS region
    public static final String FORMAT_COORDINATES = "%s,%s";
    public static final String FORMAT_DISTANCE_M = "%s m";
    public static final String FORMAT_DISTANCE_KM = "%s km";
    public static final String FORMAT_TEMPERATURE_C = "%s C";
    public static final String FORMAT_TIME_AND_DATE = "%s %s";
    // End of FORMATS region

    // REGEX region
    public static final Pattern REGEX_COORDINATES = Pattern.compile("TODO");
    public static final Pattern REGEX_CITY_NAME = Pattern.compile("TODO");
    // End of REGEX region

    // ANIMATIONS region
    public static final float ANIMATION_ALPHA_SPLASH_SCREEN_START = 0.2f;
    public static final float ANIMATION_ALPHA_SPLASH_SCREEN_END = 1f;
    public static final int ANIMATION_ALPHA_SPLASH_SCREEN_DURATION = 3000;
    // End of ANIMATIONS region

    // PERMISSIONS region
    public static final int PERMISSION_REQUEST_CODE_ACCESS_FINE_LOCATION = 0;
    // End of PERMISSIONS region

    // FORECAST region
    public static final int FORECASTS_FOR_A_DAY = 8;
    public static final long FORECAST_WEEKLY_TIMESTAMP = Long.MAX_VALUE;
    // End of FORECAST region

    // BUNDLE-KEYS region
    public static final String BUNDLE_FORECAST_DETAILS_KEY = "bundle.forecast.details.key";
    public static final String BUNDLE_LOCATION_FROM_SEARCH_KEY = "bundle.location.from.search.key";
    public static final String BUNDLE_RECYCLER_LOADING_ITEM_KEY = "bundle.recycler.loading.item.key";
    // End of BUNDLE-KEYS region

    // DB region
    public static final String DB_NAME = "com.petar.weather.persistence";
    public static final int DB_FORECAST_SIZE = 10;
    public static final long DB_CURRENT_LOCATION_KEY = 0;
    public static final long DB_WEEKLY_FORECAST_KEY = Long.MAX_VALUE;
    // End of DB region

    // TIME-OFFSETS region
    public static final int OFFSET_MINUTES_FOR_LOCATION = 30;
    public static final int OFFSET_HOURS_FOR_FORECAST = 3;
    public static final int OFFSET_DAYS_FOR_FORECAST = 6;
    public static final int OFFSET_DAYS_FOR_WEEKLY_FORECAST = 1;
    // End of TIME-OFFSETS region

    // VIEW-PAGER-FRAGMENT region
    /**
     * Based on http://blog.shamanland.com/2016/02/int-string-enum.html
     */
    @IntDef({
            ViewPagerFragmentPositions.HOURLY_FORECAST,
            ViewPagerFragmentPositions.DAILY_FORECAST,
            ViewPagerFragmentPositions.SETTINGS
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewPagerFragmentPositions {
        int HOURLY_FORECAST = 0;
        int DAILY_FORECAST = 1;
        int SETTINGS = 2;
    }

    public static final int VIEW_PAGER_FRAGMENT_COUNT = 3;
    // End of VIEW-PAGER-FRAGMENT region

    /**
     * Based on http://blog.shamanland.com/2016/02/int-string-enum.html
     */
    @IntDef({
            RecyclerItems.FORECAST_ITEM,
            RecyclerItems.LOADING_ITEM,
            RecyclerItems.LOCATION_ITEM
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface RecyclerItems {
        int FORECAST_ITEM = 0;
        int LOADING_ITEM = 1;
        int LOCATION_ITEM = 2;
    }

    /**
     * Based on http://blog.shamanland.com/2016/02/int-string-enum.html
     */
    @StringDef({
            ErrorHandling.NO_INTERNET_CONNECTION,
            ErrorHandling.NO_RESULTS_FOR_REQUEST,
            ErrorHandling.CANNOT_UPDATE_CACHED_DATA,
            ErrorHandling.NO_SEARCH_INPUT,
            ErrorHandling.WRONG_SEARCH_INPUT,
            ErrorHandling.DEFAULT
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ErrorHandling {
        String NO_INTERNET_CONNECTION = "NO_INTERNET_CONNECTION";
        String NO_RESULTS_FOR_REQUEST = "NO_RESULTS_FOR_REQUEST";
        String CANNOT_UPDATE_CACHED_DATA = "CANNOT_UPDATE_CACHED_DATA";
        String NO_SEARCH_INPUT = "NO_SEARCH_INPUT";
        String WRONG_SEARCH_INPUT = "WRONG_SEARCH_INPUT";
        String DEFAULT = "DEFAULT";
    }

    // GENERAL (common sense) region
    public static final int GENERAL_METERS_INTO_KILOMETER = 1000;
    // End of GENERAL (common sense) region
}
