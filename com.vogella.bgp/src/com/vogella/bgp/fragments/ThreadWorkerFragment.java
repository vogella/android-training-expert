package com.vogella.bgp.fragments;

import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.vogella.bgp.R;

public class ThreadWorkerFragment extends WorkerFragment {

	private Handler mHandler = new Handler(); 
	
	@Override
	public void onClick(View v) {
		mProgressBar.setIndeterminate(true);
		
		new Thread(new Runnable() {
			@Override public void run() {
				pretendToBeWorking();
				
				//getActivity().getWindow().getDecorView().getHandler();
				
				mProgressBar.post(new Runnable() {
					@Override public void run() {
						mProgressBar.setIndeterminate(false);
					}
				});
				
				/*
				mHandler.post(new Runnable() {
					@Override public void run() {
						mProgressBar.setIndeterminate(false);
					}
				});
				*/
			}
		}).start();
		
	}

	@Override
	public int getTitleId() {
		return R.string.fragment_threadworker;
	}
	
}
