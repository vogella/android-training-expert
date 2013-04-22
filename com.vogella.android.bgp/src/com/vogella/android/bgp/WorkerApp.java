package com.vogella.android.bgp;

import com.squareup.otto.Bus;

import android.app.Application;
import android.util.Log;

/**
 * Shared application class
 * @author sergej
 */
public class WorkerApp extends Application {

	private static final String TAG = null;
	
	// shared events bus
	private Bus mBus;

	@Override
	public void onCreate() {
		super.onCreate();
		
		if (BuildConfig.DEBUG) {
			Log.d(TAG, "onCreate");
		} 
		
		mBus = new Bus();
	}
	
	public Bus getBus() {
		return mBus;
	}
	
}
