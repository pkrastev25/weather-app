package com.petar.weather.ui.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.petar.weather.R;

import com.petar.weather.app.Constants;
import com.petar.weather.databinding.ActivityHomeBinding;

/**
 * Main entry point of the application. Displays a splash screen, checks if the
 * user has enabled location permissions for this application, asks him if needed.
 * Navigates to the {@link ForecastActivity} if the permissions are fulfilled.
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 22.6.2017
 */
public class HomeActivity extends AppCompatActivity implements Animation.AnimationListener {

    private View mSplashScreen;
    private AlphaAnimation mAnimation;

    // --------------------------------------------------------
    // GENERAL-ACTIVITY region
    // --------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityHomeBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        binding.setView(this);
        mSplashScreen = binding.splashScreenView;

        mAnimation = new AlphaAnimation(
                Constants.ANIMATION_ALPHA_SPLASH_SCREEN_START,
                Constants.ANIMATION_ALPHA_SPLASH_SCREEN_END
        );
        mAnimation.setDuration(Constants.ANIMATION_ALPHA_SPLASH_SCREEN_DURATION_MILLIS);
        mAnimation.setAnimationListener(this);
        mSplashScreen.setAnimation(mAnimation);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mSplashScreen = null;
        mAnimation.setAnimationListener(null);
        mAnimation = null;
    }

    // --------------------------------------------------------
    // End of GENERAL-ACTIVITY region
    // --------------------------------------------------------

    // --------------------------------------------------------
    // PERMISSIONS region
    // --------------------------------------------------------

    /**
     * Makes a check for the location permissions.
     */
    private void checkPermissions() {
        if (arePermissionsAllowed()) {
            navigateToForecastActivity();
        } else {
            onRequestPermissions();
        }
    }

    /**
     * Makes a check if the user already gave location permissions.
     *
     * @return True if the permissions are granted, false otherwise
     */
    private boolean arePermissionsAllowed() {
        return ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Requests the location permissions from the user. Displays a dialog in which the user
     * can decide whether or not to grant them. If already declined, it will display another
     * dialog stating that the app cannot be used without the location permissions.
     */
    private void onRequestPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            showExplanationDialog();
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    Constants.PERMISSION_REQUEST_CODE_ACCESS_FINE_LOCATION
            );
        }
    }

    /**
     * Shows a dialog to the user explaining that this application cannot be used
     * without location permissions.
     */
    private void showExplanationDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(false)
                .setTitle(R.string.alert_dialog_location_permissions_header)
                .setMessage(R.string.alert_dialog_location_permissions_text)
                .setPositiveButton(R.string.button_ok, (dialog, which) -> ActivityCompat.requestPermissions(HomeActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        Constants.PERMISSION_REQUEST_CODE_ACCESS_FINE_LOCATION)
                ).setNegativeButton(R.string.button_close_app, (dialog, which) -> finish());

        builder.create().show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == Constants.PERMISSION_REQUEST_CODE_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                navigateToForecastActivity();
            } else {
                finish();
            }
        }
    }

    /**
     * Navigates to {@link ForecastActivity}.
     */
    public void navigateToForecastActivity() {
        startActivity(
                new Intent(this, ForecastActivity.class)
        );
        finish();
    }

    // --------------------------------------------------------
    // End of PERMISSIONS region
    // --------------------------------------------------------

    // --------------------------------------------------------
    // ANIMATION region
    // --------------------------------------------------------

    @Override
    public void onAnimationStart(Animation animation) {
        // DO nothing, for now...
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        checkPermissions();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        // Do nothing, for now...
    }

    // --------------------------------------------------------
    // End of ANIMATION region
    // --------------------------------------------------------
}
