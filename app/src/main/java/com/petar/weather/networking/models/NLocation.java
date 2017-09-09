package com.petar.weather.networking.models;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;
import com.petar.weather.logic.models.ALocation;
import com.petar.weather.app.Constants;

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
    public Integer getIdWOE() {
        return woeid;
    }

    @Override
    public int getViewType() {
        return Constants.RecyclerItems.LOCATION_ITEM;
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
        dest.writeValue(this.distance);
        dest.writeString(this.title);
        dest.writeString(this.locationType);
        dest.writeValue(this.woeid);
        dest.writeString(this.lattLong);
    }

    public NLocation() {
    }

    protected NLocation(Parcel in) {
        this.distance = (Integer) in.readValue(Integer.class.getClassLoader());
        this.title = in.readString();
        this.locationType = in.readString();
        this.woeid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.lattLong = in.readString();
    }

    public static final Creator<NLocation> CREATOR = new Creator<NLocation>() {
        @Override
        public NLocation createFromParcel(Parcel source) {
            return new NLocation(source);
        }

        @Override
        public NLocation[] newArray(int size) {
            return new NLocation[size];
        }
    };
}
