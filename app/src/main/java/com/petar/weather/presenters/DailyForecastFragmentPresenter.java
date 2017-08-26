package com.petar.weather.presenters;

import android.content.Context;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.petar.weather.logic.DataLogic;
import com.petar.weather.logic.models.AForecast;
import com.petar.weather.ui.views.IDailyForecastFragment;
import com.petar.weather.util.AsyncTaskUtil;

import java.util.List;

/**
 * Created by User on 8.7.2017 г..
 */

public class DailyForecastFragmentPresenter extends MvpBasePresenter<IDailyForecastFragment> {

    public void loadLocationForecast(final Context context, final int id, final boolean pullToRefresh) {
        if (isViewAttached()) {
            getView().showLoading(pullToRefresh);

            AsyncTaskUtil.doInBackground(new AsyncTaskUtil.IAsyncTaskHelperListener<List<? extends AForecast>>() {
                @Override
                public List<? extends AForecast> onExecuteTask() throws Exception {
                    return DataLogic.getInstance().getLocationWeeklyForecast(
                            context,
                            id,
                            pullToRefresh
                    );
                }

                @Override
                public void onSuccess(List<? extends AForecast> result) {
                    if (isViewAttached()) {
                        getView().setData(result);
                        getView().showContent();
                    }
                }

                @Override
                public void onError(Exception error) {

                }
            });
        }
    }
}
