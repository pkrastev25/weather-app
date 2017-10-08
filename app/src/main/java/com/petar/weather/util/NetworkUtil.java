package com.petar.weather.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Helper class for managing the network state.
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 18.7.2017
 */
public class NetworkUtil {

    /**
     * Makes a check if currently the device is connected to an internet connection.
     * Note, it does not check if there is internet on the connection!
     *
     * @param context {@link Context} reference
     * @return True if a connection is established, false otherwise
     */
    public static boolean isNetworkConnected(Context context) {
        try {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = manager.getActiveNetworkInfo();

            return activeNetwork.isConnected();
        } catch (Exception e) {
            return false;
        }
    }
}
