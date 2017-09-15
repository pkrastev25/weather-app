package com.petar.weather.logic.models;

import android.support.annotation.NonNull;

import com.petar.weather.app.Constants;
import com.petar.weather.ui.recycler.AListenerRecyclerItem;
import com.petar.weather.ui.recycler.IListener;
import com.petar.weather.util.TimeUtil;


/**
 * Created by User on 24.6.2017 г..
 */

public abstract class AForecast extends AListenerRecyclerItem<AForecast.IForecastListener> implements Comparable<AForecast> {

    public abstract String getWeatherState();

    @Constants.APIWeatherStateSummary
    public abstract String getWeatherStateSummary();

    public abstract String getCreatedDate();

    public abstract String getApplicableDate();

    @Constants.APIWeatherStateSummary
    public abstract String getImageType();

    public abstract double getWindSpeed();

    public abstract double getWindDirection();

    public abstract String getWindDirectionCompass();

    public abstract double getMaxTemp();

    public abstract double getMinTemp();

    public abstract double getAirPressure();

    public abstract double getHumidity();

    @Override
    public int compareTo(@NonNull AForecast o) {
        return TimeUtil.compareDates(getApplicableDate(), o.getApplicableDate());
    }

    public void onItemClick() {
        if (getListener() != null) {
            getListener().onItemClick(this);
        }
    }

    public interface IForecastListener extends IListener {
        void onItemClick(AForecast forecast);
    }
}
