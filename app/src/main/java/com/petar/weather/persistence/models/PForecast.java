package com.petar.weather.persistence.models;

import android.support.annotation.NonNull;

import com.petar.weather.app.Constants;
import com.petar.weather.util.TimeUtil;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by User on 19.7.2017 г..
 */

@Entity(
        generateConstructors = false
)
public class PForecast implements Comparable<PForecast> {

    @Id
    private long keyDB;
    private long idWOE;
    private long expireTime;
    private long timestamp;
    private String forecasts;

    @Keep
    public PForecast(long keyDB, long idWOE, String date, String forecasts) {
        this.keyDB = keyDB;
        this.idWOE = idWOE;
        this.expireTime = TimeUtil.getCurrentTimeWithOffset(Constants.FORECAST_HOURLY_TIME_OFFSET_FOR_CACHED_DATA_MILLIS);
        this.timestamp = TimeUtil.getTimeFromAPIRequestFormatString(date);
        this.forecasts = forecasts;
    }

    @Keep
    public PForecast(long keyDB, long idWOE, long timestamp, String forecasts) {
        this.keyDB = keyDB;
        this.idWOE = idWOE;
        this.expireTime = TimeUtil.getCurrentTimeWithOffset(Constants.FORECAST_WEEKLY_TIME_OFFSET_FOR_CACHED_DATA_MILLIS);
        this.timestamp = timestamp;
        this.forecasts = forecasts;
    }

    @Override
    public int compareTo(@NonNull PForecast o) {
        if (timestamp > o.timestamp) {
            return -1;
        } else if (timestamp < o.timestamp) {
            return 1;
        } else {
            return 0;
        }
    }

    public PForecast() {
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

    public String getForecasts() {
        return this.forecasts;
    }

    public void setForecasts(String forecasts) {
        this.forecasts = forecasts;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getIdWOE() {
        return this.idWOE;
    }

    public void setIdWOE(long idWOE) {
        this.idWOE = idWOE;
    }
}
