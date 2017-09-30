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
 * Contains the business logic for the {@link com.petar.weather.ui.fragments.DailyForecastFragment}.
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 8.7.2017
 */
public class DailyForecastFragmentPresenter extends MvpBasePresenter<IDailyForecastFragment> {

    private boolean mIsLoading;

    /**
     * Performs an API request for the daily forecast. Manipulates the view accordingly in
     * each step. Provides error detection and handling for the result.
     *
     * @param context       {@link Context} reference
     * @param idWOE         'Where on Earth ID', identifies a location
     * @param pullToRefresh True if the request is made from a pull-to-refresh view, false otherwise
     */
    public void loadLocationDailyForecast(final Context context, final int idWOE, final boolean pullToRefresh) {
        if (isViewAttached() && !mIsLoading) {
            mIsLoading = true;
            getView().showLoading(pullToRefresh);

            AsyncTaskUtil.doInBackground(new AsyncTaskUtil.IAsyncTaskHelperListener<List<? extends AForecast>>() {
                @Override
                public List<? extends AForecast> onExecuteTask() throws Exception {
                    return DataLogic.getInstance().getLocationDailyForecast(
                            context,
                            idWOE,
                            pullToRefresh
                    );
                }

                @Override
                public void onSuccess(List<? extends AForecast> result) {
                    if (isViewAttached()) {
                        if (!NetworkUtil.isNetworkConnected(context) && result == null) {
                            getView().showError(new Throwable(Constants.ErrorHandling.NO_INTERNET_CONNECTION), pullToRefresh);
                        } else if (result == null) {
                            getView().showError(new Throwable(Constants.ErrorHandling.DEFAULT), pullToRefresh);
                        } else if (result.isEmpty()) {
                            getView().showError(new Throwable(Constants.ErrorHandling.NO_RESULTS_FOR_REQUEST), pullToRefresh);
                        } else {
                            getView().setData(result);
                            getView().showContent();

                            if (PersistenceLogic.getInstance(context).shouldForecastDataUpdate(Constants.DB_DAILY_FORECAST_KEY, idWOE)) {
                                getView().showToastMessage(ErrorHandlingUtil.generateErrorText(context, Constants.ErrorHandling.CANNOT_UPDATE_CACHED_DATA));
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
