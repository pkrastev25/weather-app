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
import android.widget.Toast;

import com.hannesdorfmann.mosby3.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.MvpLceViewStateFragment;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.data.ParcelableListLceViewState;
import com.petar.weather.R;
import com.petar.weather.databinding.FragmentDailyForecastBinding;
import com.petar.weather.listeners.IForecastActivityForDailyForecastFragmentListener;
import com.petar.weather.listeners.IForecastFragmentListener;
import com.petar.weather.logic.models.AForecast;
import com.petar.weather.presenters.DailyForecastFragmentPresenter;
import com.petar.weather.ui.activities.ForecastActivity;
import com.petar.weather.ui.activities.ForecastDetailsActivity;
import com.petar.weather.ui.adapter.BaseRecyclerAdapter;
import com.petar.weather.ui.recycler.AListenerRecyclerItem;
import com.petar.weather.ui.views.IDailyForecastFragment;
import com.petar.weather.app.Constants;
import com.petar.weather.ui.views.IErrorView;
import com.petar.weather.util.ErrorHandlingUtil;

import java.util.List;

/**
 * A {@link Fragment} used to display the daily forecast data.
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 8.7.2017
 */
public class DailyForecastFragment
        extends MvpLceViewStateFragment<SwipeRefreshLayout, List<? extends AListenerRecyclerItem>, IDailyForecastFragment, DailyForecastFragmentPresenter>
        implements IDailyForecastFragment, IForecastActivityForDailyForecastFragmentListener, AForecast.IForecastListener, SwipeRefreshLayout.OnRefreshListener, IErrorView {

    private Integer mIdWOE;
    private RecyclerView mRecyclerView;
    private BaseRecyclerAdapter mAdapter;

    private IForecastFragmentListener mListener;

    // --------------------------------------------------------
    // GENERAL FRAGMENT region
    // --------------------------------------------------------

    public DailyForecastFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        ((ForecastActivity) activity).setDailyForecastFragmentListener(this);
        mListener = (IForecastFragmentListener) activity;
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

        mAdapter = new BaseRecyclerAdapter();
        contentView.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);

        if (mListener != null) {
            mListener.onFragmentCreated();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        ((ForecastActivity) getActivity()).setDailyForecastFragmentListener(null);
        mListener = null;
    }

    // --------------------------------------------------------
    // End of GENERAL FRAGMENT region
    // --------------------------------------------------------

    // --------------------------------------------------------
    // MVP-LCE-VIEW-STATE-FRAGMENT region
    // --------------------------------------------------------

    @Override
    public DailyForecastFragmentPresenter createPresenter() {
        return new DailyForecastFragmentPresenter();
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        if (mIdWOE != null) {
            presenter.loadLocationDailyForecast(getContext(), mIdWOE, pullToRefresh);
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
        return ErrorHandlingUtil.generateErrorText(getContext(), e);
    }

    @Override
    public List<? extends AListenerRecyclerItem> getData() {
        return mAdapter != null ? mAdapter.getData() : null;
    }

    @NonNull
    @Override
    public LceViewState<List<? extends AListenerRecyclerItem>, IDailyForecastFragment> createViewState() {
        return new ParcelableListLceViewState<>();
    }

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    // --------------------------------------------------------
    // End of MVP-LCE-VIEW-STATE-FRAGMENT region
    // --------------------------------------------------------

    // --------------------------------------------------------
    // SWIPE-TO-REFRESH region
    // --------------------------------------------------------

    @Override
    public void onRefresh() {
        loadData(true);
    }

    // --------------------------------------------------------
    // End of SWIPE-TO-REFRESH region
    // --------------------------------------------------------

    // --------------------------------------------------------
    // ACTIVITY-FRAGMENT COMMUNICATION region
    // --------------------------------------------------------

    @Override
    public void onLocationFound(@NonNull Integer idWOE) {
        boolean didIdChange = !idWOE.equals(mIdWOE);
        mIdWOE = idWOE;

        if (didIdChange && presenter != null) {
            presenter.loadLocationDailyForecast(getContext(), idWOE, false);
        }
    }

    // --------------------------------------------------------
    // End of ACTIVITY-FRAGMENT COMMUNICATION region
    // --------------------------------------------------------

    // --------------------------------------------------------
    // FRAGMENT-FORECAST COMMUNICATION region
    // --------------------------------------------------------

    @Override
    public void onShowForecastDetails(AForecast forecast) {
        Intent intent = new Intent(getActivity(), ForecastDetailsActivity.class);
        intent.putExtra(Constants.BUNDLE_FORECAST_DETAILS_KEY, forecast);
        startActivity(intent);
    }

    // --------------------------------------------------------
    // End of FRAGMENT-FORECAST COMMUNICATION region
    // --------------------------------------------------------

    // --------------------------------------------------------
    // ERROR-VIEW region
    // --------------------------------------------------------

    @Override
    public void onReload() {
        loadData(false);
    }

    // --------------------------------------------------------
    // End of ERROR-VIEW region
    // --------------------------------------------------------
}
