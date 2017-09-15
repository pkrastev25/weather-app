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
import com.petar.weather.databinding.ActivityMainBinding;
import com.petar.weather.app.Constants;

public class MainActivity extends AppCompatActivity implements Animation.AnimationListener {

    private View mSplashScreen;
    private AlphaAnimation mAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setView(this);
        mSplashScreen = binding.splashScreen;

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

    private void checkPermissions() {
        if (arePermissionsAllowed()) {
            navigateToForecastActivity();
        } else {
            onRequestPermissions();
        }
    }

    private boolean arePermissionsAllowed() {
        return ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED;
    }

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

    private void showExplanationDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(false)
                .setTitle(R.string.alert_dialog_location_permissions_header)
                .setMessage(R.string.alert_dialog_location_permissions_text)
                .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                Constants.PERMISSION_REQUEST_CODE_ACCESS_FINE_LOCATION);
                    }
                })
                .setNegativeButton(R.string.button_close_app, new DialogInterface.OnClickListener() {
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

        if (requestCode == Constants.PERMISSION_REQUEST_CODE_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                navigateToForecastActivity();
            } else {
                finish();
            }
        }
    }

    public void navigateToForecastActivity() {
        startActivity(
                new Intent(this, ForecastActivity.class)
        );
        finish();
    }

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
}
