package com.petar.weather.ui.activities;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.petar.weather.R;
import com.petar.weather.events.INavigationEvents;
import com.petar.weather.ui.fragments.SplashScreenFragment;
import com.petar.weather.util.FragmentNavigationUtil;
import com.petar.weather.util.PermissionsUtil;

public class MainActivity extends AppCompatActivity implements INavigationEvents {

    private static final int PERMISSIONS_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (!PermissionsUtil.areAllAppPermissionsGranted(this)) {
            requestPermissions();
            return;
        }

        showStartFragment();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean areAllPermissionsGranted = false;

                    for (final int currentGrantResult : grantResults) {
                        areAllPermissionsGranted = areAllPermissionsGranted || currentGrantResult == PackageManager.PERMISSION_GRANTED;
                    }

                    if (areAllPermissionsGranted) {
                        showStartFragment();
                        return;
                    }
                }

                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        FragmentNavigationUtil.navigateBack(
                FragmentNavigationUtil.PHONE_CONTENT_VIEW_ID,
                getSupportFragmentManager()
        );

        if (FragmentNavigationUtil.isWholeStackEmpty()) {
            finish();
        }
    }

    @Override
    public void replaceTopFragmentOnStack(Fragment fragment, String fragmentNavId) {
        FragmentNavigationUtil.replaceTopFragmentOnStack(
                FragmentNavigationUtil.PHONE_CONTENT_VIEW_ID,
                getSupportFragmentManager(),
                fragment,
                fragmentNavId
        );
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                PermissionsUtil.APP_PERMISSIONS,
                PERMISSIONS_REQUEST_CODE
        );
    }

    private void showStartFragment() {
        if (FragmentNavigationUtil.isWholeStackEmpty()) {
            FragmentNavigationUtil.addFragmentToStack(
                    FragmentNavigationUtil.PHONE_CONTENT_VIEW_ID,
                    getSupportFragmentManager(),
                    SplashScreenFragment.newInstance(),
                    SplashScreenFragment.NAV_ID
            );

            return;
        }

        FragmentNavigationUtil.showTopFragmentOnStack(
                FragmentNavigationUtil.PHONE_CONTENT_VIEW_ID,
                getSupportFragmentManager()
        );
    }
}
