package com.petar.weather.ui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.MvpLceViewStateActivity;
import com.hannesdorfmann.mosby3.mvp.viewstate.lce.data.ParcelableDataLceViewState;
import com.petar.weather.R;
import com.petar.weather.databinding.ActivityForecastBinding;
import com.petar.weather.listeners.IForecastActivityForDailyForecastFragmentListener;
import com.petar.weather.listeners.IForecastActivityForHourlyForecastFragmentListener;
import com.petar.weather.listeners.IForecastFragmentListener;
import com.petar.weather.logic.models.ALocation;
import com.petar.weather.persistence.PersistenceLogic;
import com.petar.weather.presenters.ForecastActivityPresenter;
import com.petar.weather.ui.adapter.ViewPagerFragmentAdapter;
import com.petar.weather.ui.views.IForecastActivity;
import com.petar.weather.ui.views.IToolbarView;
import com.petar.weather.app.Constants;
import com.petar.weather.util.ErrorHandlingUtil;

/**
 * Main activity for the forecast state. Renders a toolbar and a view pager
 * with the different forecast types, as well as settings. Contains logic for finding
 * the current location.
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 23.6.2017
 */
public class ForecastActivity
        extends MvpLceViewStateActivity<ViewPager, ALocation, IForecastActivity, ForecastActivityPresenter>
        implements IForecastActivity, IToolbarView, IForecastFragmentListener, OnSuccessListener<LocationSettingsResponse>, OnFailureListener, OnCompleteListener<LocationSettingsResponse> {

    // Current location
    private ALocation mCurrentLocation;

    // LOCATION helpers
    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LocationCallback mLocationCallback;
    private boolean mRequestingLocationUpdates;
    private boolean mIsTaskStarted;

    // TOOLBAR helpers
    private ObservableField<String> mCurrentLocationTitle;

    // ACTIVITY-FRAGMENT COMMUNICATION helpers
    private IForecastActivityForDailyForecastFragmentListener mDailyForecastFragmentListener;
    private IForecastActivityForHourlyForecastFragmentListener mHourlyForecastFragmentListener;

    // --------------------------------------------------------
    // GENERAL ACTIVITY region
    // --------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityForecastBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_forecast);
        binding.setView(this);

        ViewPagerFragmentAdapter fragmentAdapter = new ViewPagerFragmentAdapter(getSupportFragmentManager());
        contentView.setAdapter(fragmentAdapter);

        mCurrentLocationTitle = new ObservableField<>();
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                if (locationResult.getLastLocation() != null) {
                    mRequestingLocationUpdates = false;
                    mFusedLocationProviderClient.removeLocationUpdates(this);
                    presenter.processLocationCoordinates(ForecastActivity.this, locationResult.getLastLocation());
                }
            }
        };

        // The user might want to know the forecast for another location
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            mCurrentLocation = bundle.getParcelable(Constants.BUNDLE_LOCATION_FROM_SEARCH_KEY);
        }

        // Do not start the location finding logic if it was enabled recently
        if (mCurrentLocation == null && !PersistenceLogic.getInstance(this).shouldLocationDataUpdate()) {
            mCurrentLocation = PersistenceLogic.getInstance(this).getLocation();
        }

        if (mCurrentLocation != null) {
            mCurrentLocationTitle.set(mCurrentLocation.getTitle());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Resume location updates, if they were running before the application was paused
        if (mCurrentLocation == null && mRequestingLocationUpdates) {
            findLocation();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Disable location updates, saves battery life!
        mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mFusedLocationProviderClient = null;
        mLocationCallback = null;
        mLocationRequest = null;
        mDailyForecastFragmentListener = null;
        mHourlyForecastFragmentListener = null;
    }

    // --------------------------------------------------------
    // End of GENERAL ACTIVITY region
    // --------------------------------------------------------

    // --------------------------------------------------------
    // MVP-LCE-VIEW-STATE-ACTIVITY region
    // --------------------------------------------------------

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
        mCurrentLocationTitle.set(getString(R.string.toolbar_location_not_found));

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
    public void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public ALocation getCurrentLocationShown() {
        return mCurrentLocation;
    }

    @Override
    public void onLocationDidNotChange() {
        mCurrentLocationTitle.set(mCurrentLocation.getTitle());
        viewState.setStateShowContent(getData());
    }

    // --------------------------------------------------------
    // End of MVP-LCE-VIEW-STATE-ACTIVITY region
    // --------------------------------------------------------

    // --------------------------------------------------------
    // TOOLBAR section
    // --------------------------------------------------------

    @Override
    public ObservableField<String> getCurrentLocationTitle() {
        return mCurrentLocationTitle;
    }

    @Override
    public void onFindCurrentLocationClick() {
        findLocation();
    }

    @Override
    public void onSearchClick() {
        startActivity(
                new Intent(this, SearchActivity.class)
        );
    }

    // --------------------------------------------------------
    // End of TOOLBAR section
    // --------------------------------------------------------

    // --------------------------------------------------------
    // LOCATION region
    // --------------------------------------------------------

    /**
     * Starts the process of finding the current location of the user. Creates a new instance
     * of {@link LocationRequest}, if needed, and starts a {@link Task} which checks if
     * the currently configured settings are sufficient for receiving location updates.
     */
    private void findLocation() {
        mRequestingLocationUpdates = true;
        mCurrentLocationTitle.set(getResources().getString(R.string.toolbar_location_hint));

        // Do not create another instance, the contents are static
        if (mLocationRequest == null) {
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(Constants.LOCATION_UPDATE_INTERVAL_MILLIS);
            mLocationRequest.setFastestInterval(Constants.LOCATION_FASTEST_UPDATE_INTERVAL_MILLIS);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        }

        // Do not start a task, if there is one already running
        if (!mIsTaskStarted) {
            mIsTaskStarted = true;
            presenter.onLocationTaskStatusChange(true);

            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(mLocationRequest);
            SettingsClient client = LocationServices.getSettingsClient(this);
            Task<LocationSettingsResponse> mTask = client.checkLocationSettings(builder.build());
            mTask.addOnSuccessListener(this).addOnFailureListener(this).addOnCompleteListener(this);
        }
    }

    /**
     * The currently configured settings are sufficient, start the location updates.
     *
     * @param locationSettingsResponse Ignored
     */
    @Override
    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
        try {
            mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
        } catch (SecurityException e) {
            requestPermissions();
        }
    }

    /**
     * Navigates to the {@link MainActivity} and asks the user for permissions.
     */
    private void requestPermissions() {
        startActivity(
                new Intent(this, MainActivity.class)
        );
        finish();
    }

    /**
     * The currently configured settings are insufficient. Attempts to resolve
     * the issue by showing the user dialogs.
     *
     * @param e The cause of the configuration failure
     */
    @Override
    public void onFailure(@NonNull Exception e) {
        try {
            int statusCode = ((ApiException) e).getStatusCode();

            switch (statusCode) {
                case CommonStatusCodes.RESOLUTION_REQUIRED:
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    resolvable.startResolutionForResult(ForecastActivity.this, Constants.LOCATION_REQUEST_CHECK_SETTINGS);
                    break;
                default:
                    handleGeneralError(true);
                    break;
            }
        } catch (IntentSender.SendIntentException | ClassCastException e1) {
            handleGeneralError(true);
        }
    }

    @Override
    public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
        mIsTaskStarted = false;
        presenter.onLocationTaskStatusChange(false);
    }

    /**
     * Handles a {@link com.petar.weather.app.Constants.ErrorHandling#DEFAULT} error.
     * Stops the location updates and displays an error text to the user. If required,
     * displays an extra dialog.
     *
     * @param showDialog If set to true, displays a dialog with instructions, false otherwise
     */
    private void handleGeneralError(boolean showDialog) {
        mRequestingLocationUpdates = false;
        mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
        mCurrentLocationTitle.set(getString(R.string.toolbar_location_not_found));
        showError(new Throwable(Constants.ErrorHandling.DEFAULT), false);

        if (showDialog) {
            showEnableSettingsDialog();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constants.LOCATION_REQUEST_CHECK_SETTINGS:
                if (resultCode == Activity.RESULT_OK) {
                    findLocation();
                } else {
                    handleGeneralError(false);
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    /**
     * Shows a dialog to the user when the location updates failed to execute. Gives instructions
     * on how could the error be resolved.
     */
    private void showEnableSettingsDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // TODO: Add a custom implementation that links to the corresponding settings
        builder.setTitle(R.string.alert_dialog_location_not_found_header)
                .setMessage(R.string.alert_dialog_location_not_found_text)
                .setPositiveButton(R.string.button_settings, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(
                                new Intent(Settings.ACTION_SETTINGS)
                        );
                    }
                })
                .setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.show();
    }

    // --------------------------------------------------------
    // End of LOCATION region
    // --------------------------------------------------------

    // --------------------------------------------------------
    // ACTIVITY-FRAGMENT COMMUNICATION region
    // --------------------------------------------------------

    /**
     * Invokes the listeners for {@link com.petar.weather.ui.fragments.HourlyForecastFragment}
     * and {@link com.petar.weather.ui.fragments.DailyForecastFragment} that the current location
     * is found.
     *
     * @param idWOE 'Where on Earth' id, identifies a location
     */
    public void onLocationFound(Integer idWOE) {
        if (mHourlyForecastFragmentListener != null) {
            mHourlyForecastFragmentListener.onLocationFound(idWOE);
        }

        if (mDailyForecastFragmentListener != null) {
            mDailyForecastFragmentListener.onLocationFound(idWOE);
        }
    }

    public void setDailyForecastFragmentListener(IForecastActivityForDailyForecastFragmentListener dailyForecastFragmentListener) {
        mDailyForecastFragmentListener = dailyForecastFragmentListener;
    }

    public void setHourlyForecastFragmentListener(IForecastActivityForHourlyForecastFragmentListener hourlyForecastFragmentListener) {
        mHourlyForecastFragmentListener = hourlyForecastFragmentListener;
    }

    // --------------------------------------------------------
    // End of ACTIVITY-FRAGMENT COMMUNICATION region
    // --------------------------------------------------------

    // --------------------------------------------------------
    // FRAGMENT-ACTIVITY COMMUNICATION region
    // --------------------------------------------------------

    @Override
    public void onFragmentCreated() {
        if (mCurrentLocation != null) {
            onLocationFound(mCurrentLocation.getIdWOE());
        }
    }

    // --------------------------------------------------------
    // End of FRAGMENT-ACTIVITY COMMUNICATION region
    // --------------------------------------------------------
}
