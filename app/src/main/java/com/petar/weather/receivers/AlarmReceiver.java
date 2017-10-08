package com.petar.weather.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.petar.weather.services.NotificationIntentService;

/**
 * A {@link BroadcastReceiver} invoked by the {@link com.petar.weather.util.AlarmManagerUtil}.
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 14.9.2017
 */
public class AlarmReceiver extends BroadcastReceiver {

    /**
     * Starts the {@link NotificationIntentService}.
     *
     * @param context {@link Context} reference
     * @param intent  Ignored
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent notificationIntent = new Intent(context, NotificationIntentService.class);
        context.startService(notificationIntent);
    }
}
