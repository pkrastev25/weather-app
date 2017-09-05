package com.petar.weather.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.petar.weather.ui.fragments.DailyForecastFragment;
import com.petar.weather.ui.fragments.HourlyForecastFragment;
import com.petar.weather.ui.fragments.SettingsFragment;
import com.petar.weather.app.Constants;

/**
 * Created by User on 1.7.2017 г..
 */

public class ViewPagerFragmentAdapter extends FragmentPagerAdapter {

    public ViewPagerFragmentAdapter(FragmentManager fm) {
        super(fm);
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
}
