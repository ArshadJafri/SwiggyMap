package com.skylightdeveloper.swiggymap.activity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.skylightdeveloper.swiggymap.Constant.SConstant;
import com.skylightdeveloper.swiggymap.R;
import com.skylightdeveloper.swiggymap.Util.UiUtils;
import com.skylightdeveloper.swiggymap.interfaces.AddressInterface;
import com.skylightdeveloper.swiggymap.model.Address;
import com.skylightdeveloper.swiggymap.model.Share;
import com.skylightdeveloper.swiggymap.networkmanager.NetworkManager;

import java.util.List;

/**
 * Created by Akash Wangalwar on 23-09-2016.
 */
public class NewLocationMapActivity extends BaseFragmentActivity implements
        OnMapReadyCallback, GoogleMap.OnCameraChangeListener, AddressInterface, View.OnClickListener {

    private static final String TAG = BaseFragmentActivity.class.getSimpleName();

    private GoogleMap googleMap;
    private MarkerOptions marker;
    private LatLng centerOfMap;
    private TextView mPickLocation;
    private static final int PICK_ADDRES = 153;
    private boolean retried;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_location_map_activity_layout);

        setIdsToViews();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.track_map);

        mapFragment.getMapAsync(this);
    }

    private void setIdsToViews() {

        mPickLocation = (TextView) findViewById(R.id.pick_location_tv_id);
        mPickLocation.setOnClickListener(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        if (this.googleMap != null) {

            GetCurrentLocation();
            this.googleMap.setMyLocationEnabled(true);

            this.googleMap.setOnCameraChangeListener(this);
        }
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

        centerOfMap = googleMap.getCameraPosition().target;

        Log.d(TAG, "onCameraChange: lat : " +
                centerOfMap.latitude + " long : " + centerOfMap.longitude);

        Log.d(TAG, "onCameraChange: Truncated : lat : " +
                roundDoubleValueToSixPrec(centerOfMap.latitude, 6)
                + " long : " + roundDoubleValueToSixPrec(centerOfMap.longitude, 6));

    }

    private void getAddressFromLatLng(String latLong) {
        NetworkManager networkManager = new NetworkManager(this);
        networkManager.getAddressFromlatLang(this, latLong);
    }

    private void GetCurrentLocation() {

        double[] d = getlocation();
        Share.lat = d[0];
        Share.lng = d[1];
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(Share.lat, Share.lng), 15));
    }

    public double[] getlocation() {

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = lm.getProviders(true);

        Location l = null;
        for (int i = 0; i < providers.size(); i++) {
            l = lm.getLastKnownLocation(providers.get(i));
            if (l != null)
                break;
        }
        double[] gps = new double[2];

        if (l != null) {
            gps[0] = l.getLatitude();
            gps[1] = l.getLongitude();
        }
        return gps;
    }

    public static double roundDoubleValueToSixPrec(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    @Override
    public void onSuccess(Address address) {

        if (address != null && address.getResults() != null && address.getResults().size() > 0) {

            Log.d(TAG, "onSuccess: add : " + address.getResults().get(0).getFormatted_address());
            Log.d(TAG, "onSuccess: lat : " + roundDoubleValueToSixPrec(address.getResults().get(0).
                    getGeometry().getLocation().getLat(), 6));
            Log.d(TAG, "onSuccess: long: " + roundDoubleValueToSixPrec(address.getResults().get(0).
                    getGeometry().getLocation().getLng(), 6));

            Intent intent = new Intent(this,SaveNewAddressActivity.class);
            intent.putExtra(SConstant.ADDRESS, address.getResults().get(0)
                    .getFormatted_address());

            intent.putExtra(SConstant.LATITUDE, address.getResults().get(0)
                    .getGeometry().getLocation().getLat());

            intent.putExtra(SConstant.LONGITUDE, address.getResults().get(0)
                    .getGeometry().getLocation().getLng());

            intent.putExtra(SConstant.NEWADDRESS,true);
            startActivity(intent);
            finish();

        } else if (address != null && address.getStatus() != null &&
                address.getStatus().equalsIgnoreCase(SConstant.ZERO_RESULTS)) {

            if (!retried) {
                retried = true;

                if (centerOfMap != null /*&& centerOfMap.latitude != 0 && centerOfMap.longitude !=0*/) {

                    UiUtils.showProgressDialog(this, "Re-fetching Address...");

                    NetworkManager networkManager = new NetworkManager(this);
                    networkManager.retryGetAddressFromlatLang(this,
                            roundDoubleValueToSixPrec(centerOfMap.latitude, 6) + "," +
                                    roundDoubleValueToSixPrec(centerOfMap.longitude, 6));
                } else {
                    Log.d(TAG, "onClick: Location UnAvailable ?");
                }

            } else {
                showLongSnackBar("We couldn't detect your location. Please try again and point to correct location.");
            }
        } else {
            showLongSnackBar("We couldn't detect your location. Please try again and point to correct location.");
        }
    }

    @Override
    public void onfailure(String errorMessage) {

        showShortToast(errorMessage);
        Log.e(TAG, "onfailure: errorMessage : " + errorMessage);
    }

    @Override
    public void onClick(View view) {
        if (view == mPickLocation) {

            if (centerOfMap != null /*&& centerOfMap.latitude != 0 && centerOfMap.longitude !=0*/) {
                UiUtils.showProgressDialog(this, "Fetching Address...");
                getAddressFromLatLng(roundDoubleValueToSixPrec(centerOfMap.latitude, 6) + ","
                        + roundDoubleValueToSixPrec(centerOfMap.longitude, 6));
            } else {
                Log.d(TAG, "onClick: Location UnAvailable ?");
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}


