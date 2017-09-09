package com.petar.weather.util;

import android.content.Context;

import com.petar.weather.R;
import com.petar.weather.app.Constants;

/**
 * Created by User on 6.9.2017 г..
 */

public class ErrorHandlingUtil {

    public static String generateErrorText(Context context, String cause) {
        switch (cause) {
            case Constants.ErrorHandling.NO_INTERNET_CONNECTION:
                return context.getString(R.string.error_no_internet_connection);
            case Constants.ErrorHandling.NO_RESULTS_FOR_REQUEST:
                return context.getString(R.string.error_no_results_for_request);
            case Constants.ErrorHandling.CANNOT_UPDATE_CACHED_DATA:
                return context.getString(R.string.error_cannot_update_cached_data);
            case Constants.ErrorHandling.DEFAULT:
            default:
                return context.getString(R.string.error_default);
        }
    }

    public static String generateErrorText(Context context, Throwable cause) {
        return generateErrorText(context, cause.getMessage());
    }
}
