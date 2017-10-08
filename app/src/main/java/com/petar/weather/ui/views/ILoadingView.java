package com.petar.weather.ui.views;

/**
 * An interface bound to the loading view, makes easier integration of the
 * loading view functionality to any class.
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 1.10.2017
 */
public interface ILoadingView {

    /**
     * The loading message which will be shown on the loading view.
     *
     * @return Loading message/text
     */
    String getLoadingMessage();
}
