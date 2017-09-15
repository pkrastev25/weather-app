package com.petar.weather.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.petar.weather.app.Constants;
import com.petar.weather.util.AlarmManagerUtil;

/**
 * Created by User on 14.9.2017 г..
 */

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences preferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        boolean areNotificationsEnabled = preferences.getBoolean(Constants.SHARED_PREFERENCES_NOTIFICATIONS_ACTIVE_KEY, false);

        if (areNotificationsEnabled) {
            AlarmManagerUtil.startAlarm(context);
        }
    }
}
