package com.petar.weather.networking.models;

import com.google.gson.annotations.SerializedName;
import com.petar.weather.logic.models.AForecast;
import com.petar.weather.logic.models.IDailyForecast;

import java.util.List;

/**
 * Networking model for the daily forecast that is mapped to the API response.
 *
 * @author Petar Krastev
 * @version 1.0
 * @see <a href="http://www.jsonschema2pojo.org/">http://www.jsonschema2pojo.org/</a>
 * @since 21.9.2017
 */
public class NDailyForecast implements IDailyForecast {

    @SerializedName("consolidated_weather")
    private List<NForecast> consolidatedWeather = null;

    @Override
    public List<? extends AForecast> getForecast() {
        return consolidatedWeather;
    }
}
