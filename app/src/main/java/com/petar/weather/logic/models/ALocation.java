package com.petar.weather.logic.models;

import android.support.annotation.NonNull;

import com.petar.weather.ui.recycler.AListenerRecyclerItem;
import com.petar.weather.ui.recycler.IListener;

/**
 * An abstract representation of a location model. This model is bound
 * to the view. Any changes in the networking/persistence models are
 * reflected in their respective implementation. This class should only
 * be changed if there are actual changes to the view!
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 29.6.2017
 */
public abstract class ALocation
        extends AListenerRecyclerItem<ALocation.ILocationListener>
        implements Comparable<ALocation> {

    // --------------------------------------------------------
    // VIEW-BOUND-METHODS region
    // --------------------------------------------------------

    public abstract int getDistance();

    public abstract String getTitle();

    public abstract Integer getIdWOE();

    // --------------------------------------------------------
    // End of VIEW-BOUND-METHODS region
    // --------------------------------------------------------

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

    /**
     * Click listener for the location item.
     */
    public void onItemClick() {
        if (getListener() != null) {
            getListener().onLocationChosen(this);
        }
    }

    /**
     * The mediator between {@link ALocation} and any outside class. Establishes a communication
     * between these modules.
     */
    public interface ILocationListener extends IListener {

        /**
         * Navigates to the {@link com.petar.weather.ui.activities.ForecastActivity},
         * provides the new location of the user.
         *
         * @param location The location item that has been clicked
         */
        void onLocationChosen(ALocation location);
    }
}
