package com.petar.weather.ui.views;

import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by User on 22.6.2017 г..
 */

public interface IMainActivity extends MvpView {

    void hideSplashScreen();

    void navigateToForecastActivity(int id);
}
