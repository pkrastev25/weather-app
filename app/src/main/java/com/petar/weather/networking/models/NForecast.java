package com.petar.weather.networking.models;

import com.google.gson.annotations.SerializedName;
import com.petar.weather.logic.models.AForecast;

import org.joda.time.DateTime;

/**
 * Created by User on 21.6.2017 г..
 */

public class NForecast extends AForecast {

    @SerializedName("id")
    private Long id;
    @SerializedName("weather_state_name")
    private String weatherStateName;
    @SerializedName("weather_state_abbr")
    private String weatherStateAbbr;
    @SerializedName("wind_direction_compass")
    private String windDirectionCompass;
    @SerializedName("created")
    private String created;
    @SerializedName("applicable_date")
    private String applicableDate;
    @SerializedName("min_temp")
    private Double minTemp;
    @SerializedName("max_temp")
    private Double maxTemp;
    @SerializedName("the_temp")
    private Double theTemp;
    @SerializedName("wind_speed")
    private Double windSpeed;
    @SerializedName("wind_direction")
    private Double windDirection;
    @SerializedName("air_pressure")
    private Double airPressure;
    @SerializedName("humidity")
    private Integer humidity;
    @SerializedName("visibility")
    private Object visibility;
    @SerializedName("predictability")
    private Integer predictability;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedDate() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getApplicableDate() {
        return applicableDate;
    }

    @Override
    public void convertDateStringToTimestamp() {
        mTimestamp = new DateTime(applicableDate).getMillis();
    }

    @Override
    public String getWeatherState() {
        return weatherStateName;
    }
}
