package com.skylightdeveloper.swiggymap.networkmanager;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.skylightdeveloper.swiggymap.Util.UiUtils;
import com.skylightdeveloper.swiggymap.interfaces.AddressInterface;
import com.skylightdeveloper.swiggymap.model.Address;

public class NetworkManager {

    private static final String TAG = NetworkManager.class.getSimpleName();
    protected AsyncHttpClient mAsynHttp;
    protected Context mContext;
    Gson gson;
    private String macAddress = "";

    public NetworkManager(Context context) {
        mContext = context;

        if(mAsynHttp == null)
        mAsynHttp = new AsyncHttpClient();

        gson = new Gson();
    }


    public void getAddressFromlatLang(final AddressInterface addressInterface, String latlong) {

        String url = UrlManager.getAddressUrl(latlong, mContext);
        Log.d(TAG, "getAddressFromlatLang() ..: url" + url);

        mAsynHttp.get(url, new HttpResponseHandler(
                mContext) {
            @Override
            public void onSuccess(int statusCode, String response) {
                super.onSuccess(statusCode, response);
                UiUtils.hideProgressDialog();
                Log.d(TAG, "getAddressFromlatLang() ..: success" + response);
                try {
                    Address address = gson.fromJson(response, Address.class);
                    addressInterface.onSuccess(address);
                } catch (Exception e) {
//                 TODO: handle exception
                    addressInterface.onfailure(e.getMessage());
                    Log.e(TAG, "getAddressFromlatLang() Error resposne : " + e.getMessage());
                    UiUtils.hideProgressDialog();
                }

            }

            @Override
            public void onFailure(Throwable e, String message) {
                Log.e(TAG, "getAddressFromlatLang() error response : " + message);
                super.onFailure(e, message);
                try {

                    Address address = gson.fromJson(message, Address.class);

                    addressInterface.onfailure(address.getError_message());
                    UiUtils.hideProgressDialog();

                } catch (Exception e1) {
                    Log.e(TAG, "getAddressFromlatLang() Error resposne : " + e1.getMessage());
                    UiUtils.hideProgressDialog();
                }
            }

        });
    }

    public void retryGetAddressFromlatLang(final AddressInterface addressInterface, String latlong) {

        String url = UrlManager.retryGetAddressUrl(latlong);
        Log.d(TAG, "retryGetAddressFromlatLang() ..: url" + url);

        mAsynHttp.get(url, new HttpResponseHandler(
                mContext) {
            @Override
            public void onSuccess(int statusCode, String response) {
                super.onSuccess(statusCode, response);
                UiUtils.hideProgressDialog();
                Log.d(TAG, "retryGetAddressFromlatLang() ..: success" + response);
                try {
                    Address address = gson.fromJson(response, Address.class);
                    addressInterface.onSuccess(address);
                } catch (Exception e) {
//                 TODO: handle exception
                    addressInterface.onfailure(e.getMessage());
                    Log.e(TAG, "retryGetAddressFromlatLang() Error resposne : " + e.getMessage());
                    UiUtils.hideProgressDialog();
                }
//                UiUtils.hideProgressDialog();

            }

            @Override
            public void onFailure(Throwable e, String message) {
                Log.e(TAG, "retryGetAddressFromlatLang() error response : " + message);
                super.onFailure(e, message);
                try {

                    Address address = gson.fromJson(message, Address.class);

                    addressInterface.onfailure(address.getError_message());
                    UiUtils.hideProgressDialog();

                } catch (Exception e1) {
                    Log.e(TAG, "retryGetAddressFromlatLang() Error resposne : " + e1.getMessage());
                    UiUtils.hideProgressDialog();
                }
            }

        });
    }
}
