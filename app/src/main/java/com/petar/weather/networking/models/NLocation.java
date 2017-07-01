package com.petar.weather.networking.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.petar.weather.logic.models.ILocation;

public class NLocation implements ILocation {

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
    public Integer getId() {
        return woeid;
    }

    @Override
    public int compareTo(@NonNull ILocation o) {
        if (distance > o.getDistance()) {
            return 1;
        } else if (distance < o.getDistance()) {
            return -1;
        } else {
            return 0;
        }
    }
}
