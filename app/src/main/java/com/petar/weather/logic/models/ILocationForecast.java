package com.petar.weather.logic.models;

import java.util.List;

/**
 * Created by User on 29.6.2017 г..
 */

public interface ILocationForecast {

    List<? extends AForecast> getForecast();
}
