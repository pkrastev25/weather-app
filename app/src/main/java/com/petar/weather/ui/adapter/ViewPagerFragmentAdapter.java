package com.petar.weather.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.petar.weather.ui.fragments.HourlyForecastFragment;
import com.petar.weather.ui.fragments.SettingsFragment;
import com.petar.weather.util.Constants;

/**
 * Created by User on 1.7.2017 г..
 */

public class ViewPagerFragmentAdapter extends FragmentPagerAdapter {

    public ViewPagerFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case (Constants.HOURLY_FORECAST_FRAGMENT_POSITION):
                return new HourlyForecastFragment();
            case (Constants.DAILY_FORECAST_FRAGMENT_POSITION):
                return new SettingsFragment();
            default:
                return new SettingsFragment();
        }
    }

    @Override
    public int getCount() {
        return Constants.VIEW_PAGER_FRAGMENT_COUNT;
    }
}
