package com.vogella.android.bgp.fragments;

import com.vogella.android.bgp.R;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

public class RetainedWorkerFragment extends WorkerFragment {

	public static class HeadlessRetainedWorkerFragment extends Fragment {
		public boolean isWorking;
		
		private Runnable mProcessor = new Runnable() {
			private Handler mHander = new Handler();
			
			@Override public void run() {
				sendState(true);
				pretendToBeWorking();
				sendState(false);
			}
			
			private void sendState(final boolean working) {
				// report state in the UI thread
				mHander.post(new Runnable() {
					@Override public void run() {
						isWorking = working;
						RetainedWorkerFragment listener = 
								(RetainedWorkerFragment) getTargetFragment();
						listener.onStateChanged(working);
					}
				});
			}
		};
		
		@Override public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setRetainInstance(true);
		}
		
		public void startWork() {
			if (isWorking) return;
			new Thread(mProcessor).start();
		}
	}
	
	private HeadlessRetainedWorkerFragment mRetainedWorkerFragment;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		FragmentManager fm = getFragmentManager();
		mRetainedWorkerFragment = (HeadlessRetainedWorkerFragment) 
				fm.findFragmentByTag("worker");
		if (mRetainedWorkerFragment == null) {
			mRetainedWorkerFragment = new HeadlessRetainedWorkerFragment();
            fm.beginTransaction().add(mRetainedWorkerFragment, "worker").commit();
		}
		// tell whom to report the status
		mRetainedWorkerFragment.setTargetFragment(this, 0);
		
		// check current status
		onStateChanged(mRetainedWorkerFragment.isWorking);
	}
	
	public void onStateChanged(boolean working) {
		mProgressBar.setIndeterminate(working);
	}
	
	@Override
	public void onClick(View v) {
		mRetainedWorkerFragment.startWork();
	}

	@Override
	public int getTitleId() {
		return R.string.fragment_retained;
	}
    
}
