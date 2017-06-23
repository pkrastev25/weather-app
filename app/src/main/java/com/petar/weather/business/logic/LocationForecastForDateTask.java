package com.petar.weather.business.logic;

import com.petar.weather.networking.models.ApiLogic;
import com.petar.weather.networking.models.NForecast;

import java.util.List;

/**
 * Created by User on 22.6.2017 г..
 */

public class LocationForecastForDateTask extends AAsyncTask<Object, List<NForecast>> {

    public LocationForecastForDateTask(AAsyncTaskListener listener) {
        super(listener);
    }

    @Override
    protected List<NForecast> doInBackground(Object... params) {
        try {
            int id = (Integer) params[0];
            String date = (String) params[1];
            return ApiLogic.getInstance().getLocationForecastForDate(id, date);
        } catch (Exception e) {
            mException = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<NForecast> result) {
        super.onPostExecute(result);

        if (mException != null || result == null) {
            mListener.onError();
        } else {
            mListener.onLoadFinished(result);
        }
    }
}
