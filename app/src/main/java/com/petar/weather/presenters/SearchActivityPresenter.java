package com.petar.weather.presenters;

import android.content.Context;
import android.text.TextUtils;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.petar.weather.app.Constants;
import com.petar.weather.logic.DataLogic;
import com.petar.weather.logic.models.ALocation;
import com.petar.weather.ui.views.ISearchActivity;
import com.petar.weather.util.AsyncTaskUtil;
import com.petar.weather.util.ErrorHandlingUtil;
import com.petar.weather.util.NetworkUtil;

import java.util.List;

/**
 * Created by User on 27.8.2017 г..
 */

public class SearchActivityPresenter extends MvpBasePresenter<ISearchActivity> {

    private static final String COMMA_SYMBOL = ",";
    private boolean mIsLoading;

    public void processQuery(Context context, String query, boolean pullToRefresh) {
        if (TextUtils.isEmpty(query)) {
            // TODO: Show no input error
        } else if (query.contains(COMMA_SYMBOL)) {
            // TODO: Add a regex for the coordinates
            processCoordinatesQuery(context, query, pullToRefresh);
        } else {
            // TODO: Add a regex check for characters only
            processStringQuery(context, query, pullToRefresh);
        }
    }

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
                        if (!NetworkUtil.isNetworkAvailable(context) && result == null) {
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
                        if (!NetworkUtil.isNetworkAvailable(context) && result == null) {
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
