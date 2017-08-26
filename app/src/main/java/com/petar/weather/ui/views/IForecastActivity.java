package com.petar.weather.ui.views;

import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;
import com.petar.weather.logic.models.ALocation;

/**
 * Created by User on 23.6.2017 г..
 */

public interface IForecastActivity extends MvpLceView<ALocation> {

    void setShowContentState();
}
