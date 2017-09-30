package com.petar.weather.ui.views;

import android.databinding.ObservableField;

/**
 * An interface bound to the toolbar view, makes easier integration of the
 * toolbar functionality to the activity.
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 8.7.2017
 */
public interface IToolbarView {

    /**
     * Represents the location shown to the user.
     *
     * @return The title of the current location
     */
    ObservableField<String> getCurrentLocationTitle();

    /**
     * Called when the user interacts with the "current location" icon.
     */
    void onFindCurrentLocationClick();

    /**
     * Called when the user interacts with the "search" icon.
     */
    void onSearchClick();
}
