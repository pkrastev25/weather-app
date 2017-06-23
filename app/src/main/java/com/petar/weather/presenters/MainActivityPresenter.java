package com.petar.weather.presenters;

import android.location.Location;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.petar.weather.business.logic.AAsyncTask;
import com.petar.weather.business.logic.LocationQueryWithCoordinatesTask;
import com.petar.weather.networking.models.NLocation;
import com.petar.weather.ui.views.IMainActivity;
import com.petar.weather.util.FormatUtil;

import java.util.Collections;
import java.util.List;

/**
 * Created by User on 22.6.2017 г..
 */

public class MainActivityPresenter extends MvpBasePresenter<IMainActivity> {

    private LocationQueryWithCoordinatesTask mTask;

    public MainActivityPresenter() {

    }

    public void processLocation(Location location) {
        initTask();
        mTask.execute(FormatUtil.formatCoordinates(location));
    }

    private void initTask() {
        mTask = new LocationQueryWithCoordinatesTask(new AAsyncTask.AAsyncTaskListener<List<NLocation>>() {
            @Override
            public void onLoadFinished(List<NLocation> nLocations) {
                if (!nLocations.isEmpty()) {
                    Collections.sort(nLocations);

                    if (isViewAttached()) {
                        getView().hideSplashScreen();
                        getView().navigateToForecastActivity(nLocations.get(0).getWoeid());
                    }
                }
                // TODO: Implement logic when we do not have near points
            }

            @Override
            public void onError() {

            }
        });
    }
}
