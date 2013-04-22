package com.vogella.android.imagegrid;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vogella.android.imagegrid.sampledata.Images;

public class MyListFragment extends ListFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_rsslist_overview,
				container, false);
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		MyPerformanceArrayAdapter adapter = new MyPerformanceArrayAdapter(
				getActivity(), Images.imageThumbUrls);
		setListAdapter(adapter);
	}
}
