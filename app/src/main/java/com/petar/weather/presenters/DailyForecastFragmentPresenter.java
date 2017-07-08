package com.petar.weather.presenters;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.petar.weather.logic.DataLogic;
import com.petar.weather.logic.models.ILocationForecast;
import com.petar.weather.ui.views.IDailyForecastFragment;
import com.petar.weather.util.AsyncTaskUtil;

/**
 * Created by User on 8.7.2017 г..
 */

public class DailyForecastFragmentPresenter extends MvpBasePresenter<IDailyForecastFragment> {

    public void loadLocationForecast(final int id) {
        if (isViewAttached()) {
            getView().showLoading(false);

            AsyncTaskUtil.doInBackground(new AsyncTaskUtil.IAsyncTaskHelperListener<ILocationForecast>() {
                @Override
                public ILocationForecast onExecuteTask() throws Exception {
                    return DataLogic.getInstance().getLocationForecast(
                            id
                    );
                }

                @Override
                public void onSuccess(ILocationForecast result) {
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
