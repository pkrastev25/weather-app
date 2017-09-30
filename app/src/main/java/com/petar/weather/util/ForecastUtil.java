package com.petar.weather.util;

import android.content.Context;

import com.petar.weather.R;
import com.petar.weather.app.Constants;
import com.petar.weather.logic.models.AForecast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Helper used to handle all manipulations with {@link AForecast}.
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 23.6.2017
 */
public class ForecastUtil {

    private static Set<Integer> mForecastHoursContainer;

    /**
     * Since the API provides multiple data points for a given forecast, this implementation
     * filters out only the most accurate and relevant points. Out of all data points,
     * into consideration are taken only the ones with the most recent dates. The end result
     * is sorted in ascended order.
     *
     * @param data All the data points for a given forecast
     * @return The most accurate forecast data
     */
    public static List<? extends AForecast> extractData(List<? extends AForecast> data) {
        List<? extends AForecast> result = new ArrayList<>();

        if (data.isEmpty()) {
            return result;
        }

        Collections.sort(data);
        mForecastHoursContainer = new HashSet<>(Constants.FORECASTS_FOR_A_DAY);

        int comparison = TimeUtil.compareTodayWithDate(data.get(0).getApplicableDate());

        if (comparison == 1) {
            result = extractDataForThePast(data);
        } else if (comparison == -1) {
            result = extractDataForTheFuture(data);
        } else {
            result = extractDataForThePresent(data);
        }

        mForecastHoursContainer.clear();
        mForecastHoursContainer = null;

        return result;
    }

    /**
     * Extracts the most accurate data points from the forecast data set. Starts by finding the
     * most recent forecast date from the data set, then extracts all forecast points. If the points
     * are not equal to {@link Constants#FORECASTS_FOR_A_DAY}, subtracts a day from the most recent date and
     * repeats the process. A call to {@link #extractDataForDate(List, long)} is used to realize
     * this implementation.
     *
     * @param data All the data points for a given forecast
     * @return The most accurate forecast data
     */
    private static List<? extends AForecast> extractDataForTheFuture(List<? extends AForecast> data) {
        List<AForecast> result = new ArrayList<>();
        long date = TimeUtil.convertDateFromISOString(data.get(0).getCreatedDate());

        while (mForecastHoursContainer.size() != Constants.FORECASTS_FOR_A_DAY) {
            result.addAll(extractDataForDate(data, date));
            date = TimeUtil.subtractTimeOffsetFromDate(date, TimeUnit.DAYS.toMillis(1));
        }

        /*
         * Since there is a mix of different dates for the forecast, sorting
         * by timestamp will not work! An implementation of a custom comparator is
         * needed that sorts the dates by time, to be exact by the hours attribute
         */
        Collections.sort(result, new Comparator<AForecast>() {
            @Override
            public int compare(AForecast one, AForecast another) {
                Integer oneHourAttribute = TimeUtil.getHoursTimeForISOString(one.getCreatedDate());
                Integer anotherHourAttribute = TimeUtil.getHoursTimeForISOString(another.getCreatedDate());

                return oneHourAttribute.compareTo(anotherHourAttribute);
            }
        });

        return result;
    }

    /**
     * Extracts the most accurate data points from the forecast data set. Starts by finding the
     * most recent forecast date from the data set, then extracts all forecast points. Since the
     * date is in the past, we can be assured that the extracted data points are the most
     * accurate ones.
     *
     * @param data All the data points for a given forecast
     * @return The most accurate forecast data
     */
    private static List<? extends AForecast> extractDataForThePast(List<? extends AForecast> data) {
        return extractDataForDate(data, TimeUtil.convertDateFromISOString(data.get(0).getApplicableDate()));
    }

