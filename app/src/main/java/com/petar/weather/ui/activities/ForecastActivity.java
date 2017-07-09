package com.petar.weather.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.hannesdorfmann.mosby3.mvp.lce.MvpLceActivity;
import com.petar.weather.R;
import com.petar.weather.databinding.ActivityForecastBinding;
import com.petar.weather.logic.models.ALocation;
import com.petar.weather.presenters.ForecastActivityPresenter;
import com.petar.weather.ui.adapter.LocationRecyclerAdapter;
import com.petar.weather.ui.adapter.ViewPagerFragmentAdapter;
import com.petar.weather.ui.views.IForecastActivity;
import com.petar.weather.ui.views.IToolbarView;
import com.petar.weather.util.Constants;

import java.util.List;

public class ForecastActivity extends MvpLceActivity<RecyclerView, List<? extends ALocation>, IForecastActivity, ForecastActivityPresenter>
        implements IForecastActivity, IToolbarView, LocationListener, ALocation.ILocationListener {

    // TOOLBAR helpers
    private ImageView mToolbarLocationIcon;
    private TextView mToolbarLocationText;
    private SearchView mToolbarSearch;
    private ObservableField<String> currentLocation;

    // LOCATION helpers
    private LocationManager mLocationManager;

    private LocationRecyclerAdapter mLocationAdapter;

    // ACTIVITY-FRAGMENT COMMUNICATION helpers
    private IDailyForecastFragmentListener mDailyForecastFragmentListener;
    private IHourlyForecastFragmentListener mHourlyForecastFragmentListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityForecastBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_forecast);
        binding.setView(this);

        mToolbarLocationIcon = binding.toolbar.currentLocationIcon;
        mToolbarLocationText = binding.toolbar.currentLocationText;
        mToolbarSearch = binding.toolbar.search;
        mToolbarSearch.setOnQueryTextListener(this);
        mToolbarSearch.setOnSearchClickListener(this);
        mToolbarSearch.setOnCloseListener(this);

        contentView.setLayoutManager(new LinearLayoutManager(this));
        mLocationAdapter = new LocationRecyclerAdapter();
        contentView.setAdapter(mLocationAdapter);

        ViewPager fragmentPager = binding.pager;
        ViewPagerFragmentAdapter fragmentAdapter = new ViewPagerFragmentAdapter(getSupportFragmentManager());
        fragmentPager.setAdapter(fragmentAdapter);

        currentLocation = new ObservableField<>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mToolbarLocationIcon = null;
        mToolbarLocationText = null;
        mToolbarSearch.setOnQueryTextListener(null);
        mToolbarSearch.setOnSearchClickListener(null);
        mToolbarSearch.setOnCloseListener(null);
        mToolbarSearch = null;

        mLocationManager.removeUpdates(this);
        mLocationManager = null;

        mLocationAdapter = null;

        mDailyForecastFragmentListener = null;
        mHourlyForecastFragmentListener = null;
    }

    @NonNull
    @Override
    public ForecastActivityPresenter createPresenter() {
        return new ForecastActivityPresenter();
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null;
    }

    @Override
    public void setData(List<? extends ALocation> data) {
        for (ALocation location : data) {
            location.setListener(this);
        }

        mLocationAdapter.setData(data);
        showContent();
    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }

    @Override
    public void setCurrentLocation(ALocation current) {
        currentLocation.set(current.getTitle());
        onLocationFound(current.getId());
        hideLoadingView();
    }

    private void hideLoadingView() {
        loadingView.setVisibility(View.INVISIBLE);
    }

    private void showLoadingView() {
        loadingView.setVisibility(View.VISIBLE);
    }

    private boolean isLoading() {
        return loadingView.getVisibility() == View.VISIBLE;
    }

    private void hideContentView() {
        contentView.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!mToolbarSearch.isIconified()) {
                mToolbarSearch.setIconified(true);

                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    // TOOLBAR section
    @Override
    public ObservableField<String> getCurrentLocation() {
        return currentLocation;
    }

    @Override
    public void onCurrentLocationClick() {
        if (!isLoading()) {
            getLocation();
        }
    }

    @Override
    public void onItemClick(ALocation location) {
        currentLocation.set(location.getTitle());
        hideContentView();
        onLocationFound(location.getId());
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (!TextUtils.isEmpty(query)) {
            presenter.processLocationQuery(query);
            return true;
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }


    @Override
    public boolean onClose() {
        mToolbarLocationIcon.setVisibility(View.VISIBLE);
        mToolbarLocationText.setVisibility(View.VISIBLE);
        return false;
    }

    @Override
    public void onClick(View v) {
        mToolbarLocationIcon.setVisibility(View.GONE);
        mToolbarLocationText.setVisibility(View.GONE);
    }
    // End of TOOLBAR section

    // LOCATION region
    @Override
    public void onLocationChanged(Location location) {
        // 0,0 coordinates is where the Equator crosses the Greenwich meridian, sounds good for spotting out errors !
        if (location != null && location.getLatitude() != 0 && location.getLongitude() != 0) {
            mLocationManager.removeUpdates(this);
            presenter.processLocationCoordinates(location);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // Do nothing, for now..
    }

    @Override
    public void onProviderEnabled(String provider) {
        getLocation();
    }

    @Override
    public void onProviderDisabled(String provider) {
        requestPermissions();
    }

    private void getLocation() {
        currentLocation.set(getResources().getString(R.string.toolbar_location_hint));

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try {
            // TODO: Include the GPS provider as well
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    Constants.MIN_TIME_LOCATION_UPDATE,
                    Constants.MIN_DISTANCE_LOCATION_UPDATE,
                    this
            );
        } catch (SecurityException e) {
            requestPermissions();
        }
    }

    private void requestPermissions() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    // End of LOCATION region

    // ACTIVITY-FRAGMENT COMMUNICATION region
    private interface IForecastFragmentListener {
        void onLocationFound(Integer id);
    }

    public interface IDailyForecastFragmentListener extends IForecastFragmentListener {}

    public interface IHourlyForecastFragmentListener extends IForecastFragmentListener {}

    public void onLocationFound(Integer id) {
        if (mHourlyForecastFragmentListener != null) {
            mHourlyForecastFragmentListener.onLocationFound(id);
        }

        if (mDailyForecastFragmentListener != null) {
            mDailyForecastFragmentListener.onLocationFound(id);
        }
    }

    public void setDailyForecastFragmentListener(IDailyForecastFragmentListener dailyForecastFragmentListener) {
        mDailyForecastFragmentListener = dailyForecastFragmentListener;
    }

    public void setHourlyForecastFragmentListener(IHourlyForecastFragmentListener hourlyForecastFragmentListener) {
        mHourlyForecastFragmentListener = hourlyForecastFragmentListener;
    }
    // End of ACTIVITY-FRAGMENT COMMUNICATION region
}
