package com.vogella.android.bgp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.view.View;

import com.vogella.android.bgp.R;
import com.vogella.android.bgp.WorkerIntentService4;

/**
 * Service communicates with the Fragment using ResultReceiver
 * @author sergej
 */
public class IntentServiceWorkerFragment4 extends WorkerFragment {

	private ResultReceiver mResultReceiver = new ResultReceiver(new Handler()) {
		protected void onReceiveResult(int resultCode, Bundle resultData) {
			if (resultCode == WorkerIntentService4.RESULT_OK) {
				boolean working = resultData.getBoolean(WorkerIntentService4.EXT_STATE);
				mProgressBar.setIndeterminate(working);
			}
		};
	};
	
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(getActivity(), WorkerIntentService4.class);
		intent.putExtra("result", mResultReceiver);
		getActivity().startService(intent);
	}

	@Override
	public int getTitleId() {
		return R.string.fragment_service_res;
	}
    
}
