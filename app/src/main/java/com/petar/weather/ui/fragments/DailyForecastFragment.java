package com.petar.weather.ui.fragments;


import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.petar.weather.presenters.DailyForecastFragmentPresenter;
import com.petar.weather.ui.activities.ForecastActivity;
import com.petar.weather.ui.activities.ForecastDetailsActivity;
import com.petar.weather.ui.adapter.ForecastRecyclerAdapter;
import com.petar.weather.ui.recycler.AListenerRecyclerItem;
import com.petar.weather.ui.views.IDailyForecastFragment;
import com.petar.weather.util.Constants;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DailyForecastFragment extends MvpLceViewStateFragment<SwipeRefreshLayout, List<? extends AListenerRecyclerItem>, IDailyForecastFragment, DailyForecastFragmentPresenter>
        implements IDailyForecastFragment, ForecastActivity.IDailyForecastFragmentListener, AForecast.IForecastListener, SwipeRefreshLayout.OnRefreshListener {

    private Integer mId;
    private RecyclerView mRecyclerView;
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
        mRecyclerView = binding.recycler;

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter = new ForecastRecyclerAdapter();
        contentView.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
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
            presenter.loadLocationForecast(getContext(), mId, false);
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
        if (mId != null) {
            presenter.loadLocationForecast(getContext(), mId, pullToRefresh);
        }
    }

    @Override
    public void setData(List<? extends AListenerRecyclerItem> data) {
        mAdapter.setData(data);
    }

    @Override
    public void showContent() {
        super.showContent();

        contentView.setRefreshing(false);
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null;
    }

    @Override
    public List<? extends AListenerRecyclerItem> getData() {
        return mAdapter != null ? mAdapter.getData() : null;
    }

    @NonNull
    @Override
    public LceViewState<List<? extends AListenerRecyclerItem>, IDailyForecastFragment> createViewState() {
        return new RetainingLceViewState<>();
    }

    @Override
    public void onRefresh() {
        loadData(true);
    }
    // End of MVP-LCE-VIEW-STATE-FRAGMENT region

    // ACTIVITY-FRAGMENT COMMUNICATION region
    @Override
    public void onLocationFound(Integer id) {
        boolean didIdChange = mId == null || !mId.equals(id);
        mId = id;

        if (getUserVisibleHint() && didIdChange) {
            presenter.loadLocationForecast(getContext(), id, false);
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
