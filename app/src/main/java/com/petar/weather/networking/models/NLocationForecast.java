package com.petar.weather.networking.models;

import com.google.gson.annotations.SerializedName;
import com.petar.weather.logic.models.ILocationForecast;

import java.util.List;

/**
 * Created by User on 21.6.2017 г..
 */

public class NLocationForecast implements ILocationForecast {

    @SerializedName("consolidated_weather")
    private List<NForecast> consolidatedWeather = null;
    @SerializedName("time")
    private String time;
    @SerializedName("sun_rise")
    private String sunRise;
    @SerializedName("sun_set")
    private String sunSet;
    @SerializedName("timezone_name")
    private String timezoneName;
    @SerializedName("parent")
    private NParent parent;
    @SerializedName("sources")
    private List<NSource> sources = null;
    @SerializedName("title")
    private String title;
    @SerializedName("location_type")
    private String locationType;
    @SerializedName("woeid")
    private Integer woeid;
    @SerializedName("latt_long")
    private String lattLong;
    @SerializedName("timezone")
    private String timezone;
}
