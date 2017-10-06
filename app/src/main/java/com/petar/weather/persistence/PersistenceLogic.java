package com.petar.weather.persistence;

import android.content.Context;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.petar.weather.logic.models.AForecast;
import com.petar.weather.networking.models.NForecast;
import com.petar.weather.networking.models.NLocation;
import com.petar.weather.persistence.models.DaoMaster;
import com.petar.weather.persistence.models.DaoSession;
import com.petar.weather.persistence.models.PForecast;
import com.petar.weather.persistence.models.PLocation;
import com.petar.weather.app.Constants;
import com.petar.weather.util.TimeUtil;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * Singleton that is responsible for storing/retrieving data from the database.
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 18.7.2017
 */
public class PersistenceLogic {

    private static PersistenceLogic sInstance;
    private DaoSession mDaoSession;

    /**
     * Expose only a single instance of this class. Initialize if null.
     *
     * @param context {@link Context} reference
     * @return The {@link PersistenceLogic} instance
     */
    public static PersistenceLogic getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PersistenceLogic(context);
        }

        return sInstance;
    }

    /**
     * Keep private, invoke {@link #getInstance(Context)} ()} to get the instance.
     *
     * @param context {@link Context} reference
     */
    private PersistenceLogic(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, Constants.DB_PERSISTENCE_LOGIC_NAME, null);
        DaoMaster master = new DaoMaster(helper.getWritableDatabase());
        mDaoSession = master.newSession();
    }

    /**
     * Persist the given location object.
     *
     * @param location {@link NLocation} to be persisted
     */
    public void persistLocation(NLocation location) {
        mDaoSession.getPLocationDao().insertOrReplace(
                new PLocation(location)
        );
    }

    /**
     * Gets the persisted location object.
     *
     * @return Last {@link PLocation} object
     */
    public PLocation getLocation() {
        return mDaoSession.getPLocationDao().load(Constants.DB_CURRENT_LOCATION_KEY);
    }

    /**
     * Persists the given forecast while monitoring the database size. Converts the
     * {@link List} of {@link AForecast} to a raw JSON, easier for storing in the
     * database.
     *
     * @param keyDB    Unique identifier for this object inside the database
     * @param idWOE    'Where on Earth ID', identifies a location
     * @param date     The date for the forecast
     * @param forecast The actual forecast data
     */
    public void persistForecast(long keyDB, long idWOE, String date, List<? extends AForecast> forecast) {
        monitorForecastDBSize();
        String forecastJSON = new Gson().toJson(forecast);

        mDaoSession.getPForecastDao().insertOrReplace(
                new PForecast(keyDB, idWOE, date, forecastJSON)
        );
    }

    /**
     * Persists the given forecast. Converts the {@link List} of {@link AForecast} to a raw JSON,
     * easier for storing in the database.
     *
     * @param keyDB     Unique identifier for this object inside the database
     * @param idWOE     'Where on Earth ID', identifies a location
     * @param timestamp Representation of the date for the forecast in milliseconds
     * @param forecast  The actual forecast data
     */
    public void persistForecast(long keyDB, long idWOE, long timestamp, List<? extends AForecast> forecast) {
        String forecastJSON = new Gson().toJson(forecast);

        mDaoSession.getPForecastDao().insertOrReplace(
                new PForecast(keyDB, idWOE, timestamp, forecastJSON)
        );
    }

    /**
     * Makes the conversion from a raw JSON forecast data to a {@link List}
     * of {@link NForecast}.
     *
     * @param keyDB Unique identifier for this object inside the database
     * @param idWOE 'Where on Earth ID', identifies a location
     * @return The stored forecasts, null if nothing is stored for the key
     */
    @Nullable
    public List<NForecast> getForecast(long keyDB, int idWOE) {
        PForecast cachedForecast = mDaoSession.getPForecastDao().load(keyDB);

        if (cachedForecast != null && cachedForecast.getIdWOE() == idWOE) {
            Type listType = new TypeToken<List<NForecast>>() {

            }.getType();

            return new Gson().fromJson(cachedForecast.getForecasts(), listType);
        }

        return null;
    }

    /**
     * Monitors the database size so that it does not grow to infinity. The {@link PForecast}
     * objects are sorted according to the {@link PForecast#timestamp} field. By giving
     * the daily forecast instance the timestamp value of {@link Constants#FORECAST_DAILY_TIMESTAMP},
     * it is ensured that it will never be removed by the sorting from the database!
     */
    private void monitorForecastDBSize() {
        List<PForecast> forecasts = mDaoSession.getPForecastDao().loadAll();

        if (forecasts.size() >= Constants.DB_FORECAST_SIZE) {
            Collections.sort(forecasts);
            mDaoSession.getPForecastDao().delete(forecasts.get(0));
        }
    }

    /**
     * Makes a check if the location data is outdated. The implementation is
     * dependant strongly on {@link Constants#LOCATION_TIME_OFFSET_FOR_CACHED_DATA_MILLIS}.
     *
     * @return True if the location data is outdated, false otherwise
     */
    public boolean shouldLocationDataUpdate() {
        PLocation cachedLocation = getLocation();

        if (cachedLocation == null) {
            return true;
        } else if (cachedLocation.getExpireTime() < TimeUtil.getCurrentTime()) {
            return true;
        }

        return false;
    }

    /**
     * Makes a check if the forecast data is outdated. The implementation is
     * dependant strongly on {@link Constants#FORECAST_DAILY_TIME_OFFSET_FOR_CACHED_DATA_MILLIS}
     * and {@link Constants#FORECAST_HOURLY_TIME_OFFSET_FOR_CACHED_DATA_MILLIS}.
     *
     * @param keyDB Unique identifier for this object inside the database
     * @param idWOE 'Where on Earth ID', identifies a location
     * @return True if the forecast data is outdated, false otherwise
     */
    public boolean shouldForecastDataUpdate(long keyDB, long idWOE) {
        PForecast cachedForecast = mDaoSession.getPForecastDao().load(keyDB);

        if (cachedForecast == null) {
            return true;
        } else if (cachedForecast.getIdWOE() != idWOE) {
            return true;
        } else if (cachedForecast.getExpireTime() < TimeUtil.getCurrentTime()) {
            return true;
        }

        return false;
    }
}
