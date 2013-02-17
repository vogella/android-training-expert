package com.vogella.bgp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.otto.Bus;
import com.vogella.bgp.Constants;
import com.vogella.bgp.R;
import com.vogella.bgp.WorkerApp;

public abstract class WorkerFragment extends Fragment implements OnClickListener {

	protected static final String TAG = "WorkerFragment";
	protected ProgressBar mProgressBar;

	protected static void pretendToBeWorking() {
		try {
			Thread.sleep(Constants.WORKING_DELAY);
		} catch (InterruptedException e) {
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// inflate view
		View view = inflater.inflate(R.layout.fragment_worker, container, false);
		// set title
		TextView title = (TextView) view.findViewById(R.id.textView1); 
		title.setText(getTitleId());
		// set button listener
		view.findViewById(R.id.button1).setOnClickListener(this);
		// store progress instance
		mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
		return view;
	}
	
	@Override
	public void onStop() {
		super.onStop();
		Log.d(TAG, getClass().getSimpleName() + " stopped");
	}
	
	@Override
	public void onStart() {
		super.onStart();
		Log.d(TAG, getClass().getSimpleName() + " started");
	}
	
	// shortcut for getting Bus instance
	protected Bus getBus() {
		WorkerApp app = (WorkerApp) getActivity().getApplication(); 
		return app.getBus();
	}

	public abstract int getTitleId();
}
