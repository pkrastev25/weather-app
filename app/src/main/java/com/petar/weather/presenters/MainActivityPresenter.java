package com.petar.weather.presenters;

import android.location.Location;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.petar.weather.util.AsyncTaskUtil;
import com.petar.weather.logic.DataLogic;
import com.petar.weather.logic.models.ILocation;
import com.petar.weather.ui.views.IMainActivity;
import com.petar.weather.util.FormatUtil;

import java.util.List;

/**
 * Created by User on 22.6.2017 г..
 */

public class MainActivityPresenter extends MvpBasePresenter<IMainActivity> {

    public MainActivityPresenter() {

    }

    public void processLocation(final Location location) {
        AsyncTaskUtil.doInBackground(new AsyncTaskUtil.IAsyncTaskHelperListener<List<? extends ILocation>>() {
            @Override
            public List<? extends ILocation> onExecuteTask() throws Exception {
                return DataLogic.getInstance().getLocationQueryResultWithCoordinates(
                        FormatUtil.coordinatesFormat(location)
                );
            }

            @Override
            public void onSuccess(List<? extends ILocation> nLocations) {
                if (!nLocations.isEmpty()) {
                    if (isViewAttached()) {
                        getView().hideSplashScreen();
                        getView().navigateToForecastActivity(nLocations.get(0).getId());
                    }
                }
                // TODO: Implement logic when we do not have near points
            }

            @Override
            public void onError(Exception error) {

            }
        });
    }
}
