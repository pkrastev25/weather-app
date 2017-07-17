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
import com.petar.weather.databinding.FragmentDailyForecastBinding;
import com.petar.weather.logic.models.AForecast;
import com.petar.weather.logic.models.ILocationForecast;
import com.petar.weather.presenters.DailyForecastFragmentPresenter;
import com.petar.weather.ui.activities.ForecastActivity;
import com.petar.weather.ui.activities.ForecastDetailsActivity;
import com.petar.weather.ui.adapter.ForecastRecyclerAdapter;
import com.petar.weather.ui.views.IDailyForecastFragment;
import com.petar.weather.util.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class DailyForecastFragment extends MvpLceViewStateFragment<RecyclerView, ILocationForecast, IDailyForecastFragment, DailyForecastFragmentPresenter>
        implements IDailyForecastFragment, ForecastActivity.IDailyForecastFragmentListener, AForecast.IForecastListener {

    private Integer mId;
    private ILocationForecast mLocationForecast;
    private ForecastRecyclerAdapter mAdapter;

    // GENERAL FRAGMENT region
    public DailyForecastFragment() {
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

        ((ForecastActivity) activity).setDailyForecastFragmentListener(this);
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
    public void onDestroy() {
        super.onDestroy();

        ((ForecastActivity) getActivity()).setDailyForecastFragmentListener(null);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && mId != null && mAdapter.isEmpty()) {
            presenter.loadLocationForecast(mId);
        }
    }
    // End of GENERAL FRAGMENT region

    // MVP-LCE-VIEW-STATE-FRAGMENT region
    @Override
    public DailyForecastFragmentPresenter createPresenter() {
        return new DailyForecastFragmentPresenter();
    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }

    @Override
    public void setData(ILocationForecast data) {
        mLocationForecast = data;

        for (AForecast current: data.getForecast()) {
            current.setListener(this);
        }

        mAdapter.setData(data.getForecast());
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null;
    }

    @Override
    public void showContent() {
        super.showContent();

        mLocationForecast = getData();
        mAdapter.setData(getData().getForecast());
    }

    @Override
    public ILocationForecast getData() {
        return mLocationForecast;
    }

    @NonNull
    @Override
    public LceViewState<ILocationForecast, IDailyForecastFragment> createViewState() {
        return new RetainingLceViewState<>();
    }
    // End of MVP-LCE-VIEW-STATE-FRAGMENT region

    // ACTIVITY-FRAGMENT COMMUNICATION region
    @Override
    public void onLocationFound(Integer id) {
        boolean didIdChange = mId == null || !mId.equals(id);
        mId = id;

        if (getUserVisibleHint() && didIdChange) {
            presenter.loadLocationForecast(id);
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
