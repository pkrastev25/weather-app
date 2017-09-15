package com.petar.weather.util;

import com.petar.weather.app.Constants;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by User on 18.7.2017 г..
 */
public class TimeUtil {

    public static long getCurrentTime() {
        return new DateTime().getMillis();
    }

    public static int getCurrentHoursTime() {
        return new DateTime().getHourOfDay();
    }

    public static long getCurrentTimeWithOffset(long offset) {
        return new DateTime().plus(offset).getMillis();
    }

    public static long convertDateFromISOString(String date) {
        return new DateTime(date).getMillis();
    }

    public static long getTimeFromAPIRequestFormatString(String date) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(Constants.API_DATE_REQUEST_FORMAT);

        return new DateTime(formatter.parseDateTime(date)).getMillis();
    }

    public static int getHoursTimeForISOString(String date) {
        return new DateTime(date).getHourOfDay();
    }

    public static Calendar convertDateToCalendarFromMillis(long millis) {
        return new DateTime(millis).toCalendar(Locale.getDefault());
    }

    public static long addTimeOffsetToDate(long date, long offset) {
        return new DateTime(date).plus(offset).getMillis();
    }

    public static long subtractTimeOffsetFromDate(long date, long offset) {
        return new DateTime(date).minus(offset).getMillis();
    }

    public static int compareDates(long aDate, long bDate) {
        DateTime dateA = new DateTime(aDate).withTimeAtStartOfDay();
        DateTime dateB = new DateTime(bDate).withTimeAtStartOfDay();

        return dateA.compareTo(dateB);
    }

    public static int compareDates(String aDate, String bDate) {
        return compareDates(new DateTime(aDate).getMillis(), new DateTime(bDate).getMillis());
    }

    public static int compareTodayWithDate(long aDate) {
        DateTime today = new DateTime().withTimeAtStartOfDay();
        DateTime dateA = new DateTime(aDate).withTimeAtStartOfDay();

        return today.compareTo(dateA);
    }

    public static int compareTodayWithDate(String aDate) {
        return compareTodayWithDate(new DateTime(aDate).getMillis());
    }

    public static boolean isDateToday(long aDate) {
        DateTime today = new DateTime().withTimeAtStartOfDay();
        DateTime dateA = new DateTime(aDate).withTimeAtStartOfDay();

        return dateA.isEqual(today);
    }

    public static boolean isDateBeforeDate(long aDate, long bDate) {
        DateTime dateA = new DateTime(aDate).withTimeAtStartOfDay();
        DateTime dateB = new DateTime(bDate).withTimeAtStartOfDay();

        return dateA.isBefore(dateB);
    }

    public static boolean areDatesTheSameDate(long aDate, long bDate) {
        DateTime dateA = new DateTime(aDate).withTimeAtStartOfDay();
        DateTime dateB = new DateTime(bDate).withTimeAtStartOfDay();

        return dateA.isEqual(dateB);
    }
}
