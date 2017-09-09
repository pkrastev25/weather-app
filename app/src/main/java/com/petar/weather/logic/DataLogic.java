package com.petar.weather.logic;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.petar.weather.logic.models.AForecast;
import com.petar.weather.logic.models.ALocation;
import com.petar.weather.logic.models.IWeeklyForecast;
import com.petar.weather.networking.ApiLogic;
import com.petar.weather.networking.models.NForecast;
import com.petar.weather.networking.models.NLocation;
import com.petar.weather.persistence.PersistenceLogic;
import com.petar.weather.persistence.models.PForecast;
import com.petar.weather.persistence.models.PLocation;
import com.petar.weather.app.Constants;
import com.petar.weather.util.ForecastUtil;
import com.petar.weather.util.NetworkUtil;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * Created by User on 29.6.2017 г..
 */

public class DataLogic {

    private static DataLogic sInstance;

    private DataLogic() {

    }

    public static DataLogic getInstance() {
        if (sInstance == null) {
            sInstance = new DataLogic();
        }

        return sInstance;
    }

    public List<? extends ALocation> getLocationQueryResult(Context context, String query) throws IOException {
        List<? extends ALocation> result = null;

        if (NetworkUtil.isNetworkAvailable(context)) {
            result = ApiLogic.getInstance().getLocationQueryResult(query);
        }

        return result;
    }

    public List<? extends ALocation> getLocationQueryResultWithCoordinates(Context context, String coordinates) throws Exception {
        List<? extends ALocation> result = null;

        if (NetworkUtil.isNetworkAvailable(context)) {
            result = ApiLogic.getInstance().getLocationQueryResultWithCoordinates(coordinates);
        }

        return result;
    }

    public ALocation getCurrentLocation(Context context, String coordinates, boolean makeRequest) throws IOException {
        PLocation cachedLocation = PersistenceLogic.getInstance(context).getLocation();
        NLocation currentLocation = null;

        if (NetworkUtil.isNetworkAvailable(context)) {
            if (cachedLocation == null || makeRequest || PersistenceLogic.getInstance(context).shouldLocationDataUpdate()) {
                List<NLocation> nearbyLocations = ApiLogic.getInstance().getLocationQueryResultWithCoordinates(coordinates);

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

    public List<? extends AForecast> getLocationWeeklyForecast(Context context, int id, boolean makeRequest) throws IOException {
        long keyDB = Constants.DB_WEEKLY_FORECAST_KEY;
        PForecast cachedForecast = PersistenceLogic.getInstance(context).getForecast(Constants.DB_WEEKLY_FORECAST_KEY);
        List<? extends AForecast> currentForecast = null;

        if (NetworkUtil.isNetworkAvailable(context)) {
            if (cachedForecast == null || makeRequest || PersistenceLogic.getInstance(context).shouldForecastDataUpdate(keyDB, id)) {
                IWeeklyForecast weekly = ApiLogic.getInstance().getLocationForecast(id);

                if (weekly != null && !weekly.getForecast().isEmpty()) {
                    PersistenceLogic.getInstance(context).persistForecast(
                            keyDB,
                            id,
                            Constants.FORECAST_WEEKLY_TIMESTAMP,
                            currentForecast = weekly.getForecast()
                    );
                }
            }
        }

        if (currentForecast == null && cachedForecast != null && cachedForecast.getIdWOE() == id) {
            Type listType = new TypeToken<List<NForecast>>() {
            }.getType();
            currentForecast = new Gson().fromJson(cachedForecast.getForecasts(), listType);
        }

        return currentForecast;
    }

    public List<? extends AForecast> getLocationForecastForDate(Context context, int id, String date, boolean makeRequest) throws IOException {
        long keyDB = date.hashCode();
        PForecast cachedForecast = PersistenceLogic.getInstance(context).getForecast(keyDB);
        List<? extends AForecast> currentForecast = null;

        if (NetworkUtil.isNetworkAvailable(context)) {
            if (cachedForecast == null || makeRequest || PersistenceLogic.getInstance(context).shouldForecastDataUpdate(keyDB, id)) {
                currentForecast = ApiLogic.getInstance().getLocationForecastForDate(id, date);

                if (currentForecast != null) {
                    currentForecast = ForecastUtil.extractData(currentForecast);

                    PersistenceLogic.getInstance(context).persistForecast(
                            keyDB,
                            id,
                            date,
                            currentForecast
                    );
                }
            }
        }

        if (currentForecast == null && cachedForecast != null && cachedForecast.getIdWOE() == id) {
            Type listType = new TypeToken<List<NForecast>>() {
            }.getType();
            currentForecast = new Gson().fromJson(cachedForecast.getForecasts(), listType);
        }

        return currentForecast;
    }
}
