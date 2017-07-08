package com.petar.weather.ui.fragments;


import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby3.mvp.lce.MvpLceFragment;
import com.petar.weather.R;
import com.petar.weather.databinding.FragmentHourlyForecastBinding;
import com.petar.weather.logic.models.AForecast;
import com.petar.weather.presenters.HourlyForecastFragmentPresenter;
import com.petar.weather.ui.activities.ForecastActivity;
import com.petar.weather.ui.adapter.ForecastRecyclerAdapter;
import com.petar.weather.ui.views.IHourlyForecastFragment;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HourlyForecastFragment extends MvpLceFragment<RecyclerView, List<? extends AForecast>, IHourlyForecastFragment, HourlyForecastFragmentPresenter>
        implements ForecastActivity.ForecastActivityListener, IHourlyForecastFragment {

    private Integer mId;
    private ForecastRecyclerAdapter mAdapter;

    public HourlyForecastFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        ((ForecastActivity) activity).setListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        ((ForecastActivity) getActivity()).setListener(null);
    }

    @Override
    public HourlyForecastFragmentPresenter createPresenter() {
        return new HourlyForecastFragmentPresenter();
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentHourlyForecastBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hourly_forecast, container, false);
        binding.setView(this);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter = new ForecastRecyclerAdapter();
        contentView.setLayoutManager(new LinearLayoutManager(getContext()));
        contentView.setAdapter(mAdapter);
    }

    @Override
    public void setData(List<? extends AForecast> data) {
        mAdapter.setData(data);
    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && mId != null) {
            presenter.loadForecast(mId);
        }
    }

    @Override
    public void onLocationFound(Integer id) {
        mId = id;
        presenter.loadForecast(mId);
    }
}