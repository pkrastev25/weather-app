package com.petar.weather.util;

import com.petar.weather.logic.models.AForecast;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by User on 23.6.2017 г..
 */

public class ForecastUtil {

    private static Set<Integer> mForecastHoursContainer;
    
    public static List<? extends AForecast> extractData(List<? extends AForecast> data) {
        List<? extends AForecast> result = new ArrayList<>();

        if (data.isEmpty()) {
            return result;
        }

        initTimestampField(data);
        Collections.sort(data);
        mForecastHoursContainer = new HashSet<>(Constants.FORECASTS_FOR_A_DAY);

        DateTime today = new DateTime().withTimeAtStartOfDay();
        DateTime forecastDate = new DateTime(data.get(0).getApplicableDate()).withTimeAtStartOfDay();

        int comparison = today.compareTo(forecastDate);

        if (comparison == 1) {
            result = extractDataForTheFuture(data);
        } else if (comparison == -1) {
            result = extractDataForThePast(data);
        } else {
            result = extractDataForThePresent(data);
        }

        mForecastHoursContainer.clear();
        mForecastHoursContainer = null;

        return result;
    }

    private static void initTimestampField(List<? extends AForecast> data) {
        for (AForecast entry: data) {
            entry.convertDateStringToTimestamp();
        }
    }

    private static List<? extends AForecast> extractDataForTheFuture(List<? extends AForecast> data) {
        long mostRecentDate = data.get(data.size() - 1).getTimestamp();
        return extractDataForDate(data, mostRecentDate);
    }

    private static List<? extends AForecast> extractDataForThePast(List<? extends AForecast> data) {
        long mostRecentDate = new DateTime(data.get(0).getApplicableDate()).getMillis();
        return extractDataForDate(data, mostRecentDate);
    }

    private static List<? extends AForecast> extractDataForThePresent(List<? extends AForecast> data) {
        List<AForecast> result = new ArrayList<>();
        DateTime today = new DateTime();

        while (mForecastHoursContainer.size() != Constants.FORECASTS_FOR_A_DAY) {
            result.addAll(extractDataForDate(data, today.getMillis()));
            today = today.minusDays(1);
        }

        /*
         * Since there is a mix of today and yesterday for the forecast, sorting
         * by timestamp will not work! An implementation of a custom comparator is
         * needed that sorts the dates by time, to be exact by the hours attribute
         */
        Collections.sort(result, new Comparator<AForecast>() {
            @Override
            public int compare(AForecast one, AForecast another) {
                int oneHourAttribute = new DateTime(one.getCreatedDate()).getHourOfDay();
                int anotherHourAttribute = new DateTime(another.getCreatedDate()).getHourOfDay();

                if (oneHourAttribute > anotherHourAttribute) {
                    return 1;
                } else if (oneHourAttribute < anotherHourAttribute) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        return result;
    }

    private static List<? extends AForecast> extractDataForDate(List<? extends AForecast> data, long timestamp) {
        List<AForecast> result = new ArrayList<>();
        DateTime compareDate = new DateTime(timestamp).withTimeAtStartOfDay();

        for (AForecast entry : data) {
            DateTime entryDate = new DateTime(entry.getCreatedDate());
            int hours = entryDate.getHourOfDay();

            if (compareDate.isEqual(entryDate.withTimeAtStartOfDay()) && !mForecastHoursContainer.contains(hours)) {
                mForecastHoursContainer.add(hours);
                result.add(entry);

                if (mForecastHoursContainer.size() == Constants.FORECASTS_FOR_A_DAY) {
                    return result;
                }
            }
        }

        return result;
    }
}
