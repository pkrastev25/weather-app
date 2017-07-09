package com.petar.weather.logic.models;

import android.support.annotation.NonNull;

/**
 * Created by User on 29.6.2017 г..
 */

public abstract class ALocation implements Comparable<ALocation> {

    protected ILocationListener mListener;

    public void setListener(ILocationListener listener) {
        mListener = listener;
    }

    public abstract Integer getDistance();

    public abstract String getTitle();

    public abstract Integer getId();

    @Override
    public int compareTo(@NonNull ALocation o) {
        if (getDistance() > o.getDistance()) {
            return 1;
        } else if (getDistance() < o.getDistance()) {
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

    public interface ILocationListener {
        void onItemClick(ALocation location);
    }
}
