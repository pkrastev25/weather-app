package com.petar.weather.ui.views;

/**
 * An interface bound to the error view, makes easier integration of the
 * error view functionality to any class.
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 1.10.2017
 */
public interface IErrorView {

    /**
     * Attempts to resolve the error by calling the loading functionality.
     */
    void onReload();
}
