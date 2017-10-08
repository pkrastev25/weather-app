package com.petar.weather.ui.views;

import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;
import com.petar.weather.logic.models.ALocation;

/**
 * The mediator between {@link com.petar.weather.ui.activities.ForecastActivity} and
 * {@link com.petar.weather.presenters.ForecastActivityPresenter}. All methods here
 * are exposed to the presenter.
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 23.6.2017
 */
public interface IForecastActivity extends MvpLceView<ALocation> {

    /**
     * Shows a message to the user in the form of a {@link android.widget.Toast}.
     *
     * @param message The message to be shown
     */
    void showToastMessage(String message);

    /**
     * References the current location shown in the toolbar.
     *
     * @return The current location
     */
    ALocation getCurrentLocationShown();

    /**
     * Used to notify the view that the location did not change, the current location
     * is the same like the new location.
     */
    void onLocationDidNotChange();
}
