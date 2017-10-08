package com.petar.weather.util;

import android.content.Context;

import com.petar.weather.R;
import com.petar.weather.app.Constants;

/**
 * Helper used to handle the errors occurred in the application.
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 6.9.2017
 */
public class ErrorHandlingUtil {

    /**
     * Generates appropriate text displayed to the user for the error occurred.
     *
     * @param context {@link Context} reference
     * @param cause   The cause for the error
     * @return Helper text
     */
    public static String generateErrorText(Context context, String cause) {
        switch (cause) {
            case Constants.ErrorHandling.NO_INTERNET_CONNECTION:
                return context.getString(R.string.error_no_internet_connection);
            case Constants.ErrorHandling.NO_RESULTS_FOR_REQUEST:
                return context.getString(R.string.error_no_results_for_request);
            case Constants.ErrorHandling.CANNOT_UPDATE_CACHED_DATA:
                return context.getString(R.string.error_cannot_update_cached_data);
            case Constants.ErrorHandling.NO_SEARCH_INPUT:
                return context.getString(R.string.error_no_search_input);
            case Constants.ErrorHandling.WRONG_SEARCH_INPUT:
                return context.getString(R.string.error_wrong_search_input);
            case Constants.ErrorHandling.DEFAULT:
            default:
                return context.getString(R.string.error_default);
        }
    }

    /**
     * Generates appropriate text displayed to the user for the error occurred.
     *
     * @param context {@link Context} reference
     * @param cause   The cause for the error, instance of {@link Throwable}
     * @return Helper text
     */
    public static String generateErrorText(Context context, Throwable cause) {
        return generateErrorText(context, cause.getMessage());
    }
}
