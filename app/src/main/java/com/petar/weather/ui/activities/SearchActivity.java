package com.petar.weather.ui.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
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
        implements ISearchActivity, SearchView.OnQueryTextListener, ALocation.ILocationListener, IErrorView {

    private SearchView mSearchView;
    private String mTypedText;
    private BaseRecyclerAdapter mAdapter;

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
    public void onReload() {
        loadData(false);
    }

    // --------------------------------------------------------
    // End of ERROR-VIEW region
    // --------------------------------------------------------
}
