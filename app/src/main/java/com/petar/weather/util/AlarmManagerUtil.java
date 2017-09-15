package com.petar.weather.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.petar.weather.app.Constants;
import com.petar.weather.receivers.AlarmReceiver;

/**
 * Created by User on 14.9.2017 г..
 */

public class AlarmManagerUtil {

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
