package com.petar.weather.business.logic;

import com.petar.weather.networking.models.ApiLogic;
import com.petar.weather.networking.models.NLocationForecast;

import java.io.IOException;

/**
 * Created by User on 22.6.2017 г..
 */

public class LocationForecastTask extends AAsyncTask<Integer, NLocationForecast> {

    public LocationForecastTask(AAsyncTaskListener listener) {
        super(listener);
    }

    @Override
    protected NLocationForecast doInBackground(Integer... params) {
        try {
            return ApiLogic.getInstance().getLocationForecast(params[0]);
        } catch (Exception e) {
            mException = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(NLocationForecast result) {
        super.onPostExecute(result);

        if (mException != null || result == null) {
            mListener.onError();
        } else {
            mListener.onLoadFinished(result);
        }
    }
}
