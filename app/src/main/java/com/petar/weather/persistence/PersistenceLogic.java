package com.petar.weather.persistence;

import android.content.Context;

import com.google.gson.Gson;
import com.petar.weather.logic.models.AForecast;
import com.petar.weather.networking.models.NLocation;
import com.petar.weather.persistence.models.DaoMaster;
import com.petar.weather.persistence.models.DaoSession;
import com.petar.weather.persistence.models.PForecast;
import com.petar.weather.persistence.models.PLocation;
import com.petar.weather.util.Constants;

import java.util.Collections;
import java.util.List;

/**
 * Created by User on 18.7.2017 г..
 */
public class PersistenceLogic {

    private static PersistenceLogic sInstance;
    private DaoSession mDaoSession;

    public static PersistenceLogic getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PersistenceLogic(context);
        }

        return sInstance;
    }

    private PersistenceLogic(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, Constants.DB_NAME, null);
        DaoMaster master = new DaoMaster(helper.getWritableDatabase());
        mDaoSession = master.newSession();
    }

    public void persistLocation(NLocation location) {
        mDaoSession.getPLocationDao().insertWithoutSettingPk(
                new PLocation(location)
        );
    }

    public PLocation getLocation() {
        return mDaoSession.getPLocationDao().load(Constants.DB_CURRENT_LOCATION_KEY);
    }

    public void persistForecast(long keyDB, String date, List<? extends AForecast> forecast) {
        monitorForecastDBSize();
        String forecastJSON = new Gson().toJson(forecast);

        mDaoSession.getPForecastDao().insertOrReplace(
                new PForecast(keyDB, date, forecastJSON)
        );
    }

    public void persistForecast(long keyDB, long timestamp, List<? extends AForecast> forecast) {
        String forecastJSON = new Gson().toJson(forecast);

        mDaoSession.getPForecastDao().insertOrReplace(
                new PForecast(keyDB, timestamp, forecastJSON)
        );
    }

    public PForecast getForecast(long keyDB) {
        return mDaoSession.getPForecastDao().load(keyDB);
    }

    private void monitorForecastDBSize() {
        List<PForecast> forecasts = mDaoSession.getPForecastDao().loadAll();

        if (forecasts.size() >= Constants.DB_FORECAST_SIZE) {
            Collections.sort(forecasts);
            mDaoSession.getPForecastDao().delete(forecasts.get(0));
        }
    }
}
