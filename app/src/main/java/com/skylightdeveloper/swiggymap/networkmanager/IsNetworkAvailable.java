package com.skylightdeveloper.swiggymap.networkmanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Akash Wangalwar on 23-09-2016.
 */
public class IsNetworkAvailable{
        public static boolean hasConnection(Context context) {

            // Class that answers queries about the state of network connectivity. It also notifies applications when network connectivity changes.
            //Get an instance of this class by calling Context.getSystemService(Context.CONNECTIVITY_SERVICE).
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            //NetworkInfo class describes the status of a network interface.
            //getActiveNetworkInfo(): Returns details about the currently active default data network.
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            //checking for network connectivity exists. If yes then return true otherwise return false.
            if (activeNetwork != null && activeNetwork.isConnected()) {
                return true;
            }

            return false;
        }
}
