package com.vogella.bgp.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vogella.bgp.R;

public class TimerFragment extends Fragment 
	implements OnClickListener, Runnable {

	private TextView mTimerView;
	private int mSeconds;
	
	private Handler mHandler = new Handler();
	private boolean stopped;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_timer, container, false);

		// find counter text view
		mTimerView = (TextView) view.findViewById(R.id.textView2);
		
		// set button listener
		view.findViewById(R.id.button1).setOnClickListener(this);
		
		return view;
	}

	@Override
	public void onClick(View v) {
		if (stopped) mHandler.postDelayed(this, 1000);
	}

	@Override
	public void run() {
		mSeconds++;
		mTimerView.setText(String.valueOf(mSeconds));
		if (stopped == false) mHandler.postDelayed(this, 1000);
	}
	
}
