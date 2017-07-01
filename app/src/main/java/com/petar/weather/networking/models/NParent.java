package com.petar.weather.networking.models;

import com.google.gson.annotations.SerializedName;
import com.petar.weather.logic.models.IParent;

/**
 * Created by User on 21.6.2017 г..
 */

public class NParent implements IParent {

    @SerializedName("title")
    private String title;
    @SerializedName("location_type")
    private String locationType;
    @SerializedName("woeid")
    private Integer woeid;
    @SerializedName("latt_long")
    private String lattLong;
}
