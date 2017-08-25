package com.petar.weather.networking.models;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;
import com.petar.weather.logic.models.AForecast;
import com.petar.weather.util.Constants;

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
    public String getImageType() {
        return weatherStateAbbr;
    }

    @Override
    public Double getMaxTemp() {
        return maxTemp;
    }

    @Override
    public Double getMinTemp() {
        return minTemp;
    }

    @Override
    public String getWeatherState() {
        return weatherStateName;
    }


    /**
     * Based on https://stackoverflow.com/questions/4076946/parcelable-where-when-is-describecontents-used
     *
     * @return
     */
    @Override
    public int describeContents() {
        return CONTENTS_FILE_DESCRIPTOR;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.weatherStateName);
        dest.writeString(this.weatherStateAbbr);
        dest.writeString(this.created);
        dest.writeString(this.applicableDate);
        dest.writeValue(this.minTemp);
        dest.writeValue(this.maxTemp);
    }

    protected NForecast(Parcel in) {
        this.weatherStateName = in.readString();
        this.weatherStateAbbr = in.readString();
        this.created = in.readString();
        this.applicableDate = in.readString();
        this.minTemp = (Double) in.readValue(Double.class.getClassLoader());
        this.maxTemp = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Creator<NForecast> CREATOR = new Creator<NForecast>() {
        @Override
        public NForecast createFromParcel(Parcel source) {
            return new NForecast(source);
        }

        @Override
        public NForecast[] newArray(int size) {
            return new NForecast[size];
        }
    };

    @Override
    public int getViewType() {
        return Constants.FORECAST_RECYCLER_ITEM;
    }
}
