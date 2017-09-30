package com.petar.weather.listeners;

/**
 * The mediator between {@link com.petar.weather.ui.fragments.DailyForecastFragment}/
 * {@link com.petar.weather.ui.fragments.HourlyForecastFragment}. Establishes
 * a communication between these modules.
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 27.8.2017
 */
public interface IForecastFragmentListener {

    /**
     * Notifies the listener that the fragment is created.
     */
    void onFragmentCreated();
}
