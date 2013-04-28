package com.example.android.rssfeed;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class RssFeedDetailFragment extends Fragment {

	private WebView webview;

	public static RssFeedDetailFragment instantiate(String rssItemUrl) {
		// create instance
		RssFeedDetailFragment fragment = new RssFeedDetailFragment();
		
		// initialize and store input argument
		if (rssItemUrl != null) {
			Bundle args = new Bundle();
			args.putString("url", rssItemUrl);
			fragment.setArguments(args);
		}
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
			ViewGroup container,
			Bundle savedInstanceState) {
		webview = new WebView(getActivity());
		Bundle attrs = getArguments();
		if (attrs != null) {
			String url = attrs.getString("url");
			setUrl(url);
		}
		return webview;
	}

	public void setUrl(String url) {
		if (webview == null) {
			getArguments().putString("url", url);
		} else {
			webview.setInitialScale(50);
			webview.loadUrl(url);
		}
	}
}