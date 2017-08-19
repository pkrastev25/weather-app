package com.petar.weather.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by User on 18.7.2017 г..
 */
public class TimeUtil {

    public static Long getCurrentTimeWithMinuteOffset(int offset) {
        DateTime updateTime = new DateTime();
        updateTime = updateTime.plusMinutes(offset);

        return updateTime.getMillis();
    }

    public static Long getCurrentTimeWithHourOffset(int offset) {
        DateTime updateTime = new DateTime();
        updateTime = updateTime.plusHours(offset);

        return updateTime.getMillis();
    }

    public static Long getCurrentTimeWithDayOffset(int offset) {
        DateTime updateTime = new DateTime();
        updateTime = updateTime.plusDays(offset);

        return updateTime.getMillis();
    }

    public static Long getCurrentTime() {
        DateTime today = new DateTime();

        return today.getMillis();
    }

    public static Long getTimeFromString(String date) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(Constants.API_DATE_REQUEST_FORMAT);
        DateTime time = new DateTime(formatter.parseDateTime(date));

        return time.getMillis();
    }
}
