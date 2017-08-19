package com.petar.weather.ui.fragments;


import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby3.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.MvpLceViewStateFragment;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.data.RetainingLceViewState;
import com.petar.weather.R;
import com.petar.weather.databinding.FragmentHourlyForecastBinding;
import com.petar.weather.logic.models.AForecast;
import com.petar.weather.presenters.HourlyForecastFragmentPresenter;
import com.petar.weather.ui.activities.ForecastActivity;
import com.petar.weather.ui.activities.ForecastDetailsActivity;
import com.petar.weather.ui.adapter.ForecastRecyclerAdapter;
import com.petar.weather.ui.views.IHourlyForecastFragment;
import com.petar.weather.util.Constants;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HourlyForecastFragment extends MvpLceViewStateFragment<RecyclerView, List<? extends AForecast>, IHourlyForecastFragment, HourlyForecastFragmentPresenter>
        implements IHourlyForecastFragment, ForecastActivity.IHourlyForecastFragmentListener, AForecast.IForecastListener {

    private Integer mId;
    private ForecastRecyclerAdapter mAdapter;

    // GENERAL FRAGMENT region
    public HourlyForecastFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        ((ForecastActivity) activity).setHourlyForecastFragmentListener(this);
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
    public void onDestroy() {
        super.onDestroy();

        ((ForecastActivity) getActivity()).setHourlyForecastFragmentListener(null);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && mId != null && mAdapter.isEmpty()) {
            presenter.loadForecast(getContext(), mId);
        }
    }
    // End of GENERAL FRAGMENT region

    // MVP-LCE-VIEW-STATE-FRAGMENT region
    @Override
    public HourlyForecastFragmentPresenter createPresenter() {
        return new HourlyForecastFragmentPresenter();
    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }

    @Override
    public void setData(List<? extends AForecast> data) {
        for (AForecast current : data) {
            current.setListener(this);
        }

        mAdapter.setData(data);
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null;
    }

    @Override
    public List<? extends AForecast> getData() {
        return mAdapter != null ? mAdapter.getData() : null;
    }

    @NonNull
    @Override
    public LceViewState<List<? extends AForecast>, IHourlyForecastFragment> createViewState() {
        return new RetainingLceViewState<>();
    }
    // End of MVP-LCE-VIEW-STATE-FRAGMENT region

    // ACTIVITY-FRAGMENT COMMUNICATION region
    @Override
    public void onLocationFound(Integer id) {
        boolean didIdChange = mId == null || !mId.equals(id);
        mId = id;

        if (getUserVisibleHint() && didIdChange) {
            presenter.loadForecast(getContext(), id);
        }
    }
    // End of ACTIVITY-FRAGMENT COMMUNICATION region

    // FRAGMENT-FORECAST COMMUNICATION region
    @Override
    public void onItemClick(AForecast forecast) {
        Intent intent = new Intent(getActivity(), ForecastDetailsActivity.class);
        intent.putExtra(Constants.FORECAST_DETAILS_KEY, forecast);
        startActivity(intent);
    }
    // End of FRAGMENT-FORECAST COMMUNICATION region
}
