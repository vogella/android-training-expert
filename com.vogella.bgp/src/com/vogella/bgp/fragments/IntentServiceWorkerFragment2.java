package com.vogella.bgp.fragments;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.view.View;

import com.vogella.bgp.R;
import com.vogella.bgp.WorkerIntentService2;

/**
 * Service communicates with the Fragment using Messenger
 * @author sergej
 */
public class IntentServiceWorkerFragment2 extends WorkerFragment {

	private Handler mHandler = new Handler(new Handler.Callback() {
		@Override public boolean handleMessage(Message msg) {
			mProgressBar.setIndeterminate(msg.arg1 == WorkerIntentService2.STATE_WORKING);
			return true;
		}
	});
	
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(getActivity(), WorkerIntentService2.class);
		intent.putExtra("messenger", new Messenger(mHandler));
		getActivity().startService(intent);
	}

	@Override
	public int getTitleId() {
		return R.string.fragment_service_msg;
	}
    
}
