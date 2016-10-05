package com.skylightdeveloper.swiggymap.networkmanager;

import android.content.Context;
import com.skylightdeveloper.swiggymap.R;

/**
 * Created by Akash Wangalwar on 23-09-2016.
 */
public class UrlManager {

    public static String getAddressUrl(String latlong, Context context) {

        return "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + latlong
                + "&location_type=ROOFTOP&result_type=street_address&key=" +
                context.getResources().getString(R.string.google_maps_key);
    }

    public static String retryGetAddressUrl(String latlong) {

        return "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + latlong;
    }
}
