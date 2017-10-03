package com.petar.weather.ui.fragments;


import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableInt;
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
import com.petar.weather.databinding.FragmentHourlyForecastBinding;
import com.petar.weather.listeners.IForecastActivityForHourlyForecastFragmentListener;
import com.petar.weather.listeners.IForecastFragmentListener;
import com.petar.weather.logic.models.AForecast;
import com.petar.weather.presenters.HourlyForecastFragmentPresenter;
import com.petar.weather.ui.activities.ForecastActivity;
import com.petar.weather.ui.activities.ForecastDetailsActivity;
import com.petar.weather.ui.adapter.BaseRecyclerAdapter;
import com.petar.weather.ui.recycler.AListenerRecyclerItem;
import com.petar.weather.ui.recycler.LoadingRecyclerItem;
import com.petar.weather.ui.views.IErrorView;
import com.petar.weather.ui.views.IHourlyForecastFragment;
import com.petar.weather.app.Constants;
import com.petar.weather.util.ErrorHandlingUtil;
import com.petar.weather.util.TimeUtil;

import java.util.List;

/**
 * A {@link Fragment} used to display the hourly forecast data.
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 8.7.2017
 */
public class HourlyForecastFragment
        extends MvpLceViewStateFragment<SwipeRefreshLayout, List<? extends AListenerRecyclerItem>, IHourlyForecastFragment, HourlyForecastFragmentPresenter>
        implements IHourlyForecastFragment, IForecastActivityForHourlyForecastFragmentListener, AForecast.IForecastListener, SwipeRefreshLayout.OnRefreshListener, IErrorView {

    // Current location
    private Integer mIdWOE;

    // CONTENT-VIEW helpers
    private RecyclerView mRecyclerView;
    private BaseRecyclerAdapter mAdapter;
    private LoadingRecyclerItem mLoadingRecyclerItem;

    // FRAGMENT-ACTIVITY COMMUNICATION helpers
    private IForecastFragmentListener mListener;

    // ERROR-VIEW helpers
    private ObservableInt mErrorViewVisibility;

    // --------------------------------------------------------
    // GENERAL FRAGMENT region
    // --------------------------------------------------------

    public HourlyForecastFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        ((ForecastActivity) activity).setHourlyForecastFragmentListener(this);
        mListener = (IForecastFragmentListener) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentHourlyForecastBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hourly_forecast, container, false);
        binding.setView(this);
        mRecyclerView = binding.recycler;

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Must be already initialized when onFragmentCreated() is called!
        mErrorViewVisibility = new ObservableInt();
        mLoadingRecyclerItem = new LoadingRecyclerItem();

        mAdapter = new BaseRecyclerAdapter();
        contentView.setOnRefreshListener(this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    int totalItemCount = layoutManager.getItemCount();
                    // Starts to count from 0
                    int visibleItemCount = layoutManager.findLastVisibleItemPosition() + 1;

                    if (visibleItemCount == totalItemCount && !presenter.isLoading()) {
                        presenter.loadHourlyForecastForNextDay(getContext(), mIdWOE, true);
                    }
                }
            }
        });

        if (mListener != null) {
            mListener.onFragmentCreated();
        }

        if (savedInstanceState != null) {
            mLoadingRecyclerItem = savedInstanceState.getParcelable(Constants.BUNDLE_RECYCLER_LOADING_ITEM_KEY);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(Constants.BUNDLE_RECYCLER_LOADING_ITEM_KEY, mLoadingRecyclerItem);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        ((ForecastActivity) getActivity()).setHourlyForecastFragmentListener(null);
        mListener = null;
    }

    // --------------------------------------------------------
    // End of GENERAL FRAGMENT region
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
    // MVP-LCE-VIEW-STATE-FRAGMENT region
    // --------------------------------------------------------

    @NonNull
    @Override
    public HourlyForecastFragmentPresenter createPresenter() {
        return new HourlyForecastFragmentPresenter();
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        if (mIdWOE != null) {
            presenter.loadHourlyForecastForToday(getContext(), mIdWOE, pullToRefresh);
        }
    }

    @Override
    public void setData(List<? extends AListenerRecyclerItem> data) {
        for (AListenerRecyclerItem current : data) {
            current.setListener(this);
        }

        mAdapter.setData(data);
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        removeLoadingRecyclerItem();

        return ErrorHandlingUtil.generateErrorText(getContext(), e);
    }

    @Override
    public List<? extends AListenerRecyclerItem> getData() {
        return mAdapter != null ? mAdapter.getData() : null;
    }

    @NonNull
    @Override
    public LceViewState<List<? extends AListenerRecyclerItem>, IHourlyForecastFragment> createViewState() {
        return new ParcelableListLceViewState<>();
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        super.showError(e, pullToRefresh);

        mErrorViewVisibility.set(errorView.getVisibility());
    }

    @Override
    public void showContent() {
        super.showContent();

        contentView.setRefreshing(false);
        mErrorViewVisibility.set(errorView.getVisibility());
    }

    @Override
    public void showLoading(boolean pullToRefresh) {
        super.showLoading(pullToRefresh);

        mErrorViewVisibility.set(errorView.getVisibility());
    }

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addNextDayForecast(List<? extends AListenerRecyclerItem> data) {
        for (AListenerRecyclerItem current : data) {
            current.setListener(this);
        }

        mAdapter.addItems(data);
    }

    @Override
    public void scrollToCurrentForecast() {
        List<? extends AListenerRecyclerItem> data = mAdapter.getData();

        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getViewType() == Constants.RecyclerItems.FORECAST_ITEM) {
                int forecastHour = TimeUtil.getHoursTimeForISOString(
                        ((AForecast) data.get(i)).getCreatedDate()
                );

                if (forecastHour > TimeUtil.getCurrentHoursTime()) {
                    mRecyclerView.smoothScrollToPosition(i);
                    break;
                }
            }
        }
    }

    @Override
    public void setLoadingRecyclerItem() {
        mAdapter.addItem(mLoadingRecyclerItem);
    }

    @Override
    public void removeLoadingRecyclerItem() {
        mAdapter.removeItem(mLoadingRecyclerItem);
    }

    // --------------------------------------------------------
    // End of MVP-LCE-VIEW-STATE-FRAGMENT region
    // --------------------------------------------------------

    // --------------------------------------------------------
    // ACTIVITY-FRAGMENT COMMUNICATION region
    // --------------------------------------------------------

    @Override
    public void onLocationFound(@NonNull Integer idWOE) {
        boolean didIdChange = !idWOE.equals(mIdWOE);
        mIdWOE = idWOE;

        if (didIdChange && presenter != null) {
            presenter.loadHourlyForecastForToday(getContext(), idWOE, false);
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

    @Override
    public ObservableInt getErrorViewVisibility() {
        return mErrorViewVisibility;
    }

    // --------------------------------------------------------
    // End of ERROR-VIEW region
    // --------------------------------------------------------
}
