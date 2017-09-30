package com.petar.weather.logic;

import android.content.Context;
import android.support.annotation.Nullable;

import com.petar.weather.logic.models.AForecast;
import com.petar.weather.logic.models.ALocation;
import com.petar.weather.logic.models.IDailyForecast;
import com.petar.weather.networking.ApiLogic;
import com.petar.weather.networking.models.NLocation;
import com.petar.weather.persistence.PersistenceLogic;
import com.petar.weather.persistence.models.PLocation;
import com.petar.weather.app.Constants;
import com.petar.weather.util.ForecastUtil;
import com.petar.weather.util.NetworkUtil;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Singleton that is responsible for fetching data from the database or
 * by performing a request to the API.
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 29.6.2017
 */
public class DataLogic {

    private static DataLogic sInstance;

    /**
     * Keep private, invoke {@link #getInstance()} to get access to
     * the instance.
     */
    private DataLogic() {

    }

    /**
     * Expose only a single instance of this class. Initialize if null.
     *
     * @return The {@link DataLogic} instance.
     */
    public static DataLogic getInstance() {
        if (sInstance == null) {
            sInstance = new DataLogic();
        }

        return sInstance;
    }

    /**
     * Performs an API request for the given query location. There is no
     * persistence for this type of request!
     *
     * @param context {@link Context} reference
     * @param query   The location query of interest
     * @return {@link List} of {@link ALocation} that match the query, null if the request failed
     * @throws IOException Something went wrong with the request
     */
    @Nullable
    public List<? extends ALocation> getLocationQueryResult(Context context, String query) throws IOException {
        List<? extends ALocation> result = null;

        if (NetworkUtil.isNetworkConnected(context)) {
            result = ApiLogic.getInstance().getLocationQueryResult(query);
        }

        return result;
    }

    /**
     * Performs an API request for the given coordinates of a location. There is no
     * persistence for this type of request!
     *
     * @param context     {@link Context} reference
     * @param coordinates Coordinates in the form of {@link Constants#FORMAT_COORDINATES}
     * @return {@link List} of {@link ALocation} that match the coordinates, null if the request failed
     * @throws IOException Something went wrong with the request
     */
    @Nullable
    public List<? extends ALocation> getLocationQueryResultWithCoordinates(Context context, String coordinates) throws IOException {
        List<? extends ALocation> result = null;

        if (NetworkUtil.isNetworkConnected(context)) {
            result = ApiLogic.getInstance().getLocationQueryResultWithCoordinates(coordinates);
        }

        return result;
    }

    /**
     * Performs an API request for finding the current location. In order to minimize the number of API requests,
     * the last location is stored and retrieved if the data is considered 'up-to-date'.
     *
     * @param context     {@link Context} reference
     * @param coordinates Coordinates in the form of {@link Constants#FORMAT_COORDINATES}
     * @param makeRequest If set to true, it will ignore the cached location in the database and perform a new API request, if possible, false otherwise
     * @return The current location of the user, null if not found
     * @throws IOException Something went wrong with the request
     */
    @Nullable
    public ALocation getCurrentLocation(Context context, String coordinates, boolean makeRequest) throws IOException {
        PLocation cachedLocation = PersistenceLogic.getInstance(context).getLocation();
        NLocation currentLocation = null;

        if (NetworkUtil.isNetworkConnected(context)) {
            // Perform an API request only if it is needed
            if (cachedLocation == null || makeRequest || PersistenceLogic.getInstance(context).shouldLocationDataUpdate()) {
                List<NLocation> nearbyLocations = ApiLogic.getInstance().getLocationQueryResultWithCoordinates(coordinates);

                // Do not persist inconsistent data
                if (nearbyLocations != null && !nearbyLocations.isEmpty()) {
                    Collections.sort(nearbyLocations);

                    PersistenceLogic.getInstance(context).persistLocation(
                            currentLocation = nearbyLocations.get(0)
                    );
                }
            }
        }

        return currentLocation == null ? cachedLocation : currentLocation;
    }

    /**
     * Performs an API request for getting the daily forecast for a given location. In order to minimize
     * the number of API requests, the last daily forecast is stored and retrieved if the data is
     * considered 'up-to-date'.
     *
     * @param context     {@link Context} reference
     * @param idWOE       'Where on Earth ID', identifies a location
     * @param makeRequest If set to true, it will ignore the cached location in the database and perform a new API request, if possible, false otherwise
     * @return The daily forecast for the given location, null if not found
     * @throws IOException Something went wrong with the request
     */
    @Nullable
    public List<? extends AForecast> getLocationDailyForecast(Context context, int idWOE, boolean makeRequest) throws IOException {
        long keyDB = Constants.DB_DAILY_FORECAST_KEY;
        List<? extends AForecast> cachedForecast = PersistenceLogic.getInstance(context).getForecast(Constants.DB_DAILY_FORECAST_KEY, idWOE);
        List<? extends AForecast> currentForecast = null;

        if (NetworkUtil.isNetworkConnected(context)) {
            // Perform an API request only if it is needed
            if (cachedForecast == null || makeRequest || PersistenceLogic.getInstance(context).shouldForecastDataUpdate(keyDB, idWOE)) {
                IDailyForecast weekly = ApiLogic.getInstance().getLocationDailyForecast(idWOE);

                // Do not persist inconsistent data
                if (weekly != null && !weekly.getForecast().isEmpty()) {
                    PersistenceLogic.getInstance(context).persistForecast(
                            keyDB,
                            idWOE,
                            Constants.FORECAST_DAILY_TIMESTAMP,
                            currentForecast = weekly.getForecast()
                    );
                }
            }
        }

        return currentForecast == null ? cachedForecast : currentForecast;
    }

    /**
     * Performs an API request for getting the hourly forecast of a given location for
     * a given date. In order to minimize the number of API requests, the last hourly
     * forecast is stored and retrieved if the data is considered 'up-to-date'.
     *
     * @param context     {@link Context} reference
     * @param idWOE       'Where on Earth ID', identifies a location
     * @param date        The date which the forecast is for. Must be in the form of {@link Constants#API_DATE_REQUEST_FORMAT}
     * @param makeRequest If set to true, it will ignore the cached location in the database and perform a new API request, if possible, false otherwise
     * @return The hourly forecast for the given location and date, null if not found
     * @throws IOException Something went wrong with the request
     */
    @Nullable
    public List<? extends AForecast> getLocationHourlyForecastForDate(Context context, int idWOE, String date, boolean makeRequest) throws IOException {
        long keyDB = date.hashCode();
        List<? extends AForecast> cachedForecast = PersistenceLogic.getInstance(context).getForecast(keyDB, idWOE);
        List<? extends AForecast> currentForecast = null;

        if (NetworkUtil.isNetworkConnected(context)) {
            // Perform an API request only if it is needed
            if (cachedForecast == null || makeRequest || PersistenceLogic.getInstance(context).shouldForecastDataUpdate(keyDB, idWOE)) {
                currentForecast = ApiLogic.getInstance().getLocationForecastForDate(idWOE, date);

                // Do not persist inconsistent data
                if (currentForecast != null) {
                    currentForecast = ForecastUtil.extractData(currentForecast);

                    PersistenceLogic.getInstance(context).persistForecast(
                            keyDB,
                            idWOE,
                            date,
                            currentForecast
                    );
                }
            }
        }

        return currentForecast == null ? cachedForecast : currentForecast;
    }
}
