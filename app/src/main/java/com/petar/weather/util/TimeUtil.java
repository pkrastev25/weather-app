package com.petar.weather.util;

import com.petar.weather.app.Constants;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Calendar;
import java.util.Locale;

/**
 * Handles all time conversions within this application, relies heavily on {@link net.danlew.android.joda.JodaTimeAndroid}.
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 18.7.2017
 */
public class TimeUtil {

    /**
     * Takes into account the exact current time.
     *
     * @return Current time in millis
     */
    public static long getCurrentTime() {
        return new DateTime().getMillis();
    }

    /**
     * Gets the hours field of the current time.
     *
     * @return Current time's hours
     */
    public static int getCurrentHoursTime() {
        return new DateTime().getHourOfDay();
    }

    /**
     * Adds the time offset to the current time.
     *
     * @param offset Time offset to be added, in millis
     * @return Current time plus offset
     */
    public static long getCurrentTimeWithOffset(long offset) {
        return new DateTime().plus(offset).getMillis();
    }

    /**
     * Converts the ISO date string into time.
     *
     * @param date ISO date string to be converted
     * @return ISO date in millis
     */
    public static long convertDateFromISOString(String date) {
        return new DateTime(date).getMillis();
    }

    /**
     * Converts the date string into time.
     *
     * @param date Date string to be converted, must be in the format of {@link Constants#API_DATE_REQUEST_FORMAT}
     * @return Date into millis
     */
    public static long getTimeFromAPIRequestFormatString(String date) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(Constants.API_DATE_REQUEST_FORMAT);

        return new DateTime(formatter.parseDateTime(date)).getMillis();
    }

    /**
     * Converts the ISO date string into time and extracts the hours field from it.
     *
     * @param date ISO date string to be converted
     * @return ISO date's hours
     */
    public static int getHoursTimeForISOString(String date) {
        return new DateTime(date).getHourOfDay();
    }

    /**
     * Converts the given date from millis to a {@link Calendar} instance.
     *
     * @param millis The time to be converted, in millis
     * @return The resulting {@link Calendar} instance
     */
    public static Calendar convertDateToCalendarFromMillis(long millis) {
        return new DateTime(millis).toCalendar(Locale.getDefault());
    }

    /**
     * Adds a time offset to a specified date.
     *
     * @param date   The date to which the offset will be added, in millis
     * @param offset Time offset to be added, in millis
     * @return Date plus offset
     */
    public static long addTimeOffsetToDate(long date, long offset) {
        return new DateTime(date).plus(offset).getMillis();
    }

    /**
     * Subtracts a time offset from a specified date.
     *
     * @param date   The date to which the offset will be subtracted, in millis
     * @param offset Time offset to be subtracted, in millis
     * @return Date minus offset
     */
    public static long subtractTimeOffsetFromDate(long date, long offset) {
        return new DateTime(date).minus(offset).getMillis();
    }

    /**
     * Compares the given dates to each other. Note, for this comparison,
     * the calendar dates are only taken into account!
     *
     * @param aDate First date to be compared to, in millis
     * @param bDate Second date to be compared to, in millis
     * @return 1, 0, -1 for bigger, equal, smaller respectively
     * @see DateTime#compareTo(Object)
     */
    public static int compareDates(long aDate, long bDate) {
        DateTime dateA = new DateTime(aDate).withTimeAtStartOfDay();
        DateTime dateB = new DateTime(bDate).withTimeAtStartOfDay();

        return dateA.compareTo(dateB);
    }

    /**
     * Compares the given dates to each other. Note, for this comparison,
     * the calendar dates are only taken into account!
     *
     * @param aDate First date to be compared to
     * @param bDate Second date to be compared to
     * @return 1, 0, -1 for bigger, equal, smaller respectively
     * @see DateTime#compareTo(Object)
     */
    public static int compareDates(String aDate, String bDate) {
        return compareDates(new DateTime(aDate).getMillis(), new DateTime(bDate).getMillis());
    }

    /**
     * Compares the given date to today. Note, for this comparison,
     * the calendar dates are only taken into account!
     *
     * @param aDate Date to be compared with, in millis
     * @return 1, 0, -1 for bigger, equal, smaller respectively
     * @see DateTime#compareTo(Object)
     */
    public static int compareTodayWithDate(long aDate) {
        DateTime today = new DateTime().withTimeAtStartOfDay();
        DateTime dateA = new DateTime(aDate).withTimeAtStartOfDay();

        return today.compareTo(dateA);
    }

    /**
     * Compares the given date to today. Note, for this comparison,
     * the calendar dates are only taken into account!
     *
     * @param aDate Date to be compared with
     * @return 1, 0, -1 for bigger, equal, smaller respectively
     * @see DateTime#compareTo(Object)
     */
    public static int compareTodayWithDate(String aDate) {
        return compareTodayWithDate(new DateTime(aDate).getMillis());
    }

    /**
     * Makes a check if the given date is today. Note, for this comparison,
     * the calendar dates are only taken into account!
     *
     * @param aDate Date to be compared with, in millis
     * @return True, if the given date is today, false otherwise
     */
    public static boolean isDateToday(long aDate) {
        DateTime today = new DateTime().withTimeAtStartOfDay();
        DateTime dateA = new DateTime(aDate).withTimeAtStartOfDay();

        return dateA.isEqual(today);
    }

    /**
     * Makes a check if the 1st date is before the 2nd. Note,
     * for this comparison, the calendar dates are only taken into account!
     *
     * @param aDate First date to be compared with, in millis
     * @param bDate Second date to be compared with, in millis
     * @return True if the 1st date is before the 2nd, false otherwise
     * @see DateTime#isBefore(long)
     */
    public static boolean isDateBeforeDate(long aDate, long bDate) {
        DateTime dateA = new DateTime(aDate).withTimeAtStartOfDay();
        DateTime dateB = new DateTime(bDate).withTimeAtStartOfDay();

        return dateA.isBefore(dateB);
    }

    /**
     * Makes a check if both dates are the same. Note, for this comparison,
     * the calendar dates are only taken into account!
     *
     * @param aDate First date to be compared with, in millis
     * @param bDate Second date to be compared with, in millis
     * @return True if the two dates are the same, false otherwise
     */
    public static boolean areDatesTheSameDate(long aDate, long bDate) {
        DateTime dateA = new DateTime(aDate).withTimeAtStartOfDay();
        DateTime dateB = new DateTime(bDate).withTimeAtStartOfDay();

        return dateA.isEqual(dateB);
    }
}
