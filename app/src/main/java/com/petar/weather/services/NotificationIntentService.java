package com.petar.weather.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.petar.weather.R;
import com.petar.weather.app.Constants;
import com.petar.weather.logic.DataLogic;
import com.petar.weather.logic.models.AForecast;
import com.petar.weather.logic.models.ALocation;
import com.petar.weather.persistence.PersistenceLogic;
import com.petar.weather.ui.activities.ForecastActivity;
import com.petar.weather.util.ForecastUtil;
import com.petar.weather.util.FormatUtil;
import com.petar.weather.util.TimeUtil;

import java.io.IOException;
import java.util.List;

/**
 * Created by User on 14.9.2017 г..
 */

public class NotificationIntentService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public NotificationIntentService(String name) {
        super(name);
    }

    public NotificationIntentService() {
        super(NotificationIntentService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        ALocation cachedLocation = PersistenceLogic.getInstance(this).getLocation();
        List<? extends AForecast> forecasts;
        AForecast currentForecast = null;

        if (cachedLocation != null) {
            String requestDateFormat = FormatUtil.formatDateRequest(
                    java.util.Calendar.getInstance()
            );
            long forecastKeyDB = requestDateFormat.hashCode();

            try {
                forecasts = DataLogic.getInstance().getLocationForecastForDate(this, cachedLocation.getIdWOE(), requestDateFormat, true);
            } catch (IOException e) {
                forecasts = PersistenceLogic.getInstance(this).getForecast(forecastKeyDB, cachedLocation.getIdWOE());
            }

            if (forecasts != null && !forecasts.isEmpty()) {
                for (AForecast forecast : forecasts) {
                    int forecastHour = TimeUtil.getHoursTimeForISOString(
                            forecast.getCreatedDate()
                    );

                    if (forecastHour > TimeUtil.getCurrentHoursTime()) {
                        currentForecast = forecast;
                        break;
                    }
                }

                if (currentForecast != null) {
                    buildForecastNotification(currentForecast);
                    return;
                }

            }
        }

        buildErrorNotification();
    }

    private void buildForecastNotification(AForecast forecast) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);

        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        notificationBuilder.setContentTitle(forecast.getWeatherState());
        notificationBuilder.setContentText(ForecastUtil.generateTextForWeatherStateSummary(this, forecast.getWeatherStateSummary()));
        notificationBuilder.setAutoCancel(true);

        Intent resultIntent = new Intent(this, ForecastActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                this,
                Constants.NOTIFICATION_PENDING_INTENT_REQUEST_CODE,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        notificationBuilder.setContentIntent(resultPendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(Constants.NOTIFICATION_NOTIFY_ID, notificationBuilder.build());
    }

    private void buildErrorNotification() {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);

        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        notificationBuilder.setContentTitle(getString(R.string.notifications_error_title));
        notificationBuilder.setContentText(getString(R.string.notifications_error_text));
        notificationBuilder.setAutoCancel(true);

        Intent resultIntent = new Intent(this, ForecastActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                this,
                Constants.NOTIFICATION_PENDING_INTENT_REQUEST_CODE,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        notificationBuilder.setContentIntent(resultPendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(Constants.NOTIFICATION_NOTIFY_ID, notificationBuilder.build());
    }
}
