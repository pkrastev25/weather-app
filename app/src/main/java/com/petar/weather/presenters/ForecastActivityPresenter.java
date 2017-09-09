package com.petar.weather.presenters;

import android.content.Context;
import android.location.Location;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.petar.weather.app.Constants;
import com.petar.weather.logic.models.ALocation;
import com.petar.weather.persistence.PersistenceLogic;
import com.petar.weather.util.AsyncTaskUtil;
import com.petar.weather.logic.DataLogic;
import com.petar.weather.ui.views.IForecastActivity;
import com.petar.weather.util.ErrorHandlingUtil;
import com.petar.weather.util.FormatUtil;
import com.petar.weather.util.NetworkUtil;

/**
 * Created by User on 23.6.2017 г..
 */

public class ForecastActivityPresenter extends MvpBasePresenter<IForecastActivity> {

    private boolean mIsLoading;

    public void processLocationCoordinates(final Context context, final Location location, final boolean pullToRefresh) {
        if (isViewAttached() && !mIsLoading) {
            mIsLoading = true;
            getView().showLoading(pullToRefresh);

            AsyncTaskUtil.doInBackground(new AsyncTaskUtil.IAsyncTaskHelperListener<ALocation>() {
                @Override
                public ALocation onExecuteTask() throws Exception {
                    return DataLogic.getInstance().getCurrentLocation(
                            context,
                            FormatUtil.formatCoordinates(location),
                            pullToRefresh
                    );
                }

                @Override
                public void onSuccess(ALocation result) {
                    if (isViewAttached()) {
                        if (!NetworkUtil.isNetworkAvailable(context) && result == null) {
                            getView().showError(new Throwable(Constants.ErrorHandling.NO_INTERNET_CONNECTION), pullToRefresh);
                        } else if (result == null) {
                            getView().showError(new Throwable(Constants.ErrorHandling.DEFAULT), pullToRefresh);
                        } else {
                            getView().setData(result);

                            if (pullToRefresh) {
                                getView().setShowContentState();
                            } else {
                                getView().showContent();
                            }

                            if (PersistenceLogic.getInstance(context).shouldLocationDataUpdate()) {
                                getView().showMessage(ErrorHandlingUtil.generateErrorText(context, Constants.ErrorHandling.CANNOT_UPDATE_CACHED_DATA));
                            }
                        }
                    }

                    mIsLoading = false;
                }

                @Override
                public void onError(Exception error) {
                    if (isViewAttached()) {
                        getView().showError(new Throwable(Constants.ErrorHandling.DEFAULT), pullToRefresh);
                    }

                    mIsLoading = false;
                }
            });
        }
    }
}
