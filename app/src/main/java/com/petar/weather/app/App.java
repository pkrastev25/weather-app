package com.petar.weather.app;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Base class for the application state.
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 5.9.2017
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize the library
        JodaTimeAndroid.init(this);
    }
}
