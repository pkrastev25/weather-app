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
import com.petar.weather.util.TimeUtil;

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

    public List<? extends ALocation> getLocationQueryResult(String query) throws IOException {
        return ApiLogic.getInstance().getLocationQueryResult(query);
    }

    public List<? extends ALocation> getLocationQueryResultWithCoordinates(String coordinates) throws IOException {
        return ApiLogic.getInstance().getLocationQueryResultWithCoordinates(coordinates);
    }

    public ALocation getCurrentLocation(Context context, String coordinates, boolean useCache) throws IOException {
        PLocation cachedLocation = PersistenceLogic.getInstance(context).getLocation();
        NLocation currentLocation = null;

        if (NetworkUtil.isNetworkAvailable(context)) {
            if (cachedLocation == null || useCache || cachedLocation.getExpireTime() < TimeUtil.getCurrentTime()) {
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

    public List<? extends AForecast> getLocationWeeklyForecast(Context context, int id, boolean useCache) throws IOException {
        PForecast cachedForecast = PersistenceLogic.getInstance(context).getForecast(Constants.DB_WEEKLY_FORECAST_KEY);
        List<? extends AForecast> currentForecast = null;

        if (NetworkUtil.isNetworkAvailable(context)) {
            if (cachedForecast == null || useCache || cachedForecast.getExpireTime() < TimeUtil.getCurrentTime()) {
                IWeeklyForecast weekly = ApiLogic.getInstance().getLocationForecast(id);

                if (weekly != null && !weekly.getForecast().isEmpty()) {
                    PersistenceLogic.getInstance(context).persistForecast(
                            Constants.DB_WEEKLY_FORECAST_KEY,
                            Constants.FORECAST_WEEKLY_TIMESTAMP,
                            currentForecast = weekly.getForecast()
                    );
                }
            }
        }

        if (cachedForecast != null) {
            Type listType = new TypeToken<List<NForecast>>() {
            }.getType();
            currentForecast = new Gson().fromJson(cachedForecast.getForecasts(), listType);
        }

        return currentForecast;
    }

    public List<? extends AForecast> getLocationForecastForDate(Context context, int id, String date, boolean useCache) throws IOException {
        long keyDB = date.hashCode();
        PForecast cachedForecast = PersistenceLogic.getInstance(context).getForecast(keyDB);
        List<? extends AForecast> currentForecast = null;

        if (NetworkUtil.isNetworkAvailable(context)) {
            if (cachedForecast == null || useCache || cachedForecast.getExpireTime() < TimeUtil.getCurrentTime()) {
                currentForecast = ApiLogic.getInstance().getLocationForecastForDate(id, date);

                if (currentForecast != null) {
                    currentForecast = ForecastUtil.extractData(currentForecast);

                    PersistenceLogic.getInstance(context).persistForecast(
                            keyDB,
                            date,
                            currentForecast
                    );
                }
            }
        }

        if (cachedForecast != null) {
            Type listType = new TypeToken<List<NForecast>>() {
            }.getType();
            currentForecast = new Gson().fromJson(cachedForecast.getForecasts(), listType);
        }

        return currentForecast;
    }
}
