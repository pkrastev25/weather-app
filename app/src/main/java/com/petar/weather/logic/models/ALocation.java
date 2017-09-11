package com.petar.weather.logic.models;

import android.support.annotation.NonNull;

import com.petar.weather.ui.recycler.AListenerRecyclerItem;
import com.petar.weather.ui.recycler.IListener;

/**
 * Created by User on 29.6.2017 г..
 */

public abstract class ALocation extends AListenerRecyclerItem<ALocation.ILocationListener> implements Comparable<ALocation> {

    public abstract int getDistance();

    public abstract String getTitle();

    public abstract Integer getIdWOE();

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
        if (getListener() != null) {
            getListener().onItemClick(this);
        }
    }

    public interface ILocationListener extends IListener {
        void onItemClick(ALocation location);
    }
}
