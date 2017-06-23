package com.petar.weather.ui.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;
import com.petar.weather.R;
import com.petar.weather.databinding.ActivityMainBinding;
import com.petar.weather.presenters.MainActivityPresenter;
import com.petar.weather.ui.views.IMainActivity;
import com.petar.weather.util.Constants;

public class MainActivity extends MvpActivity<IMainActivity, MainActivityPresenter> implements LocationListener, IMainActivity {

    private LocationManager mLocationManager;
    private View mSplashScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setView(this);
        mSplashScreen = binding.splashScreen;

        checkPermissions();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermissions();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationManager.removeUpdates(this);
        mLocationManager = null;
        mSplashScreen = null;
    }

    @NonNull
    @Override
    public MainActivityPresenter createPresenter() {
        return new MainActivityPresenter();
    }

    private void checkPermissions() {
        if (arePermissionsAllowed()) {
            getLocation();
        } else {
            onRequestPermissions();
        }
    }

    private boolean arePermissionsAllowed() {
        return ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED;
    }

    private void getLocation() {
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
            onRequestPermissions();
        }
    }

    private void onRequestPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            showExplanationDialog();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constants.REQUEST_CODE_ACCESS_FINE_LOCATION);
        }
    }

    private void showExplanationDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.alert_dialog_header_warning)
                .setMessage(R.string.alert_dialog_text_warning)
                .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                Constants.REQUEST_CODE_ACCESS_FINE_LOCATION);
                    }
                }).setNegativeButton(R.string.button_close_app, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
        });
        builder.create().show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == Constants.REQUEST_CODE_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                finish();
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        // 0,0 coordinates is where the Equator crosses the Greenwich meridian, sounds good for spotting out errors !
        if (location != null && location.getLatitude() != 0 && location.getLongitude() != 0) {
            mLocationManager.removeUpdates(this);
            presenter.processLocation(location);
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
        showEnableSettingsDialog();
    }

    private void showEnableSettingsDialog() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.alert_dialog_header_enable_location)
                .setMessage(R.string.alert_dialog_text_enable_location)
                .setPositiveButton(R.string.button_location_settings, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        finish();
                    }
                });
        dialog.show();
    }

    @Override
    public void hideSplashScreen() {
        mSplashScreen.setVisibility(View.INVISIBLE);
    }

    @Override
    public void navigateToForecastActivity(int id) {
        Intent intent = new Intent(this, ForecastActivity.class);
        intent.putExtra("woId", id);
        startActivity(intent);
    }
}
