package com.petar.weather.networking.models;

import com.google.gson.annotations.SerializedName;
import com.petar.weather.logic.models.ALocation;

public class NLocation extends ALocation {

    @SerializedName("distance")
    private Integer distance;
    @SerializedName("title")
    private String title;
    @SerializedName("location_type")
    private String locationType;
    @SerializedName("woeid")
    private Integer woeid;
    @SerializedName("latt_long")
    private String lattLong;

    public Integer getDistance() {
        return distance;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Integer getId() {
        return woeid;
    }
}
