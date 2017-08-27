package com.petar.weather.presenters;

import android.text.TextUtils;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.petar.weather.logic.DataLogic;
import com.petar.weather.logic.models.ALocation;
import com.petar.weather.ui.views.ISearchActivity;
import com.petar.weather.util.AsyncTaskUtil;

import java.util.List;

/**
 * Created by User on 27.8.2017 г..
 */

public class SearchActivityPresenter extends MvpBasePresenter<ISearchActivity> {

    private static final String COMMA_SYMBOL = ",";
    private boolean mIsLoading;

    public void processQuery(final String query, final boolean pullToRefresh) {
        if (TextUtils.isEmpty(query)) {
            // TODO: Show no input error
        } else if (query.contains(COMMA_SYMBOL)) {
            // TODO: Add a regex for the coordinates
            processCoordinatesQuery(query, pullToRefresh);
        } else {
            // TODO: Add a regex check for characters only
            processStringQuery(query, pullToRefresh);
        }
    }

    private void processStringQuery(final String query, final boolean pullToRefresh) {
        if (isViewAttached() && !mIsLoading) {
            getView().showLoading(pullToRefresh);
            mIsLoading = true;

            AsyncTaskUtil.doInBackground(new AsyncTaskUtil.IAsyncTaskHelperListener<List<? extends ALocation>>() {
                @Override
                public List<? extends ALocation> onExecuteTask() throws Exception {
                    return DataLogic.getInstance().getLocationQueryResult(
                            query
                    );
                }

                @Override
                public void onSuccess(List<? extends ALocation> result) {
                    if (isViewAttached()) {
                        getView().setData(result);
                        getView().showContent();
                    }

                    mIsLoading = false;
                }

                @Override
                public void onError(Exception error) {
                    mIsLoading = false;
                }
            });
        }
    }

    private void processCoordinatesQuery(final String query, final boolean pullToRefresh) {
        if (isViewAttached() && !mIsLoading) {
            getView().showLoading(pullToRefresh);
            mIsLoading = true;

            AsyncTaskUtil.doInBackground(new AsyncTaskUtil.IAsyncTaskHelperListener<List<? extends ALocation>>() {
                @Override
                public List<? extends ALocation> onExecuteTask() throws Exception {
                    return DataLogic.getInstance().getLocationQueryResultWithCoordinates(
                            query
                    );
                }

                @Override
                public void onSuccess(List<? extends ALocation> result) {
                    if (isViewAttached()) {
                        getView().setData(result);
                        getView().showContent();
                    }

                    mIsLoading = false;
                }

                @Override
                public void onError(Exception error) {
                    mIsLoading = false;
                }
            });
        }
    }
}
