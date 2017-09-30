package com.petar.weather.presenters;

import android.content.Context;
import android.text.TextUtils;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.petar.weather.app.Constants;
import com.petar.weather.logic.DataLogic;
import com.petar.weather.logic.models.ALocation;
import com.petar.weather.ui.views.ISearchActivity;
import com.petar.weather.util.AsyncTaskUtil;
import com.petar.weather.util.NetworkUtil;

import java.util.List;

/**
 * Contains the business logic for the {@link com.petar.weather.ui.activities.SearchActivity}.
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 27.8.2017
 */
public class SearchActivityPresenter extends MvpBasePresenter<ISearchActivity> {

    private boolean mIsLoading;

    /**
     * Provides error detection and handling for the input. Calls {@link #processStringQuery(Context, String, boolean)}
     * for a string input or {@link #processCoordinatesQuery(Context, String, boolean)} for coordinates input.
     *
     * @param context       {@link Context} reference
     * @param query         Either a string location or coordinates in the form of {@link Constants#FORMAT_COORDINATES}
     * @param pullToRefresh True if the request is made from a pull-to-refresh view, false otherwise
     */
    public void processQuery(Context context, String query, boolean pullToRefresh) {
        if (isViewAttached()) {
            if (TextUtils.isEmpty(query)) {
                getView().showError(new Throwable(Constants.ErrorHandling.NO_SEARCH_INPUT), pullToRefresh);
            } else if (query.contains(Constants.GENERAL_COMMA_SYMBOL)) {
                if (Constants.REGEX_COORDINATES.matcher(query).matches()) {
                    getView().showError(new Throwable(), pullToRefresh);
                } else {
                    processCoordinatesQuery(context, query, pullToRefresh);
                }
            } else {
                if (Constants.REGEX_CITY_NAME.matcher(query).matches()) {
                    getView().showError(new Throwable(), pullToRefresh);
                } else {
                    processStringQuery(context, query, pullToRefresh);
                }
            }
        }
    }

    /**
     * Performs an API request for the given query location. Manipulates the view accordingly in
     * each step. Provides error detection and handling for the result.
     *
     * @param context       {@link Context} reference
     * @param query         The location query of interest
     * @param pullToRefresh True if the request is made from a pull-to-refresh view, false otherwise
     */
    private void processStringQuery(final Context context, final String query, final boolean pullToRefresh) {
        if (isViewAttached() && !mIsLoading) {
            getView().showLoading(pullToRefresh);
            mIsLoading = true;

            AsyncTaskUtil.doInBackground(new AsyncTaskUtil.IAsyncTaskHelperListener<List<? extends ALocation>>() {
                @Override
                public List<? extends ALocation> onExecuteTask() throws Exception {
                    return DataLogic.getInstance().getLocationQueryResult(
                            context,
                            query
                    );
                }

                @Override
                public void onSuccess(List<? extends ALocation> result) {
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

    /**
     * Performs an API request for the given coordinates of a location. Manipulates the view accordingly in
     * each step. Provides error detection and handling for the result.
     *
     * @param context       {@link Context} reference
     * @param query         Coordinates in the form of {@link Constants#FORMAT_COORDINATES}
     * @param pullToRefresh True if the request is made from a pull-to-refresh view, false otherwise
     */
    private void processCoordinatesQuery(final Context context, final String query, final boolean pullToRefresh) {
        if (isViewAttached() && !mIsLoading) {
            getView().showLoading(pullToRefresh);
            mIsLoading = true;

            AsyncTaskUtil.doInBackground(new AsyncTaskUtil.IAsyncTaskHelperListener<List<? extends ALocation>>() {
                @Override
                public List<? extends ALocation> onExecuteTask() throws Exception {
                    return DataLogic.getInstance().getLocationQueryResultWithCoordinates(
                            context,
                            query
                    );
                }

                @Override
                public void onSuccess(List<? extends ALocation> result) {
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
