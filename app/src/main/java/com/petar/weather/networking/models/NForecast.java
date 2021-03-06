package com.petar.weather.networking.models;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;
import com.petar.weather.logic.models.AForecast;
import com.petar.weather.app.Constants;

/**
 * Networking model for a forecast that is mapped to the API response.
 *
 * @author Petar Krastev
 * @version 1.0
 * @see <a href="http://www.jsonschema2pojo.org/">http://www.jsonschema2pojo.org/</a>
 * @since 21.6.2017
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
    private double minTemp;
    @SerializedName("max_temp")
    private double maxTemp;
    @SerializedName("wind_speed")
    private double windSpeed;
    @SerializedName("wind_direction")
    private double windDirection;
    @SerializedName("air_pressure")
    private double airPressure;
    @SerializedName("humidity")
    private int humidity;

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
    public double getWindSpeed() {
        return windSpeed;
    }

    @Override
    public double getWindDirection() {
        return windDirection;
    }

    @Override
    public String getWindDirectionCompass() {
        return windDirectionCompass;
    }

    @Override
    public double getMaxTemp() {
        return maxTemp;
    }

    @Override
    public double getMinTemp() {
        return minTemp;
    }

    @Override
    public double getAirPressure() {
        return airPressure;
    }

    @Override
    public double getHumidity() {
        return humidity;
    }

    @Override
    public String getWeatherState() {
        return weatherStateName;
    }

    @Override
    public String getWeatherStateSummary() {
        return weatherStateAbbr;
    }


    @Override
    public int getViewType() {
        return Constants.RecyclerItems.FORECAST_ITEM;
    }

    // --------------------------------------------------------
    // PARCELABLE region
    // --------------------------------------------------------

    public NForecast() {
    }

    /**
     * @return #CONTENTS_FILE_DESCRIPTOR
     * @see <a href="https://stackoverflow.com/questions/4076946/parcelable-where-when-is-describecontents-used">https://stackoverflow.com/questions/4076946/parcelable-where-when-is-describecontents-used</a>
     */
    @Override
    public int describeContents() {
        return CONTENTS_FILE_DESCRIPTOR;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.weatherStateName);
        dest.writeString(this.weatherStateAbbr);
        dest.writeString(this.windDirectionCompass);
        dest.writeString(this.created);
        dest.writeString(this.applicableDate);
        dest.writeDouble(this.minTemp);
        dest.writeDouble(this.maxTemp);
        dest.writeDouble(this.windSpeed);
        dest.writeDouble(this.windDirection);
        dest.writeDouble(this.airPressure);
        dest.writeInt(this.humidity);
    }

    protected NForecast(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.weatherStateName = in.readString();
        this.weatherStateAbbr = in.readString();
        this.windDirectionCompass = in.readString();
        this.created = in.readString();
        this.applicableDate = in.readString();
        this.minTemp = in.readDouble();
        this.maxTemp = in.readDouble();
        this.windSpeed = in.readDouble();
        this.windDirection = in.readDouble();
        this.airPressure = in.readDouble();
        this.humidity = in.readInt();
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
}
