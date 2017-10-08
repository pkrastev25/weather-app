package com.petar.weather.logic.models;

import java.util.List;

/**
 * An abstract representation of a daily forecast model.
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 29.6.2017
 */
public interface IDailyForecast {

    /**
     * Getter.
     *
     * @return The daily forecast
     */
    List<? extends AForecast> getForecast();
}
