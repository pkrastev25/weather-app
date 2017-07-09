package com.petar.weather.presenters;

import android.location.Location;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.petar.weather.logic.models.ALocation;
import com.petar.weather.util.AsyncTaskUtil;
import com.petar.weather.logic.DataLogic;
import com.petar.weather.ui.views.IForecastActivity;
import com.petar.weather.util.FormatUtil;

import java.util.List;

/**
 * Created by User on 23.6.2017 г..
 */

public class ForecastActivityPresenter extends MvpBasePresenter<IForecastActivity> {

    public void processLocationCoordinates(final Location location) {
        if (isViewAttached()) {
            getView().showLoading(false);

            AsyncTaskUtil.doInBackground(new AsyncTaskUtil.IAsyncTaskHelperListener<List<? extends ALocation>>() {
                @Override
                public List<? extends ALocation> onExecuteTask() throws Exception {
                    return DataLogic.getInstance().getLocationQueryResultWithCoordinates(
                            FormatUtil.coordinatesFormat(location)
                    );
                }

                @Override
                public void onSuccess(List<? extends ALocation> result) {
                    if (isViewAttached()) {
                        if (!result.isEmpty()) {
                            getView().setData(result.get(0));
                            getView().showContent();
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
}
