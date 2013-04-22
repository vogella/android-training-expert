package com.vogella.android.bgp.fragments;


import android.content.Intent;
import android.view.View;

import com.squareup.otto.Subscribe;
import com.vogella.android.bgp.R;
import com.vogella.android.bgp.WorkerIntentService3;
import com.vogella.android.bgp.WorkerIntentService3.StateEvent;

/**
 * Service communicates with the Fragment using Event Bus (Otto)
 * @author sergej
 */
public class IntentServiceWorkerFragment3 extends WorkerFragment {

	@Override
	public void onResume() {
		super.onResume();
		getBus().register(this);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		getBus().unregister(this);
	}
	
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(getActivity(), WorkerIntentService3.class);
		getActivity().startService(intent);
	}
    
	@Subscribe
	public void onStateChanged(StateEvent event) {
		mProgressBar.setIndeterminate(event.isWorking);
	}

	@Override
	public int getTitleId() {
		return R.string.fragment_service_bus;
	}
}
