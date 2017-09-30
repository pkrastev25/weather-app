package com.petar.weather.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.petar.weather.app.Constants;
import com.petar.weather.receivers.AlarmReceiver;

/**
 * Helper class for manipulating the {@link AlarmManager}.
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 14.9.2017
 */
public class AlarmManagerUtil {

    /**
     * Schedules alarms at inexact repeating intervals of {@link Constants#NOTIFICATION_INTERVAL_TIME_MILLIS}.
     * Starts {@link AlarmReceiver} when the interval time passed.
     *
     * @param context {@link Context} reference
     */
    public static void startAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);

        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(
                context,
                Constants.NOTIFICATION_PENDING_INTENT_REQUEST_CODE,
                alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        alarmManager.setInexactRepeating(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + Constants.NOTIFICATION_INTERVAL_TIME_MILLIS,
                Constants.NOTIFICATION_INTERVAL_TIME_MILLIS,
                alarmPendingIntent
        );
    }

    /**
     * Stops the scheduled alarms.
     *
     * @param context {@link Context} reference
     */
    public static void stopAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);

        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(
                context,
                Constants.NOTIFICATION_PENDING_INTENT_REQUEST_CODE,
                alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        alarmManager.cancel(alarmPendingIntent);
    }
}
