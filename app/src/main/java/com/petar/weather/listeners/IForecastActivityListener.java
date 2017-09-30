package com.petar.weather.listeners;

import android.support.annotation.NonNull;

/**
 * The mediator between {@link com.petar.weather.ui.activities.ForecastActivity}
 * and {@link com.petar.weather.ui.fragments.DailyForecastFragment}/
 * {@link com.petar.weather.ui.fragments.HourlyForecastFragment}. Establishes
 * a communication between these modules.
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 29.9.2017
 */
public interface IForecastActivityListener {

    /**
     * Notifies the listener that the current location has been found.
     *
     * @param idWOE 'Where on Earth ID', identifies a location
     */
    void onLocationFound(@NonNull Integer idWOE);
}
