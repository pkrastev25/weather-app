package com.petar.weather.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.petar.weather.R;

public class ForecastPagerAdapter extends PagerAdapter {

    private final Context mContext;

    public ForecastPagerAdapter(final Context context) {
        mContext = context.getApplicationContext();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        final ViewGroup view = (ViewGroup) LayoutInflater.from(container.getContext())
                .inflate(getForecastPagerForPosition(position).layoutId, container, false);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return ForecastPagerEnum.values().length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getString(getForecastPagerForPosition(position).titleResId);
    }

    private ForecastPagerEnum getForecastPagerForPosition(final int position) {
        return ForecastPagerEnum.values()[position];
    }

    private enum ForecastPagerEnum {

        HOURLY_FORECAST_PAGER(R.string.fragment_hourly_forecast_title, R.layout.view_hourly_forecast),
        DAILY_FORECAST_PAGER(R.string.fragment_daily_forecast_title, R.layout.view_daily_forecast);

        ForecastPagerEnum(final int titleResId, final int layoutId) {
            this.titleResId = titleResId;
            this.layoutId = layoutId;
        }

        public final int titleResId;
        public final int layoutId;
    }
}
