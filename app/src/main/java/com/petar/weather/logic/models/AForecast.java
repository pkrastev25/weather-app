package com.petar.weather.logic.models;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.petar.weather.ui.recycler.AListenerRecyclerItem;
import com.petar.weather.ui.recycler.IListener;

import org.joda.time.DateTime;

/**
 * Created by User on 24.6.2017 г..
 */

public abstract class AForecast extends AListenerRecyclerItem<AForecast.IForecastListener> implements Comparable<AForecast>, Parcelable {

    public abstract String getWeatherState();

    public abstract String getCreatedDate();

    public abstract String getApplicableDate();

    public abstract String getImageType();

    public abstract Double getMaxTemp();

    public abstract Double getMinTemp();

    @Override
    public int compareTo(@NonNull AForecast o) {
        long timestamp = new DateTime(getApplicableDate()).getMillis();
        long oTimestamp = new DateTime(o.getApplicableDate()).getMillis();

        if (timestamp > oTimestamp) {
            return 1;
        } else if (timestamp < oTimestamp) {
            return -1;
        } else {
            return 0;
        }
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
