package com.petar.weather.presenters;

import android.content.Context;
import android.location.Location;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.petar.weather.logic.models.ALocation;
import com.petar.weather.util.AsyncTaskUtil;
import com.petar.weather.logic.DataLogic;
import com.petar.weather.ui.views.IForecastActivity;
import com.petar.weather.util.FormatUtil;

/**
 * Created by User on 23.6.2017 г..
 */

public class ForecastActivityPresenter extends MvpBasePresenter<IForecastActivity> {

    private ALocation mCurrentLocation;

    public void processLocationCoordinates(final Context context, final Location location, final boolean pullToRefresh) {
        if (isViewAttached()) {
            getView().showLoading(pullToRefresh);

            AsyncTaskUtil.doInBackground(new AsyncTaskUtil.IAsyncTaskHelperListener<ALocation>() {
                @Override
                public ALocation onExecuteTask() throws Exception {
                    return DataLogic.getInstance().getCurrentLocation(
                            context,
                            FormatUtil.coordinatesFormat(location),
                            pullToRefresh
                    );
                }

                @Override
                public void onSuccess(ALocation result) {
                    if (isViewAttached()) {
                        getView().setData(result);
                        mCurrentLocation = result;

                        if (pullToRefresh) {
                            getView().setShowContentState();
                        } else {
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

    public ALocation getCurrentLocation() {
        return mCurrentLocation;
    }
}
