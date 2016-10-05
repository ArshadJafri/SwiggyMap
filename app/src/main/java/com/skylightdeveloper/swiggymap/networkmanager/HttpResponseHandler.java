package com.skylightdeveloper.swiggymap.networkmanager;

import android.content.Context;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.skylightdeveloper.swiggymap.Util.UiUtils;

public class HttpResponseHandler extends AsyncHttpResponseHandler {

	private boolean abort;
	private Context mContext;

	public boolean isAbort() {
		return abort;
	}

	public void setAbort(boolean abort) {
		this.abort = abort;
	}

	public HttpResponseHandler(Context mContext) {
		super();
		this.mContext = mContext;
	}
	public HttpResponseHandler() {
		super();	
	}
	@Override
	public void onFailure(Throwable e, String message) {
		super.onFailure(e, message);
		UiUtils.hideProgressDialog();
		if(mContext!=null) {
			if (!IsNetworkAvailable.hasConnection(mContext)){
				Toast.makeText(mContext, "No Internet connection", Toast.LENGTH_LONG).show();
			}
		}

	}

}
