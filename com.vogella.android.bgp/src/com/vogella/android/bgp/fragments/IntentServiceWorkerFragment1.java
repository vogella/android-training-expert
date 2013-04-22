package com.vogella.android.bgp.fragments;

import static com.vogella.android.bgp.WorkerIntentService1.*;

import com.vogella.android.bgp.R;
import com.vogella.android.bgp.WorkerIntentService1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;

/**
 * Service communicates with this fragment using 
 * LocalBroadcastReceiver
 * @author sergej
 */
public class IntentServiceWorkerFragment1 extends WorkerFragment {

	BroadcastReceiver mStateBroadcastReceiver = new BroadcastReceiver() {
		@Override public void onReceive(Context context, Intent intent) {
			int status = intent.getIntExtra(EXT_STATE, STATE_DONE);
			mProgressBar.setIndeterminate(status == STATE_WORKING);
			Log.d(TAG, "State received: " + (status == STATE_WORKING ? "working" : "done"));
		}
	};

	@Override
	public void onResume() {
		super.onResume();
		IntentFilter filter = new IntentFilter(ON_STATE_ACTION);
		LocalBroadcastManager mgr = LocalBroadcastManager.getInstance(getActivity()); 
		mgr.registerReceiver(mStateBroadcastReceiver, filter);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		LocalBroadcastManager mgr = LocalBroadcastManager.getInstance(getActivity()); 
		mgr.unregisterReceiver(mStateBroadcastReceiver);
	}
	
	@Override
	public void onClick(View v) {
		getActivity().startService(new Intent(getActivity(), WorkerIntentService1.class));
	}

	@Override
	public int getTitleId() {
		return R.string.fragment_service_lbr;
	}
    
}
