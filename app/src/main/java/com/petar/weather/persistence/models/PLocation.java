package com.petar.weather.persistence.models;
import android.os.Parcel;

import com.petar.weather.logic.models.ALocation;
import com.petar.weather.networking.models.NLocation;
import com.petar.weather.util.Constants;
import com.petar.weather.util.TimeUtil;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by User on 18.7.2017 г..
 */

@Entity(
        generateConstructors = false
)
public class PLocation extends ALocation {

    @Id
    private long keyDB;
    private long expireTime;
    private int idWOE;
    private int distance;
    private String title;

    @Keep
    public PLocation(NLocation location) {
        keyDB = Constants.DB_CURRENT_LOCATION_KEY;
        expireTime = TimeUtil.getCurrentTimeWithMinuteOffset(Constants.OFFSET_MINUTES_FOR_LOCATION);
        idWOE = location.getId();
        distance = location.getDistance();
        title = location.getTitle();
    }

    public PLocation() {
    }

    @Override
    public Integer getDistance() {
        return distance;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Integer getId() {
        return idWOE;
    }

    public long getKeyDB() {
        return this.keyDB;
    }

    public void setKeyDB(long keyDB) {
        this.keyDB = keyDB;
    }

    public long getExpireTime() {
        return this.expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public int getIdWOE() {
        return this.idWOE;
    }

    public void setIdWOE(int idWOE) {
        this.idWOE = idWOE;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public int getViewType() {
        return Constants.LOCATION_RECYCLER_ITEM;
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
        dest.writeLong(this.keyDB);
        dest.writeLong(this.expireTime);
        dest.writeInt(this.idWOE);
        dest.writeInt(this.distance);
        dest.writeString(this.title);
    }

    protected PLocation(Parcel in) {
        this.keyDB = in.readLong();
        this.expireTime = in.readLong();
        this.idWOE = in.readInt();
        this.distance = in.readInt();
        this.title = in.readString();
    }

    public static final Creator<PLocation> CREATOR = new Creator<PLocation>() {
        @Override
        public PLocation createFromParcel(Parcel source) {
            return new PLocation(source);
        }

        @Override
        public PLocation[] newArray(int size) {
            return new PLocation[size];
        }
    };
}
