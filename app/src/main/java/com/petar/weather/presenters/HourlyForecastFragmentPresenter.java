package com.petar.weather.presenters;

import android.content.Context;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.petar.weather.logic.DataLogic;
import com.petar.weather.logic.models.AForecast;
import com.petar.weather.persistence.PersistenceLogic;
import com.petar.weather.ui.views.IHourlyForecastFragment;
import com.petar.weather.util.AsyncTaskUtil;
import com.petar.weather.app.Constants;
import com.petar.weather.util.ErrorHandlingUtil;
import com.petar.weather.util.FormatUtil;
import com.petar.weather.util.NetworkUtil;
import com.petar.weather.util.TimeUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
        mLimitForecastDate = TimeUtil.getCurrentTimeWithOffset(Constants.API_HOURLY_FORECAST_LIMIT_MILLIS);
    }

    public void loadNextForecast(Context context, int idWOE, boolean pullToRefresh) {
        loadForecast(context, idWOE, pullToRefresh, true);
    }

    public void loadForecastForToday(Context context, int idWOE, boolean pullToRefresh) {
        mCurrentForecastDate = TimeUtil.getCurrentTime();
        loadForecast(context, idWOE, pullToRefresh, false);
    }

    private void loadForecast(final Context context, final int idWOE, final boolean pullToRefresh, final boolean isNextForecast) {
        if (isViewAttached() && !mIsLoading && TimeUtil.isDateBeforeDate(mCurrentForecastDate, mLimitForecastDate)) {
            mIsLoading = true;
            final String dateRequestFormat = FormatUtil.formatDateRequest(
                    TimeUtil.convertDateToCalendarFromMillis(mCurrentForecastDate)
            );

            getView().showLoading(pullToRefresh);
            getView().setLoadingRecyclerItem();

            AsyncTaskUtil.doInBackground(new AsyncTaskUtil.IAsyncTaskHelperListener<List<? extends AForecast>>() {
                @Override
                public List<? extends AForecast> onExecuteTask() throws Exception {
                    return DataLogic.getInstance().getLocationForecastForDate(
                            context,
                            idWOE,
                            dateRequestFormat,
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
                            if (isNextForecast) {
                                getView().addNextForecast(result);
                            } else {
                                getView().setData(result);
                            }

                            getView().showContent();

                            if (PersistenceLogic.getInstance(context).shouldForecastDataUpdate(dateRequestFormat.hashCode(), idWOE)) {
                                getView().showMessage(ErrorHandlingUtil.generateErrorText(context, Constants.ErrorHandling.CANNOT_UPDATE_CACHED_DATA));
                            }

                            if (TimeUtil.isDateToday(mCurrentForecastDate)) {
                                getView().scrollToCurrentForecast();
                            }

                            mCurrentForecastDate = TimeUtil.addTimeOffsetToDate(mCurrentForecastDate, TimeUnit.DAYS.toMillis(1));

                            if (TimeUtil.isDateBeforeDate(mCurrentForecastDate, mLimitForecastDate) || TimeUtil.areDatesTheSameDate(mCurrentForecastDate, mLimitForecastDate)) {
                                getView().removeLoadingRecyclerItem();
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

    public boolean isLoading() {
        return mIsLoading;
    }
}
