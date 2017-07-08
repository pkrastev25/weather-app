package com.petar.weather.presenters;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.petar.weather.logic.DataLogic;
import com.petar.weather.logic.models.AForecast;
import com.petar.weather.ui.views.IHourlyForecastFragment;
import com.petar.weather.util.AsyncTaskUtil;
import com.petar.weather.util.ForecastUtil;
import com.petar.weather.util.FormatUtil;

import java.util.Calendar;
import java.util.List;

/**
 * Created by User on 1.7.2017 г..
 */

public class HourlyForecastFragmentPresenter extends MvpBasePresenter<IHourlyForecastFragment> {

    public void loadForecast(final int id) {
        if (isViewAttached()) {
            getView().showLoading(false);

            AsyncTaskUtil.doInBackground(new AsyncTaskUtil.IAsyncTaskHelperListener<List<? extends AForecast>>() {
                @Override
                public List<? extends AForecast> onExecuteTask() throws Exception {
                    return DataLogic.getInstance().getLocationForecastForDate(
                            id,
                            FormatUtil.dateRequestFormat(Calendar.getInstance())
                    );
                }

                @Override
                public void onSuccess(List<? extends AForecast> result) {
                    if (isViewAttached()) {
                        getView().setData(ForecastUtil.extractData(result));
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
