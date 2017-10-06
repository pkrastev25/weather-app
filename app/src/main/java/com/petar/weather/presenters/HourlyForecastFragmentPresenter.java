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

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Contains the business logic for the {@link com.petar.weather.ui.fragments.HourlyForecastFragment}.
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 1.7.2017
 */
public class HourlyForecastFragmentPresenter extends MvpBasePresenter<IHourlyForecastFragment> {

    private boolean mIsLoading;
    private long mCurrentForecastDate;
    private long mLimitForecastDate;

    /**
     * Initialize the current forecast date, used to perform the API requests,
     * and the limit forecast date, {@link Constants#API_HOURLY_FORECAST_LIMIT_MILLIS}.
     */
    public HourlyForecastFragmentPresenter() {
        mCurrentForecastDate = TimeUtil.getCurrentTime();
        mLimitForecastDate = TimeUtil.getCurrentTimeWithOffset(Constants.API_HOURLY_FORECAST_LIMIT_MILLIS);
    }

    /**
     * Makes a check if the cached forecast data is up-to-date. If not, performs
     * a new API request to update it.
     *
     * @param context {@link Context} reference
     * @param idWOE   'Where on Earth ID', identifies a location
     */
    public void updateForecast(Context context, int idWOE) {
        long keyDB = FormatUtil.formatDateRequest(Calendar.getInstance()).hashCode();

        if (PersistenceLogic.getInstance(context).shouldForecastDataUpdate(keyDB, idWOE)) {
            loadHourlyForecastForToday(context, idWOE, false);
        }
    }

    /**
     * Performs an API request for the hourly forecast while keeping the previous results.
     * It adds the new data set to the already existing one.
     *
     * @param context       {@link Context} reference
     * @param idWOE         'Where on Earth ID', identifies a location
     * @param pullToRefresh True if the request is made from a pull-to-refresh view, false otherwise
     */
    public void loadHourlyForecastForNextDay(Context context, int idWOE, boolean pullToRefresh) {
        loadHourlyForecast(context, idWOE, pullToRefresh, true);
    }

    /**
     * Performs an API request for the hourly forecast. It discards the old data set and
     * replaces it completely with the new one.
     *
     * @param context       {@link Context} reference
     * @param idWOE         'Where on Earth ID', identifies a location
     * @param pullToRefresh True if the request is made from a pull-to-refresh view, false otherwise
     */
    public void loadHourlyForecastForToday(Context context, int idWOE, boolean pullToRefresh) {
        mCurrentForecastDate = TimeUtil.getCurrentTime();
        loadHourlyForecast(context, idWOE, pullToRefresh, false);
    }

    /**
     * Performs an API request for the hourly forecast. Manipulates the view accordingly in
     * each step. Provides error detection and handling for the result.
     *
     * @param context           {@link Context} reference
     * @param idWOE             'Where on Earth ID', identifies a location
     * @param pullToRefresh     True if the request is made from a pull-to-refresh view, false otherwise
     * @param isNextDayForecast If true, it will not discard the old data set, it will add the new result to it, false otherwise
     */
    private void loadHourlyForecast(final Context context, final int idWOE, final boolean pullToRefresh, final boolean isNextDayForecast) {
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
                    return DataLogic.getInstance().getLocationHourlyForecastForDate(
                            context,
                            idWOE,
                            dateRequestFormat,
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
                            if (isNextDayForecast) {
                                getView().addNextDayForecast(result);
                            } else {
                                getView().setData(result);
                            }

                            getView().showContent();

                            if (PersistenceLogic.getInstance(context).shouldForecastDataUpdate(dateRequestFormat.hashCode(), idWOE)) {
                                getView().showToastMessage(ErrorHandlingUtil.generateErrorText(context, Constants.ErrorHandling.CANNOT_UPDATE_CACHED_DATA));
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