    /**
     * Extracts the most accurate data points from the forecast data set. Starts by finding the
     * most recent forecast date from the data set, then extracts all forecast points. If the points
     * are not equal to {@link Constants#FORECASTS_FOR_A_DAY}, subtracts a day from the most recent date
     * and repeats the process. A call to {@link #extractDataForDate(List, long)} is used to realize
     * this implementation.
     *
     * @param data All the data points for a given forecast
     * @return The most accurate forecast data
     */
    private static List<? extends AForecast> extractDataForThePresent(List<? extends AForecast> data) {
        List<AForecast> result = new ArrayList<>();
        long date = TimeUtil.getCurrentTime();

        while (mForecastHoursContainer.size() != Constants.FORECASTS_FOR_A_DAY) {
            result.addAll(extractDataForDate(data, date));
            date = TimeUtil.subtractTimeOffsetFromDate(date, TimeUnit.DAYS.toMillis(1));
        }

        /*
         * Since there is a mix of today and yesterday for the forecast, sorting
         * by timestamp will not work! An implementation of a custom comparator is
         * needed that sorts the dates by time, to be exact by the hours attribute
         */
        Collections.sort(result, new Comparator<AForecast>() {
            @Override
            public int compare(AForecast one, AForecast another) {
                Integer oneHourAttribute = TimeUtil.getHoursTimeForISOString(one.getCreatedDate());
                Integer anotherHourAttribute = TimeUtil.getHoursTimeForISOString(another.getCreatedDate());

                return oneHourAttribute.compareTo(anotherHourAttribute);
            }
        });

        return result;
    }

    /**
     * Extracts all forecast data points for the given date. It may happen that the end
     * result does not equal {@link Constants#FORECASTS_FOR_A_DAY}!
     *
     * @param data All the data points for a given forecast
     * @param date The date for which the forecasts should be filtered
     * @return The most accurate forecast data
     */
    private static List<? extends AForecast> extractDataForDate(List<? extends AForecast> data, long date) {
        List<AForecast> result = new ArrayList<>();

        for (AForecast entry : data) {
            long entryDate = TimeUtil.convertDateFromISOString(entry.getCreatedDate());
            int hours = TimeUtil.getHoursTimeForISOString(entry.getCreatedDate());

            if (TimeUtil.areDatesTheSameDate(date, entryDate) && !mForecastHoursContainer.contains(hours)) {
                mForecastHoursContainer.add(hours);
                result.add(entry);

                if (mForecastHoursContainer.size() == Constants.FORECASTS_FOR_A_DAY) {
                    return result;
                }
            }
        }

        return result;
    }

    /**
     * Generates appropriate text for the weather state which will be displayed to the user.
     *
     * @param context      {@link Context} reference
     * @param weatherState The weather state, one of type {@link com.petar.weather.app.Constants.APIWeatherStateSummary}
     * @return Helper text
     */
    public static String generateTextForWeatherStateSummary(Context context, @Constants.APIWeatherStateSummary String weatherState) {
        switch (weatherState) {
            case Constants.APIWeatherStateSummary.SNOW:
                return context.getString(R.string.forecast_state_snow);
            case Constants.APIWeatherStateSummary.SLEET:
                return context.getString(R.string.forecast_state_sleet);
            case Constants.APIWeatherStateSummary.HAIL:
                return context.getString(R.string.forecast_state_hail);
            case Constants.APIWeatherStateSummary.THUNDERSTORM:
                return context.getString(R.string.forecast_state_thunderstorm);
            case Constants.APIWeatherStateSummary.HEAVY_RAIN:
                return context.getString(R.string.forecast_state_heavy_rain);
            case Constants.APIWeatherStateSummary.LIGHT_RAIN:
                return context.getString(R.string.forecast_state_light_rain);
            case Constants.APIWeatherStateSummary.SHOWERS:
                return context.getString(R.string.forecast_state_showers);
            case Constants.APIWeatherStateSummary.HEAVY_CLOUD:
                return context.getString(R.string.forecast_state_heavy_cloud);
            case Constants.APIWeatherStateSummary.LIGHT_CLOUD:
                return context.getString(R.string.forecast_state_light_cloud);
            case Constants.APIWeatherStateSummary.CLEAR:
            default:
                return context.getString(R.string.forecast_state_clear);
        }
    }
}
