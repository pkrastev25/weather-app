package com.petar.weather.ui.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import com.hannesdorfmann.mosby3.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.MvpLceViewStateActivity;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.data.ParcelableListLceViewState;
import com.petar.weather.R;
import com.petar.weather.databinding.ActivitySearchBinding;
import com.petar.weather.logic.models.ALocation;
import com.petar.weather.presenters.SearchActivityPresenter;
import com.petar.weather.ui.adapter.BaseRecyclerAdapter;
import com.petar.weather.ui.recycler.AListenerRecyclerItem;
import com.petar.weather.ui.views.IErrorView;
import com.petar.weather.ui.views.ILoadingView;
import com.petar.weather.ui.views.ISearchActivity;
import com.petar.weather.app.Constants;
import com.petar.weather.util.ErrorHandlingUtil;

import java.util.List;

/**
 * Provides functionality for searching for any location. The location can be of
 * a type string name or in the form of coordinates. Shows results based on
 * the location query.
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 28.8.2017
 */
public class SearchActivity
        extends MvpLceViewStateActivity<RecyclerView, List<? extends AListenerRecyclerItem>, ISearchActivity, SearchActivityPresenter>
        implements ISearchActivity, SearchView.OnQueryTextListener, ALocation.ILocationListener, IErrorView, ILoadingView {

    // SEARCH helpers
    private SearchView mSearchView;
    private String mTypedText;

    // CONTENT-VIEW helpers
    private BaseRecyclerAdapter mAdapter;

    // ERROR-VIEW helpers
    private ObservableInt mErrorViewVisibility;

    // --------------------------------------------------------
    // GENERAL ACTIVITY region
    // --------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivitySearchBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        binding.setView(this);

        mSearchView = binding.searchView;
        mSearchView.setOnQueryTextListener(this);

        mAdapter = new BaseRecyclerAdapter();
        contentView.setLayoutManager(new LinearLayoutManager(this));
        contentView.setAdapter(mAdapter);

        mErrorViewVisibility = new ObservableInt();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mSearchView.setOnQueryTextListener(null);
        mSearchView = null;
        mAdapter = null;
    }

    // --------------------------------------------------------
    // End of GENERAL ACTIVITY region
    // --------------------------------------------------------

    // --------------------------------------------------------
    // MVP-LCE-VIEW-STATE-ACTIVITY region
    // --------------------------------------------------------

    @NonNull
    @Override
    public SearchActivityPresenter createPresenter() {
        return new SearchActivityPresenter();
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.processQuery(this, mTypedText, pullToRefresh);
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
        return ErrorHandlingUtil.generateErrorText(this, e);
    }

    @Override
    public List<? extends AListenerRecyclerItem> getData() {
        return mAdapter != null ? mAdapter.getData() : null;
    }

    @NonNull
    @Override
    public LceViewState<List<? extends AListenerRecyclerItem>, ISearchActivity> createViewState() {
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

        mErrorViewVisibility.set(errorView.getVisibility());
    }

    @Override
    public void showLoading(boolean pullToRefresh) {
        super.showLoading(pullToRefresh);

        mErrorViewVisibility.set(errorView.getVisibility());
    }

    // --------------------------------------------------------
    // End of MVP-LCE-VIEW-STATE-ACTIVITY region
    // --------------------------------------------------------

    // --------------------------------------------------------
    // SEARCH region
    // --------------------------------------------------------

    @Override
    public boolean onQueryTextSubmit(String query) {
        presenter.processQuery(this, query, false);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mTypedText = newText;
        return true;
    }

    // --------------------------------------------------------
    // End of SEARCH region
    // --------------------------------------------------------

    // --------------------------------------------------------
    // ACTIVITY-LOCATION COMMUNICATION region
    // --------------------------------------------------------

    @Override
    public void onLocationChosen(ALocation location) {
        Intent intent = new Intent(this, ForecastActivity.class);
        // Overwrite the Activity stack
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Constants.BUNDLE_LOCATION_FROM_SEARCH_KEY, location);
        startActivity(intent);
        finish();
    }

    // --------------------------------------------------------
    // End of ACTIVITY-LOCATION COMMUNICATION region
    // --------------------------------------------------------

    // --------------------------------------------------------
    // ERROR-VIEW region
    // --------------------------------------------------------

    @Override
    public void onRetry() {
        loadData(false);
    }

    @Override
    public ObservableInt getErrorViewVisibility() {
        return mErrorViewVisibility;
    }

    // --------------------------------------------------------
    // End of ERROR-VIEW region
    // --------------------------------------------------------

    // --------------------------------------------------------
    // TOOLBAR region
    // --------------------------------------------------------

    /**
     * Called when the user interacts with the "x" icon. Navigates to the
     * {@link ForecastActivity}.
     */
    public void onExit() {
        onBackPressed();
    }

    // --------------------------------------------------------
    // End of TOOLBAR region
    // --------------------------------------------------------

    // --------------------------------------------------------
    // LOADING-VIEW region
    // --------------------------------------------------------

    @Override
    public String getLoadingMessage() {
        return getString(R.string.loading_search);
    }

    // --------------------------------------------------------
    // End of LOADING-VIEW region
    // --------------------------------------------------------
}
