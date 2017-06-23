package com.petar.weather.networking.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class NLocation implements Comparable<NLocation> {

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

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public Integer getWoeid() {
        return woeid;
    }

    public void setWoeid(Integer woeid) {
        this.woeid = woeid;
    }

    public String getLattLong() {
        return lattLong;
    }

    public void setLattLong(String lattLong) {
        this.lattLong = lattLong;
    }

    @Override
    public int compareTo(@NonNull NLocation o) {
        if (distance > o.distance) {
            return 1;
        } else if (distance < o.distance) {
            return -1;
        } else {
            return 0;
        }
    }
}
