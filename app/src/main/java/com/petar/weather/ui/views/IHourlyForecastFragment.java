package com.petar.weather.ui.views;

import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;
import com.petar.weather.logic.models.AForecast;

import java.util.List;

/**
 * Created by User on 1.7.2017 г..
 */

public interface IHourlyForecastFragment extends MvpLceView<List<? extends AForecast>> {
}
