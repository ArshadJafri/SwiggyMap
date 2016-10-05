package com.skylightdeveloper.swiggymap;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.skylightdeveloper.swiggymap.Constant.SConstant;

/**
 * Created by Akash Wangalwar on 23-09-2016.
 */
public class Prefference {

    private static final String APP_SHARED_PREFS = SConstant.APPREFERENCES_NAME;
    private SharedPreferences _sharedPrefs;
    private SharedPreferences.Editor _prefsEditor;

    public Prefference(Context context) {
        try {
            this._sharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS,
                    Activity.MODE_PRIVATE);
            this._prefsEditor = _sharedPrefs.edit();
        } catch (Exception e) {
        }
    }
    private static final String LOCATION_PERMISSION = "location_permission";

    public boolean getLocationPermissionDeniedStatus() {
        return _sharedPrefs.getBoolean(LOCATION_PERMISSION, false);
    }

    public void setLocationPermissionDeniedStatus(boolean isLoggedIn) {

        _prefsEditor.putBoolean(LOCATION_PERMISSION, isLoggedIn);
        _prefsEditor.commit();
    }
}
