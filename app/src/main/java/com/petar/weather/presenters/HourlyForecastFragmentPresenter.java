package com.petar.weather.presenters;

import android.content.Context;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.petar.weather.logic.DataLogic;
import com.petar.weather.logic.models.AForecast;
import com.petar.weather.ui.views.IHourlyForecastFragment;
import com.petar.weather.util.AsyncTaskUtil;
import com.petar.weather.app.Constants;
import com.petar.weather.util.FormatUtil;
import com.petar.weather.util.TimeUtil;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by User on 1.7.2017 г..
 */

public class HourlyForecastFragmentPresenter extends MvpBasePresenter<IHourlyForecastFragment> {

    private boolean mIsLoading;
    private long mCurrentForecastDate;
    private long mLimitForecastDate;

    public HourlyForecastFragmentPresenter() {
        super();

        mCurrentForecastDate = TimeUtil.getCurrentTime();
        mLimitForecastDate = new DateTime(
                TimeUtil.getCurrentTimeWithDayOffset(Constants.OFFSET_DAYS_FOR_FORECAST)
        ).withTimeAtStartOfDay().getMillis();
    }

    public void loadForecastForDate(final Context context, final int id, final boolean pullToRefresh) {
        if (isViewAttached() && !mIsLoading && new DateTime(mCurrentForecastDate).withTimeAtStartOfDay().isBefore(mLimitForecastDate)) {
            mIsLoading = true;
            getView().showLoading(pullToRefresh);
            getView().removeLoadingRecyclerItem();

            AsyncTaskUtil.doInBackground(new AsyncTaskUtil.IAsyncTaskHelperListener<List<? extends AForecast>>() {
                @Override
                public List<? extends AForecast> onExecuteTask() throws Exception {
                    return DataLogic.getInstance().getLocationForecastForDate(
                            context,
                            id,
                            FormatUtil.dateRequestFormat(
                                    TimeUtil.convertDateToCalendarFromMillis(mCurrentForecastDate)
                            ),
                            pullToRefresh
                    );
                }

                @Override
                public void onSuccess(List<? extends AForecast> result) {
                    if (isViewAttached()) {
                        getView().setData(result);
                        getView().showContent();

                        DateTime today = new DateTime().withTimeAtStartOfDay();
                        DateTime forecastDate = new DateTime(mCurrentForecastDate).withTimeAtStartOfDay();

                        if (today.isEqual(forecastDate)) {
                            getView().scrollToCurrentForecast();
                        }

                        mCurrentForecastDate = TimeUtil.addDayOffsetToDate(mCurrentForecastDate, 1);

                        if (new DateTime(mCurrentForecastDate).withTimeAtStartOfDay().isBefore(mLimitForecastDate)) {
                            getView().setLoadingRecyclerItem();
                        }
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

    public void loadForecastForToday(Context context, int id, boolean pullToRefresh) {
        mCurrentForecastDate = TimeUtil.getCurrentTime();

        loadForecastForDate(context, id, pullToRefresh);
    }

    public boolean isLoading() {
        return mIsLoading;
    }

    public long getCurrentForecastDate() {
        return mCurrentForecastDate;
    }
}
