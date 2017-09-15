package com.petar.weather.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.petar.weather.services.NotificationIntentService;

/**
 * Created by User on 14.9.2017 г..
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent notificationIntent = new Intent(context, NotificationIntentService.class);
        context.startService(notificationIntent);
    }
}
