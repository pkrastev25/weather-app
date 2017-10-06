package com.petar.weather.app;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.StringDef;
import android.widget.TextView;

import com.petar.weather.persistence.PersistenceLogic;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * Container for all constants used within the application.
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 22.6.2017
 */
public class Constants {

    // --------------------------------------------------------
    // API region
    // --------------------------------------------------------

    /**
     * Contains the base URL for the API.
     */
    public static final String API_BASE_URL = "https://www.metaweather.com";

    /**
     * Specifies the format, in which the date must be in order to carry a forecast
     * request.
     *
     * @see com.petar.weather.logic.DataLogic#getLocationHourlyForecastForDate(Context, int, String, boolean)
     */
    public static final String API_DATE_REQUEST_FORMAT = "yyyy/MM/dd/";

    /**
     * Specifies the time after which an unresolved API connection/request must terminate.
     */
    public static final long API_REQUEST_TIMEOUT_MILLIS = TimeUnit.SECONDS.toMillis(20);

    /**
     * Specifies the number of days, from today, after which the API does not have forecast
     * data.
     */
    public static final long API_HOURLY_FORECAST_LIMIT_MILLIS = TimeUnit.DAYS.toMillis(6);

    /**
     * Maps the short form of the forecast state from the API.
     *
     * @see <a href="http://blog.shamanland.com/2016/02/int-string-enum.html">http://blog.shamanland.com/2016/02/int-string-enum.html</a>
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

        /**
         * Represents the short form of snow weather conditions.
         */
        String SNOW = "sn";

        /**
         * Represents the short form of sleet weather conditions.
         */
        String SLEET = "sl";

        /**
         * Represents the short form of hail weather conditions.
         */
        String HAIL = "h";

        /**
         * Represents the short form of thunderstorm weather conditions.
         */
        String THUNDERSTORM = "t";

        /**
         * Represents the short form of heavy rain weather conditions.
         */
        String HEAVY_RAIN = "hr";

        /**
         * Represents the short form of light rain weather conditions.
         */
        String LIGHT_RAIN = "lr";

        /**
         * Represents the short form of showers weather conditions.
         */
        String SHOWERS = "s";

        /**
         * Represents the short form of heavy cloud weather conditions.
         */
        String HEAVY_CLOUD = "hc";

        /**
         * Represents the short form of light cloud weather conditions.
         */
        String LIGHT_CLOUD = "lc";

