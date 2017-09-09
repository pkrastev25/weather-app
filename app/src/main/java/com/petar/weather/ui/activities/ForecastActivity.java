package com.petar.weather.ui.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.location.Location;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.MvpLceViewStateActivity;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.data.ParcelableDataLceViewState;
import com.petar.weather.R;
import com.petar.weather.databinding.ActivityForecastBinding;
import com.petar.weather.listeners.IForecastFragmentListener;
import com.petar.weather.logic.models.ALocation;
import com.petar.weather.persistence.PersistenceLogic;
import com.petar.weather.presenters.ForecastActivityPresenter;
import com.petar.weather.ui.adapter.ViewPagerFragmentAdapter;
import com.petar.weather.ui.views.IForecastActivity;
import com.petar.weather.ui.views.IToolbarView;
import com.petar.weather.app.Constants;
import com.petar.weather.util.ErrorHandlingUtil;

public class ForecastActivity extends MvpLceViewStateActivity<ViewPager, ALocation, IForecastActivity, ForecastActivityPresenter>
        implements IForecastActivity, IToolbarView, IForecastFragmentListener, OnSuccessListener<Location>, OnFailureListener {

    private ALocation mCurrentLocation;

    // LOCATION helpers
    private FusedLocationProviderClient mFusedLocationProviderClient;

    // TOOLBAR helpers
    private ObservableField<String> mCurrentLocationTitle;

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
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            mCurrentLocation = bundle.getParcelable(Constants.BUNDLE_LOCATION_FROM_SEARCH_KEY);
        }

        if (mCurrentLocation == null && !PersistenceLogic.getInstance(this).shouldLocationDataUpdate()) {
            mCurrentLocation = PersistenceLogic.getInstance(this).getLocation();
        }

        if (mCurrentLocation != null) {
            mCurrentLocationTitle.set(mCurrentLocation.getTitle());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mFusedLocationProviderClient = null;
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
        if (mCurrentLocation == null) {
            findLocation();
        } else {
            showContent();
        }
    }

    @Override
    public void setData(ALocation current) {
        mCurrentLocation = current;
        mCurrentLocationTitle.set(current.getTitle());
        onLocationFound(current.getIdWOE());
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return ErrorHandlingUtil.generateErrorText(this, e);
    }

    @Override
    public ALocation getData() {
        return mCurrentLocation;
    }

    @NonNull
    @Override
    public LceViewState<ALocation, IForecastActivity> createViewState() {
        return new ParcelableDataLceViewState<>();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setShowContentState() {
        viewState.setStateShowContent(getData());
    }
    // End of MVP-LCE-VIEW-STATE-ACTIVITY region

    // TOOLBAR section
    @Override
    public ObservableField<String> getCurrentLocationTitle() {
        return mCurrentLocationTitle;
    }

    @Override
    public void onFindCurrentLocationClick() {
        findLocation();
    }

    @Override
    public void onCurrentLocationClick() {
        // TODO: Implement
    }

    @Override
    public void onSearchClick() {
        startActivity(
                new Intent(this, SearchActivity.class)
        );
    }
    // End of TOOLBAR section

    // LOCATION region
    private void findLocation() {
        mCurrentLocationTitle.set(getResources().getString(R.string.toolbar_location_hint));

        try {
            mFusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(this)
                    .addOnFailureListener(this);
        } catch (SecurityException e) {
            e.printStackTrace();
            requestPermissions();
        }
    }

    private void requestPermissions() {
        startActivity(
                new Intent(this, MainActivity.class)
        );
        finish();
    }

    @Override
    public void onSuccess(Location location) {
        if (location != null) {
            presenter.processLocationCoordinates(this, location, false);
        } else {
            // TODO: Implement logic for actually fetching the current location !!!
            // TODO: Showing this error is not enough
            showError(new Throwable(Constants.ErrorHandling.DEFAULT), false);
        }
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        e.printStackTrace();
        showError(new Throwable(Constants.ErrorHandling.DEFAULT), false);
    }
    // End of LOCATION region

    // ACTIVITY-FRAGMENT COMMUNICATION region
    private interface IForecastActivityListener {
        void onLocationFound(@NonNull Integer idWOE);
    }

    public interface IDailyForecastFragmentListener extends IForecastActivityListener {
    }

    public interface IHourlyForecastFragmentListener extends IForecastActivityListener {
    }

    public void onLocationFound(Integer idWOE) {
        if (mHourlyForecastFragmentListener != null) {
            mHourlyForecastFragmentListener.onLocationFound(idWOE);
        }

        if (mDailyForecastFragmentListener != null) {
            mDailyForecastFragmentListener.onLocationFound(idWOE);
        }
    }

    public void setDailyForecastFragmentListener(IDailyForecastFragmentListener dailyForecastFragmentListener) {
        mDailyForecastFragmentListener = dailyForecastFragmentListener;
    }

    public void setHourlyForecastFragmentListener(IHourlyForecastFragmentListener hourlyForecastFragmentListener) {
        mHourlyForecastFragmentListener = hourlyForecastFragmentListener;
    }
    // End of ACTIVITY-FRAGMENT COMMUNICATION region

    // FRAGMENT-ACTIVITY COMMUNICATION region
    @Override
    public void onFragmentCreated() {
        if (mCurrentLocation != null) {
            onLocationFound(mCurrentLocation.getIdWOE());
        }
    }
    // End of FRAGMENT-ACTIVITY COMMUNICATION region
}
