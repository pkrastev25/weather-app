package com.petar.weather.networking.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by User on 21.6.2017 г..
 */

public class NLocationForecast {

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

    public List<NForecast> getConsolidatedWeather() {
        return consolidatedWeather;
    }

    public void setConsolidatedWeather(List<NForecast> consolidatedWeather) {
        this.consolidatedWeather = consolidatedWeather;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSunRise() {
        return sunRise;
    }

    public void setSunRise(String sunRise) {
        this.sunRise = sunRise;
    }

    public String getSunSet() {
        return sunSet;
    }

    public void setSunSet(String sunSet) {
        this.sunSet = sunSet;
    }

    public String getTimezoneName() {
        return timezoneName;
    }

    public void setTimezoneName(String timezoneName) {
        this.timezoneName = timezoneName;
    }

    public NParent getParent() {
        return parent;
    }

    public void setParent(NParent parent) {
        this.parent = parent;
    }

    public List<NSource> getSources() {
        return sources;
    }

    public void setSources(List<NSource> sources) {
        this.sources = sources;
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

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
}
