package com.skylightdeveloper.swiggymap.Util;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Akash Wangalwar on 23-09-2016.
 */
public class UiUtils {

    private static ProgressDialog progressDialog;

    public static ProgressDialog getProgressDialog(){
        return progressDialog;
    }

    public static void showProgressDialog(Context context, String message) {
        try {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(message);
            progressDialog.setCancelable(false);
			/*progressDialog
					.setProgressStyle(android.R.style.Widget_ProgressBar_Small);*/
//			progressDialog.setIndeterminate(true);

            progressDialog.show();
        } catch (Exception e) {
        }
    }

    public static void hideProgressDialog() {
        try {
            if (progressDialog.isShowing()) {
                progressDialog.cancel();
            }
        } catch (Exception e) {
        }
    }
}
