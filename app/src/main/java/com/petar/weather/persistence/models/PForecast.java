package com.petar.weather.persistence.models;

import android.support.annotation.NonNull;

import com.petar.weather.util.Constants;
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
    private long expireTime;
    private long timestamp;
    private String forecasts;

    @Keep
    public PForecast(long keyDB, String date, String forecasts) {
        this.keyDB = keyDB;
        this.expireTime = TimeUtil.getCurrentTimeWithHourOffset(Constants.OFFSET_HOURS_FOR_FORECAST);
        this.timestamp = TimeUtil.getTimeFromString(date);
        this.forecasts = forecasts;
    }

    @Keep
    public PForecast(long keyDB, long timestamp, String forecasts) {
        this.keyDB = keyDB;
        this.expireTime = TimeUtil.getCurrentTimeWithDayOffset(Constants.OFFSET_DAYS_FOR_WEEKLY_FORECAST);
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
}