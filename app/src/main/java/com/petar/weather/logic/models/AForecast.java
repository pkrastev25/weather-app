package com.petar.weather.logic.models;

import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by User on 24.6.2017 г..
 */

public abstract class AForecast implements Comparable<AForecast>, Parcelable {

    protected long mTimestamp;

    protected IForecastListener mListener;

    public void setListener(IForecastListener listener) {
        mListener = listener;
    }

    public long getTimestamp() {
        return mTimestamp;
    }

    public abstract void convertDateStringToTimestamp();

    public abstract String getWeatherState();

    public abstract String getCreatedDate();

    public abstract String getApplicableDate();

    public abstract String getImageType();

    public abstract Double getMaxTemp();

    public abstract Double getMinTemp();

    @Override
    public int compareTo(@NonNull AForecast o) {
        if (mTimestamp > o.mTimestamp) {
            return 1;
        } else if (mTimestamp < o.mTimestamp) {
            return -1;
        } else {
            return 0;
        }
    }

    public void onItemClick() {
        if (mListener != null) {
            mListener.onItemClick(this);
        }
    }

    public interface IForecastListener {
        void onItemClick(AForecast forecast);
    }
}
