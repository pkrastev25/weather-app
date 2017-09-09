package com.petar.weather.presenters;

import android.content.Context;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.petar.weather.app.Constants;
import com.petar.weather.logic.DataLogic;
import com.petar.weather.logic.models.AForecast;
import com.petar.weather.persistence.PersistenceLogic;
import com.petar.weather.ui.views.IDailyForecastFragment;
import com.petar.weather.util.AsyncTaskUtil;
import com.petar.weather.util.ErrorHandlingUtil;
import com.petar.weather.util.NetworkUtil;

import java.util.List;

/**
 * Created by User on 8.7.2017 г..
 */

public class DailyForecastFragmentPresenter extends MvpBasePresenter<IDailyForecastFragment> {

    public void loadLocationForecast(final Context context, final int idWOE, final boolean pullToRefresh) {
        if (isViewAttached()) {
            getView().showLoading(pullToRefresh);

            AsyncTaskUtil.doInBackground(new AsyncTaskUtil.IAsyncTaskHelperListener<List<? extends AForecast>>() {
                @Override
                public List<? extends AForecast> onExecuteTask() throws Exception {
                    return DataLogic.getInstance().getLocationWeeklyForecast(
                            context,
                            idWOE,
                            pullToRefresh
                    );
                }

                @Override
                public void onSuccess(List<? extends AForecast> result) {
                    if (isViewAttached()) {
                        if (!NetworkUtil.isNetworkAvailable(context) && result == null) {
                            getView().showError(new Throwable(Constants.ErrorHandling.NO_INTERNET_CONNECTION), pullToRefresh);
                        } else if (result == null) {
                            getView().showError(new Throwable(Constants.ErrorHandling.DEFAULT), pullToRefresh);
                        } else if (result.isEmpty()) {
                            getView().showError(new Throwable(Constants.ErrorHandling.NO_RESULTS_FOR_REQUEST), pullToRefresh);
                        } else {
                            getView().setData(result);
                            getView().showContent();

                            if (PersistenceLogic.getInstance(context).shouldForecastDataUpdate(Constants.DB_WEEKLY_FORECAST_KEY, idWOE)) {
                                getView().showMessage(ErrorHandlingUtil.generateErrorText(context, Constants.ErrorHandling.CANNOT_UPDATE_CACHED_DATA));
                            }
                        }
                    }
                }

                @Override
                public void onError(Exception error) {
                    if (isViewAttached()) {
                        getView().showError(new Throwable(Constants.ErrorHandling.DEFAULT), pullToRefresh);
                    }
                }
            });
        }
    }
}
