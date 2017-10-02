package com.petar.weather.util;

import android.content.Context;

import com.petar.weather.R;
import com.petar.weather.app.Constants;

/**
 * Helper class used to handle manipulations within {@link com.petar.weather.ui.adapter.ViewPagerFragmentAdapter};
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 2.10.2017
 */
public class ViewPagerFragmentAdapterHelper {

    /**
     * Generates the fragment's title based on its position within
     * {@link com.petar.weather.ui.adapter.ViewPagerFragmentAdapter}.
     *
     * @param context {@link Context} reference
     * @param position The position of the fragment, must be one of type {@link com.petar.weather.app.Constants.ViewPagerFragmentPositions}
     * @return The title of the fragment
     */
    public static String generateFragmentTitle(Context context, int position) {
        switch (position) {
            case Constants.ViewPagerFragmentPositions.HOURLY_FORECAST:
                return context.getString(R.string.fragment_hourly_forecast_title);
            case Constants.ViewPagerFragmentPositions.DAILY_FORECAST:
                return context.getString(R.string.fragment_daily_forecast_title);
            case Constants.ViewPagerFragmentPositions.SETTINGS:
                return context.getString(R.string.fragment_settings_title);
            default:
                return "";
        }
    }
}
