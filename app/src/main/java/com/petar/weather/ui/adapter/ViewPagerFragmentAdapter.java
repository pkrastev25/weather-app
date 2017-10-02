package com.petar.weather.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.petar.weather.app.Constants;
import com.petar.weather.ui.fragments.DailyForecastFragment;
import com.petar.weather.ui.fragments.HourlyForecastFragment;
import com.petar.weather.ui.fragments.SettingsFragment;
import com.petar.weather.util.ViewPagerFragmentAdapterHelper;

/**
 * Base {@link FragmentPagerAdapter} for the {@link com.petar.weather.app.Constants.ViewPagerFragmentPositions}.
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 1.7.2017
 */
public class ViewPagerFragmentAdapter extends FragmentPagerAdapter {

    private static String[] FRAGMENT_TITLES;

    /**
     * Initializes the {@link ViewPagerFragmentAdapter}. It also keeps a reference
     * of the fragment titles generated from the string resource.
     *
     * @param context {@link Context} reference
     * @param fm {@link FragmentManager} used by the adapter
     */
    public ViewPagerFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);

        FRAGMENT_TITLES = new String[Constants.VIEW_PAGER_FRAGMENT_COUNT];

        for (int position = 0; position < FRAGMENT_TITLES.length; position++) {
            FRAGMENT_TITLES[position] = ViewPagerFragmentAdapterHelper.generateFragmentTitle(context, position);
        }
    }

    @Override
    public Fragment getItem(@Constants.ViewPagerFragmentPositions int position) {
        switch (position) {
            case Constants.ViewPagerFragmentPositions.HOURLY_FORECAST:
                return new HourlyForecastFragment();
            case Constants.ViewPagerFragmentPositions.DAILY_FORECAST:
                return new DailyForecastFragment();
            case Constants.ViewPagerFragmentPositions.SETTINGS:
                return new SettingsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return Constants.VIEW_PAGER_FRAGMENT_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return FRAGMENT_TITLES[position];
    }
}
