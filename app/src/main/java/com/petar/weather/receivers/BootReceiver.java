package com.petar.weather.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.petar.weather.app.Constants;
import com.petar.weather.util.AlarmManagerUtil;

/**
 * A {@link BroadcastReceiver}, invoked when the phone is booted.
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 14.9.2017
 */
public class BootReceiver extends BroadcastReceiver {

    /**
     * Performs a check for the user preferences regarding notifications.
     * If they are enabled, the {@link android.app.AlarmManager} is started.
     *
     * @param context {@link Context} reference
     * @param intent  Ignored
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences preferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        boolean areNotificationsEnabled = preferences.getBoolean(Constants.SHARED_PREFERENCES_NOTIFICATIONS_ACTIVE_KEY, false);

        if (areNotificationsEnabled) {
            AlarmManagerUtil.startAlarm(context);
        }
    }
}
