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

import com.hannesdorfmann.mosby3.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.MvpLceViewStateActivity;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.data.RetainingLceViewState;
import com.petar.weather.R;
import com.petar.weather.databinding.ActivityForecastBinding;
import com.petar.weather.logic.models.ALocation;
import com.petar.weather.presenters.ForecastActivityPresenter;
import com.petar.weather.ui.adapter.ViewPagerFragmentAdapter;
import com.petar.weather.ui.views.IForecastActivity;
import com.petar.weather.ui.views.IToolbarView;
import com.petar.weather.util.Constants;

public class ForecastActivity extends MvpLceViewStateActivity<ViewPager, ALocation, IForecastActivity, ForecastActivityPresenter>
        implements IForecastActivity, IToolbarView, LocationListener {

    // GENERAL ACTIVITY helpers
    private ALocation mCurrentLocation;

    // TOOLBAR helpers
    private ObservableField<String> mCurrentLocationTitle;

    // LOCATION helpers
    private LocationManager mLocationManager;

    // ACTIVITY-FRAGMENT COMMUNICATION helpers
    private IDailyForecastFragmentListener mDailyForecastFragmentListener;
    private IHourlyForecastFragmentListener mHourlyForecastFragmentListener;

    // GENERAL ACTIVITY region
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityForecastBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_forecast);
        binding.setView(this);

        ViewPagerFragmentAdapter fragmentAdapter = new ViewPagerFragmentAdapter(getSupportFragmentManager());
        contentView.setAdapter(fragmentAdapter);

        mCurrentLocationTitle = new ObservableField<>();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mCurrentLocation == null) {
            getLocation();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mLocationManager.removeUpdates(this);
        mLocationManager = null;

        mDailyForecastFragmentListener = null;
        mHourlyForecastFragmentListener = null;
    }
    // End of GENERAL ACTIVITY region

    // MVP-LCE-VIEW-STATE-ACTIVITY region
    @NonNull
    @Override
    public ForecastActivityPresenter createPresenter() {
        return new ForecastActivityPresenter();
    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }

    @Override
    public void setData(ALocation current) {
        mCurrentLocation = current;
        mCurrentLocationTitle.set(current.getTitle());
        onLocationFound(current.getId());
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null;
    }

    @Override
    public void showContent() {
        super.showContent();

        mCurrentLocation = getData();
        mCurrentLocationTitle.set(getData().getTitle());
    }

    @Override
    public ALocation getData() {
        return mCurrentLocation;
    }

    @NonNull
    @Override
    public LceViewState<ALocation, IForecastActivity> createViewState() {
        return new RetainingLceViewState<>();
    }
    // End of MVP-LCE-VIEW-STATE-ACTIVITY region

    // TOOLBAR section
    @Override
    public ObservableField<String> getCurrentLocationTitle() {
        return mCurrentLocationTitle;
    }

    @Override
    public void onFindCurrentLocationClick() {
        getLocation();
    }

    @Override
    public void onCurrentLocationClick() {
        // TODO: Implement
    }

    @Override
    public void onSearchClick() {
        // TODO: Start a search activity
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
        mCurrentLocationTitle.set(getResources().getString(R.string.toolbar_location_hint));

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