        /**
         * Represents the short form of clear weather conditions.
         */
        String CLEAR = "c";
    }

    // --------------------------------------------------------
    // End of API region
    // --------------------------------------------------------

    // --------------------------------------------------------
    // FORMATS region
    // --------------------------------------------------------

    /**
     * Specifies the coordinates format, where the 1st param is latitude, 2nd - longitude.
     */
    public static final String FORMAT_COORDINATES = "%s,%s";

    /**
     * Specifies the distance format in meters.
     */
    public static final String FORMAT_DISTANCE_M = "%s m";

    /**
     * Specifies the distance format in kilometers.
     */
    public static final String FORMAT_DISTANCE_KM = "%s km";

    /**
     * Specifies the temperature format in Celsius.
     */
    public static final String FORMAT_TEMPERATURE_C = "%s\u00b0C";

    /**
     * Specifies the minimum and maximum temperature format, where the 1st param
     * is the min temperature in the form of {@link #FORMAT_TEMPERATURE_C} and the
     * 2nd param is the max temperature in the form of {@link #FORMAT_TEMPERATURE_C}.
     */
    public static final String FORMAT_MIN_MAX_TEMPERATURE_C = "%s | %s";

    /**
     * Specifies the time format.
     *
     * @see org.joda.time.format.DateTimeFormat#forStyle(String)
     */
    public static final String FORMAT_TIME = "-S";

    /**
     * Specifies the date format.
     *
     * @see org.joda.time.format.DateTimeFormat#forStyle(String)
     */
    public static final String FORMAT_DATE = "M-";

    /**
     * Specifies the date format, where the 1st param is the formatted time in the form of
     * {@link #FORMAT_TIME}, 2nd - formatted date in the form of {@link #FORMAT_DATE}.
     */
    public static final String FORMAT_TIME_AND_DATE = "%s / %s";

    /**
     * Specifies the air pressure format in millibar.
     */
    public static final String FORMAT_AIR_PRESSURE = "%s mbar";

    /**
     * Specifies the wind speed format in miles per hour.
     */
    public static final String FORMAT_WIND_SPEED = "%s mph";

    /**
     * Specifies the wind direction format in degrees.
     */
    public static final String FORMAT_WIND_DIRECTION = "%s\u00b0";

    /**
     * Specifies the humidity format in percentage.
     */
    public static final String FORMAT_HUMIDITY = "%s%%";

    /**
     * Specifies the precision format of all fractions within the application.
     */
    public static final String FORMAT_DOUBLE_VALUES_PRECISION = "%.2f";

    // --------------------------------------------------------
    // End of FORMATS region
    // --------------------------------------------------------

    // --------------------------------------------------------
    // REGEX region
    // --------------------------------------------------------

    /**
     * A regex used to match the input coordinates to.
     */
    public static final Pattern REGEX_COORDINATES = Pattern.compile("TODO");

    /**
     * A regex used to match the input string name of a location to.
     */
    public static final Pattern REGEX_CITY_NAME = Pattern.compile("TODO");

    // --------------------------------------------------------
    // End of REGEX region
    // --------------------------------------------------------

    // --------------------------------------------------------
    // ANIMATIONS region
    // --------------------------------------------------------

    /**
     * Specifies the initial alpha/opacity value of the splash screen animation.
     */
    public static final float ANIMATION_ALPHA_SPLASH_SCREEN_START = 0.2f;

    /**
     * Specifies the last alpha/opacity value of the splash screen animation.
     */
    public static final float ANIMATION_ALPHA_SPLASH_SCREEN_END = 1f;

    /**
     * Specifies the duration of the splash screen alpha animation which transitions from
     * {@link #ANIMATION_ALPHA_SPLASH_SCREEN_START} to {@link #ANIMATION_ALPHA_SPLASH_SCREEN_END}.
     */
    public static final long ANIMATION_ALPHA_SPLASH_SCREEN_DURATION_MILLIS = TimeUnit.SECONDS.toMillis(2);

    // --------------------------------------------------------
    // End of ANIMATIONS region
    // --------------------------------------------------------

    // --------------------------------------------------------
    // PERMISSIONS region
    // --------------------------------------------------------

    /**
     * A numeric constant used to determine whether the user gave location permissions to the
     * application.
     */
    public static final int PERMISSION_REQUEST_CODE_ACCESS_FINE_LOCATION = 0;

    // --------------------------------------------------------
    // End of PERMISSIONS region
    // --------------------------------------------------------

    // --------------------------------------------------------
    // LOCATION region
    // --------------------------------------------------------

    /**
     * A numeric constant used to determine whether the user enabled the needed location
     * settings for finding the current location.
     */
    public static final int LOCATION_REQUEST_CHECK_SETTINGS = 0;

    /**
     * Specifies the time interval for the location updates.
     */
    public static final long LOCATION_UPDATE_INTERVAL_MILLIS = TimeUnit.SECONDS.toMillis(10);

    /**
     * Specifies the minimum time interval for the location updates.
     */
    public static final long LOCATION_FASTEST_UPDATE_INTERVAL_MILLIS = TimeUnit.SECONDS.toMillis(5);

    /**
     * Specifies the time after which the cached location data should be updated with a
     * new one.
     */
    public static final long LOCATION_TIME_OFFSET_FOR_CACHED_DATA_MILLIS = TimeUnit.MINUTES.toMillis(30);

    // --------------------------------------------------------
    // End of LOCATION region
    // --------------------------------------------------------

    // --------------------------------------------------------
    // NOTIFICATION region
    // --------------------------------------------------------

    /**
     * A numeric constant used to identify a unique {@link android.app.PendingIntent}.
     * Used mainly bu {@link com.petar.weather.util.AlarmManagerUtil#stopAlarm(Context)}.
     */
    public static final int NOTIFICATION_PENDING_INTENT_REQUEST_CODE = 0;

    /**
     * Specifies the ID for the forecast notification.
     */
    public static final int NOTIFICATION_NOTIFY_ID = 0;

    /**
     * Specifies the time interval after which a new notification should show up.
     */
    public static final long NOTIFICATION_INTERVAL_TIME_MILLIS = TimeUnit.HOURS.toMillis(3);

    // --------------------------------------------------------
    // End of NOTIFICATION region
    // --------------------------------------------------------

    // --------------------------------------------------------
    // FORECAST region
    // --------------------------------------------------------

    /**
     * Specifies the number of forecasts for a single day.
     */
    public static final int FORECASTS_FOR_A_DAY = 8;

    /**
     * Specifies the time interval after which the cached hourly forecast data should be
     * updated with a new one.
     */
    public static final long FORECAST_HOURLY_TIME_OFFSET_FOR_CACHED_DATA_MILLIS = TimeUnit.HOURS.toMillis(3);

    /**
     * Specifies the time interval after which the cached daily forecast data should be
     * updated with a new one.
     */
    public static final long FORECAST_DAILY_TIME_OFFSET_FOR_CACHED_DATA_MILLIS = TimeUnit.DAYS.toMillis(1);

    /**
     * Specifies the timestamp with which the daily forecast is saved into the database.
     * The value must be bigger than {@link Integer#MAX_VALUE}!
     *
     * @see PersistenceLogic#monitorForecastDBSize()
     */
    public static final long FORECAST_DAILY_TIMESTAMP = Long.MAX_VALUE;

    // --------------------------------------------------------
    // End of FORECAST region
    // --------------------------------------------------------

    // --------------------------------------------------------
    // BUNDLE-KEYS region
    // --------------------------------------------------------

    /**
     * Unique bundle key. In order to ensure this, keep the variable declaration
     * the same as its value!
     */
    public static final String BUNDLE_FORECAST_DETAILS_KEY = "BUNDLE_FORECAST_DETAILS_KEY";

    /**
     * Unique bundle key. In order to ensure this, keep the variable declaration
     * the same as its value!
     */
    public static final String BUNDLE_LOCATION_FROM_SEARCH_KEY = "BUNDLE_LOCATION_FROM_SEARCH_KEY";

    /**
     * Unique bundle key. In order to ensure this, keep the variable declaration
     * the same as its value!
     */
    public static final String BUNDLE_RECYCLER_LOADING_ITEM_KEY = "BUNDLE_RECYCLER_LOADING_ITEM_KEY";

    // --------------------------------------------------------
    // End of BUNDLE-KEYS region
    // --------------------------------------------------------

    // --------------------------------------------------------
    // DB region
    // --------------------------------------------------------

    /**
     * Unique name for the database. In order to ensure this,
     * keep the variable declaration the same as its value!
     */
    public static final String DB_PERSISTENCE_LOGIC_NAME = "DB_PERSISTENCE_LOGIC_NAME";

    /**
     * Specifies the database size for forecasts.
     */
    public static final int DB_FORECAST_SIZE = 10;

    /**
     * Specifies the database key used to get the cached location.
     */
    public static final long DB_CURRENT_LOCATION_KEY = 0;

    /**
     * Specifies the database key used to get the cached daily forecast.
     * The value must be bigger than {@link Integer#MAX_VALUE}!
     *
     * @see PersistenceLogic#monitorForecastDBSize()
     */
    public static final long DB_DAILY_FORECAST_KEY = Long.MAX_VALUE;

    // --------------------------------------------------------
    // End of DB region
    // --------------------------------------------------------

    // --------------------------------------------------------
    // SHARED-PREFERENCES region
    // --------------------------------------------------------

    /**
     * Unique shared preferences key. In order to ensure this, keep
     * the variable declaration the same as its value!
     */
    public static final String SHARED_PREFERENCES_NOTIFICATIONS_ACTIVE_KEY = "SHARED_PREFERENCES_NOTIFICATIONS_ACTIVE_KEY";

    // --------------------------------------------------------
    // End of SHARED-PREFERENCES region
    // --------------------------------------------------------

    // --------------------------------------------------------
    // VIEW-PAGER-FRAGMENT region
    // --------------------------------------------------------

    /**
     * Specifies the different fragment types and their respective positions
     * for the {@link com.petar.weather.ui.adapter.ViewPagerFragmentAdapter}.
     *
     * @see <a href="http://blog.shamanland.com/2016/02/int-string-enum.html">http://blog.shamanland.com/2016/02/int-string-enum.html</a>
     */
    @IntDef({
            ViewPagerFragmentPositions.HOURLY_FORECAST,
            ViewPagerFragmentPositions.DAILY_FORECAST,
            ViewPagerFragmentPositions.SETTINGS
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewPagerFragmentPositions {

        /**
         * Numeric representation of {@link com.petar.weather.ui.fragments.HourlyForecastFragment}.
         */
        int HOURLY_FORECAST = 0;

        /**
         * Numeric representation of {@link com.petar.weather.ui.fragments.HourlyForecastFragment}.
         */
        int DAILY_FORECAST = 1;

        /**
         * Numeric representation of {@link com.petar.weather.ui.fragments.SettingsFragment}.
         */
        int SETTINGS = 2;
    }

    /**
     * Specifies the number of fragments in the {@link android.support.v4.view.ViewPager}.
     * Note, this number should not be higher than the number of constants in
     * {@link ViewPagerFragmentPositions}!
     */
    public static final int VIEW_PAGER_FRAGMENT_COUNT = 3;

    // --------------------------------------------------------
    // End of VIEW-PAGER-FRAGMENT region
    // --------------------------------------------------------

    // --------------------------------------------------------
    // RECYCLER-ITEMS region
    // --------------------------------------------------------

    /**
     * Specifies all the different types of {@link com.petar.weather.ui.recycler.AListenerRecyclerItem}.
     * Used to identify them in a data set and apply the needed actions.
     *
     * @see <a href="http://blog.shamanland.com/2016/02/int-string-enum.html">http://blog.shamanland.com/2016/02/int-string-enum.html</a>
     */
    @IntDef({
            RecyclerItems.FORECAST_ITEM,
            RecyclerItems.LOADING_ITEM,
            RecyclerItems.LOCATION_ITEM
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface RecyclerItems {

        /**
         * Numeric representation of {@link com.petar.weather.logic.models.AForecast}.
         */
        int FORECAST_ITEM = 0;

        /**
         * Numeric representation of {@link com.petar.weather.ui.recycler.LoadingRecyclerItem}.
         */
        int LOADING_ITEM = 1;

        /**
         * Numeric representation of {@link com.petar.weather.logic.models.ALocation}.
         */
        int LOCATION_ITEM = 2;
    }

    // --------------------------------------------------------
    // End of RECYCLER-ITEMS region
    // --------------------------------------------------------

    // --------------------------------------------------------
    // ERROR-HANDLING region
    // --------------------------------------------------------

    /**
     * Specifies the types of errors that can occur within this application.
     *
     * @see <a href="http://blog.shamanland.com/2016/02/int-string-enum.html">http://blog.shamanland.com/2016/02/int-string-enum.html</a>
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

        /**
         * Set as message for {@link Exception}/{@link Throwable}, when there is
         * no internet connection.
         */
        String NO_INTERNET_CONNECTION = "NO_INTERNET_CONNECTION";

        /**
         * Set as message for {@link Exception}/{@link Throwable}, when there are
         * no results for a request.
         */
        String NO_RESULTS_FOR_REQUEST = "NO_RESULTS_FOR_REQUEST";

        /**
         * Set as message for {@link Exception}/{@link Throwable}, when the cached
         * data cannot be updated with a new one.
         */
        String CANNOT_UPDATE_CACHED_DATA = "CANNOT_UPDATE_CACHED_DATA";

        /**
         * Set as message for {@link Exception}/{@link Throwable}, when the search
         * input is empty.
         */
        String NO_SEARCH_INPUT = "NO_SEARCH_INPUT";

        /**
         * Set as message for {@link Exception}/{@link Throwable}, when the search
         * input is invalid.
         */
        String WRONG_SEARCH_INPUT = "WRONG_SEARCH_INPUT";

        /**
         * Set as message for {@link Exception}/{@link Throwable}, when the cause of
         * the error is unknown, generalize the error.
         */
        String DEFAULT = "DEFAULT";
    }

    // --------------------------------------------------------
    // End of ERROR-HANDLING region
    // --------------------------------------------------------

    // --------------------------------------------------------
    // CREDITS region
    // --------------------------------------------------------

    /**
     * Specifies who is the API provider for this application. Must be used
     * within {@link com.petar.weather.R.string#settings_credits_api_text}.
     */
    public static final String CREDITS_API_PROVIDER = "MetaWeather";

    /**
     * Specifies the URL for {@link #CREDITS_API_PROVIDER}.
     *
     * @see com.petar.weather.util.TextUtil#linkifyText(TextView, String, String, String)
     */
    public static final String CREDITS_API_PROVIDER_URL = "http://www.metaweather.com/";

    /**
     * Specifies who is the app icon provider for this application. Must be used
     * within {@link com.petar.weather.R.string#settings_credits_app_icon_text}.
     */
    public static final String CREDITS_APP_ICON_PROVIDER = "Alfredo Hernandez";

    /**
     * Specifies the URL for {@link #CREDITS_APP_ICON_PROVIDER}.
     *
     * @see com.petar.weather.util.TextUtil#linkifyText(TextView, String, String, String)
     */
    public static final String CREDITS_APP_ICON_PROVIDER_URL = "https://www.flaticon.com/authors/alfredo-hernandez";

    // --------------------------------------------------------
    // End of CREDITS region
    // --------------------------------------------------------

    // --------------------------------------------------------
    // GENERAL/COMMON-SENSE region
    // --------------------------------------------------------

    /**
     * Specifies how many meters are in a kilometer.
     */
    public static final int GENERAL_METERS_INTO_KILOMETER = 1_000;

    /**
     * Contains a comma symbol.
     */
    public static final String GENERAL_COMMA_SYMBOL = ",";

    // --------------------------------------------------------
    // End of GENERAL/COMMON-SENSE region
    // --------------------------------------------------------
}
