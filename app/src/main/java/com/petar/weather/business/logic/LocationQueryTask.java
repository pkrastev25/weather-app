package com.petar.weather.business.logic;

import com.petar.weather.networking.models.ApiLogic;
import com.petar.weather.networking.models.NLocation;

import java.io.IOException;
import java.util.List;

/**
 * Created by User on 22.6.2017 г..
 */

public class LocationQueryTask extends AAsyncTask<String, List<NLocation>> {

    public LocationQueryTask(AAsyncTaskListener listener) {
        super(listener);
    }

    @Override
    protected List<NLocation> doInBackground(String... params) {
        try {
            return ApiLogic.getInstance().getLocationQueryResult(params[0]);
        } catch (Exception e) {
            mException = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<NLocation> result) {
        super.onPostExecute(result);

        if (mException != null || result == null) {
            mListener.onError();
        } else {
            mListener.onLoadFinished(result);
        }
    }
}
