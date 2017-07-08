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
import com.petar.weather.databinding.FragmentDailyForecastBinding;
import com.petar.weather.logic.models.ILocationForecast;
import com.petar.weather.presenters.DailyForecastFragmentPresenter;
import com.petar.weather.ui.activities.ForecastActivity;
import com.petar.weather.ui.adapter.ForecastRecyclerAdapter;
import com.petar.weather.ui.views.IDailyForecastFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class DailyForecastFragment extends MvpLceFragment<RecyclerView, ILocationForecast, IDailyForecastFragment, DailyForecastFragmentPresenter>
        implements IDailyForecastFragment, ForecastActivity.IDailyForecastFragmentListener {

    private Integer mId;
    private ForecastRecyclerAdapter mAdapter;

    public DailyForecastFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        ((ForecastActivity) activity).setDailyForecastFragmentListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        ((ForecastActivity) getActivity()).setDailyForecastFragmentListener(null);
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null;
    }

    @Override
    public DailyForecastFragmentPresenter createPresenter() {
        return new DailyForecastFragmentPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentDailyForecastBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_daily_forecast, container, false);
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
    public void setData(ILocationForecast data) {
        mAdapter.setData(data.getForecast());
    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && mId != null && mAdapter.isEmpty()) {
            presenter.loadLocationForecast(mId);
        }
    }


    @Override
    public void onLocationFound(Integer id) {
        mId = id;

        if (getUserVisibleHint()) {
            presenter.loadLocationForecast(id);
        }
    }
}
