package com.petar.weather.app;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * Created by User on 22.6.2017 г..
 */

public class Constants {

    // API region
    public static final String API_BASE_URL = "https://www.metaweather.com";
    public static final String API_IMAGE_PNG = "/static/img/weather/png/%s.png";
    public static final String API_DATE_REQUEST_FORMAT = "yyyy/MM/dd/";
    public static final long API_REQUEST_TIMEOUT_MILLIS = TimeUnit.SECONDS.toMillis(20);
    public static final long API_HOURLY_FORECAST_LIMIT_MILLIS = TimeUnit.DAYS.toMillis(6);

    /**
     * Based on http://blog.shamanland.com/2016/02/int-string-enum.html
     */
    @StringDef({
            APIWeatherStateSummary.SNOW,
            APIWeatherStateSummary.SLEET,
            APIWeatherStateSummary.HAIL,
            APIWeatherStateSummary.THUNDERSTORM,
            APIWeatherStateSummary.HEAVY_RAIN,
            APIWeatherStateSummary.LIGHT_RAIN,
            APIWeatherStateSummary.SHOWERS,
            APIWeatherStateSummary.HEAVY_CLOUD,
            APIWeatherStateSummary.LIGHT_CLOUD,
            APIWeatherStateSummary.CLEAR
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface APIWeatherStateSummary {
        String SNOW = "sn";
        String SLEET = "sl";
        String HAIL = "h";
        String THUNDERSTORM = "t";
        String HEAVY_RAIN = "hr";
        String LIGHT_RAIN = "lr";
        String SHOWERS = "s";
        String HEAVY_CLOUD = "hc";
        String LIGHT_CLOUD = "lc";
        String CLEAR = "c";
    }
    // End of API region

    // FORMATS region
    public static final String FORMAT_COORDINATES = "%s,%s";
    public static final String FORMAT_DISTANCE_M = "%s m";
    public static final String FORMAT_DISTANCE_KM = "%s km";
    public static final String FORMAT_TEMPERATURE_C = "%s\u00b0C";
    public static final String FORMAT_TIME_AND_DATE = "%s %s";
    public static final String FORMAT_AIR_PRESSURE = "%s mbar";
    public static final String FORMAT_WIND_SPEED = "%s mph";
    public static final String FORMAT_WIND_DIRECTION = "%s\u00b0";
    public static final String FORMAT_HUMIDITY = "%s%%";
    public static final String FORMAT_DOUBLE_VALUES_PRECISION = "%.2f";
    // End of FORMATS region

    // REGEX region
    public static final Pattern REGEX_COORDINATES = Pattern.compile("TODO");
    public static final Pattern REGEX_CITY_NAME = Pattern.compile("TODO");
    // End of REGEX region

    // ANIMATIONS region
    public static final float ANIMATION_ALPHA_SPLASH_SCREEN_START = 0.2f;
    public static final float ANIMATION_ALPHA_SPLASH_SCREEN_END = 1f;
    public static final long ANIMATION_ALPHA_SPLASH_SCREEN_DURATION_MILLIS = TimeUnit.SECONDS.toMillis(2);
    // End of ANIMATIONS region

    // PERMISSIONS region
    public static final int PERMISSION_REQUEST_CODE_ACCESS_FINE_LOCATION = 0;
    // End of PERMISSIONS region

    // LOCATION region
    public static final int LOCATION_REQUEST_CHECK_SETTINGS = 0;
    public static final long LOCATION_UPDATE_INTERVAL_MILLIS = TimeUnit.SECONDS.toMillis(10);
    public static final long LOCATION_FASTEST_UPDATE_INTERVAL_MILLIS = TimeUnit.SECONDS.toMillis(5);
    public static final long LOCATION_TIME_OFFSET_FOR_CACHED_DATA_MILLIS = TimeUnit.MINUTES.toMillis(30);
    // End of LOCATION region

    // NOTIFICATION region
    public static final int NOTIFICATION_PENDING_INTENT_REQUEST_CODE = 0;
    public static final int NOTIFICATION_NOTIFY_ID = 0;
    public static final long NOTIFICATION_INTERVAL_TIME_MILLIS = TimeUnit.HOURS.toMillis(3);
    // End of NOTIFICATION region

    // FORECAST region
    public static final int FORECASTS_FOR_A_DAY = 8;
    public static final long FORECAST_HOURLY_TIME_OFFSET_FOR_CACHED_DATA_MILLIS = TimeUnit.HOURS.toMillis(3);
    public static final long FORECAST_WEEKLY_TIME_OFFSET_FOR_CACHED_DATA_MILLIS = TimeUnit.DAYS.toMillis(1);
    public static final long FORECAST_WEEKLY_TIMESTAMP = Long.MAX_VALUE;
    // End of FORECAST region

    // BUNDLE-KEYS region
    public static final String BUNDLE_FORECAST_DETAILS_KEY = "BUNDLE_FORECAST_DETAILS_KEY";
    public static final String BUNDLE_LOCATION_FROM_SEARCH_KEY = "BUNDLE_LOCATION_FROM_SEARCH_KEY";
    public static final String BUNDLE_RECYCLER_LOADING_ITEM_KEY = "BUNDLE_RECYCLER_LOADING_ITEM_KEY";
    // End of BUNDLE-KEYS region

    // DB region
    public static final String DB_PERSISTENCE_LOGIC_NAME = "DB_PERSISTENCE_LOGIC_NAME";
    public static final int DB_FORECAST_SIZE = 10;
    public static final long DB_CURRENT_LOCATION_KEY = 0;
    public static final long DB_WEEKLY_FORECAST_KEY = Long.MAX_VALUE;
    // End of DB region

    // SHARED_PREFERENCES region
    public static final String SHARED_PREFERENCES_NOTIFICATIONS_ACTIVE_KEY = "SHARED_PREFERENCES_NOTIFICATIONS_ACTIVE_KEY";
    // End of SHARED_PREFERENCES region

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

    // GENERAL/COMMON-SENSE region
    public static final int GENERAL_METERS_INTO_KILOMETER = 1_000;
    // End of GENERAL/COMMON-SENSE region
}
