package com.petar.weather.logic.models;

import android.support.annotation.NonNull;

import com.petar.weather.app.Constants;
import com.petar.weather.ui.recycler.AListenerRecyclerItem;
import com.petar.weather.ui.recycler.IListener;
import com.petar.weather.util.TimeUtil;

/**
 * An abstract representation of a forecast model. This model is bound
 * to the view. Any changes in the networking/persistence models are
 * reflected in their respective implementation. This class should only
 * be changed if there are actual changes to the view!
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 24.6.2017
 */
public abstract class AForecast
        extends AListenerRecyclerItem<AForecast.IForecastListener>
        implements Comparable<AForecast> {

    // --------------------------------------------------------
    // VIEW-BOUND-METHODS region
    // --------------------------------------------------------

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

    /**
     * Click listener for the forecast item.
     */
    public void onItemClick() {
        if (getListener() != null) {
            getListener().onShowForecastDetails(this);
        }
    }

    // --------------------------------------------------------
    // End of VIEW-BOUND-METHODS region
    // --------------------------------------------------------

    @Override
    public int compareTo(@NonNull AForecast o) {
        return TimeUtil.compareDates(getApplicableDate(), o.getApplicableDate());
    }

    /**
     * The mediator between {@link AForecast} and any outside class. Establishes a communication
     * between these modules.
     */
    public interface IForecastListener extends IListener {

        /**
         * Navigates to the {@link com.petar.weather.ui.activities.ForecastDetailsActivity}.
         *
         * @param forecast The forecast item that has been clicked
         */
        void onShowForecastDetails(AForecast forecast);
    }
}
