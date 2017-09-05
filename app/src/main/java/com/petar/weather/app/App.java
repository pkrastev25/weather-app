package com.petar.weather.app;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by User on 5.9.2017 г..
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        JodaTimeAndroid.init(this);
    }
}
