package com.petar.weather.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.petar.weather.R;
import com.petar.weather.ui.adapter.ForecastPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForecastOverviewFragment extends Fragment {

    public static final String NAV_ID = "FORECAST_FRAGMENT_NAV_ID";

    @BindView(R.id.data_view)
    View dataView;
    @BindView(R.id.forecast_view_pager)
    ViewPager forecastViewPager;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        final View view = inflater.inflate(R.layout.fragment_forecast_overview, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        forecastViewPager.setAdapter(new ForecastPagerAdapter(view.getContext()));
    }

    public static Fragment newInstance() {
        return new ForecastOverviewFragment();
    }

}
