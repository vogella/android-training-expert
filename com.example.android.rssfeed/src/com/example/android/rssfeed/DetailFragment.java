package com.example.android.rssfeed;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailFragment extends Fragment {
	private String item;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_rssitem_detail,
				container, false);
		setText(item);
		return view;
	}

	public void setText(String item) {
		this.item = item;
		TextView view = (TextView) getView().findViewById(R.id.detailsText);
		view.setText(item);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// Save the text currently displayed
		outState.putString("curChoice", item);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}