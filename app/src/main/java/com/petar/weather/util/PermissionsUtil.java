package com.petar.weather.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

public final class PermissionsUtil {

    public static final String[] APP_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION};

    public static boolean areAllAppPermissionsGranted(final Context context) {
        boolean result = false;

        for (final String permission : APP_PERMISSIONS) {
            result = result || isPermissionGranted(context, permission);
        }

        return result;
    }

    public static boolean isPermissionGranted(final Context context, final String permission) {
        return ContextCompat.checkSelfPermission(
                context, permission
        ) == PackageManager.PERMISSION_GRANTED;
    }
}
