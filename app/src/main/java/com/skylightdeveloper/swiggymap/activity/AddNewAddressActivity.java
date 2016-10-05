package com.skylightdeveloper.swiggymap.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.skylightdeveloper.swiggymap.Constant.SConstant;
import com.skylightdeveloper.swiggymap.Prefference;
import com.skylightdeveloper.swiggymap.R;

public class AddNewAddressActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = AddNewAddressActivity.class.getSimpleName();

    private static final int REQUEST_LOCATION_PERMISSION = 114;
    private static final int PICK_ADDRES = 153;

    private TextView mAddAdressTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle(getResources().getString(R.string.main_activity));

        setIdstoViews();
        setListenerToViews();
    }

    private void setIdstoViews() {

        mAddAdressTv = (TextView) findViewById(R.id.add_new_address_tv_id);
    }

    private void setListenerToViews() {

        mAddAdressTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (mAddAdressTv == view) {

            if (requestLocationPermissions())
                IsNeedToshowLocationChooserDialog();

            return;
        }
    }

    private void IsNeedToshowLocationChooserDialog() {

        if (isGPSActive()) {
            moveToNewLocationMapActivity();
        } else {
            buildAlertMessageForNoGps();
        }
    }

/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_ADDRES && data != null) {

            String addr = data.getStringExtra(SConstant.ADDRESS);
            double lat = data.getDoubleExtra(SConstant.LATITUDE, 0);
            double longi = data.getDoubleExtra(SConstant.LONGITUDE, 0);

            Log.d(TAG, "onActivityResult: addr = " + addr + "\n lat = " + lat + "\n longi = " + longi);
        }
    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
//                    startVideoConsultation(position);
                    IsNeedToshowLocationChooserDialog();

                } else {

                    Prefference appPreferences = new Prefference(this);

                    showLongToast("Location Permission is necessary to get your current address");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    appPreferences.setLocationPermissionDeniedStatus(true);

                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private boolean requestLocationPermissions() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            return true;
        } else {
            Prefference appPreferences = new Prefference(this);
            if (!appPreferences.getLocationPermissionDeniedStatus()) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_LOCATION_PERMISSION);

            } else if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                showDialogOK(getString(R.string.android_location_permissions_denied),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE:

                                        ActivityCompat.requestPermissions(AddNewAddressActivity.this,
                                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                                REQUEST_LOCATION_PERMISSION);

                                        break;
                                    case DialogInterface.BUTTON_NEGATIVE:
                                        // proceed with logic by disabling the related features or quit the app.
                                        break;
                                }
                            }
                        });
            } else {
                showSettingSnackBar("You need to enable Location permission from Settings ");
            }
        }
        return false;
    }

    private void moveToNewLocationMapActivity() {

        Intent intent = new Intent(this, NewLocationMapActivity.class);
        startActivity(intent);
    }

    private void buildAlertMessageForNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();

    }

    private boolean isGPSActive() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean statusOfGPS = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

        return statusOfGPS;
    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    private void launchAppSettingScreen() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

    private void showSettingSnackBar(String message) {

        Snackbar snackbar = Snackbar
                .make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.action_settings), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        launchAppSettingScreen();
                    }
                });

        snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.colorAccent));

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setMaxLines(4);
        snackbar.show();
    }
}
