package com.vogella.android.bgp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.View;

import com.vogella.android.bgp.R;

public class AsyncTaskLoaderFragment extends WorkerFragment 
	implements Loader.OnLoadCompleteListener<Object> {

	public static class WorkerLoader extends AsyncTaskLoader<Object> {
		public OnLoadCompleteListener<Object> listener;
		public Object result;
		public boolean working;

		public WorkerLoader(Context context) {
			super(context);
		}

		@Override public Object loadInBackground() {
			pretendToBeWorking();
			return "Data";
		}

		@Override public void deliverResult(Object data) {
			working = false;
			result = data;
			if (listener != null) listener.onLoadComplete(this, data);
			super.deliverResult(data);
		}

		@Override public void forceLoad() {
			working = true;
			result = null;
			super.forceLoad();
		}
	}

	private static final int LOADER_ID = 0;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		final LoaderManager loaderManager = getLoaderManager();
		loaderManager.initLoader(LOADER_ID, savedInstanceState, 
			new LoaderCallbacks<Object>() {
				@Override
				public Loader<Object> onCreateLoader(int id, Bundle bundle) {
					return new WorkerLoader(getActivity());
				}
				@Override
				public void onLoadFinished(Loader<Object> loader, Object result) { }
				@Override
				public void onLoaderReset(Loader<Object> arg0) { }
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		WorkerLoader loader = getWorkerLoader();
		
		// register this fragment as a current listener
		loader.listener = this;
		// update UI with the current loader state
		onStateChanged(loader.working);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		// remove listener
		getWorkerLoader().listener = null;
	}
	
	@Override
	public void onLoadComplete(Loader<Object> loader, Object result) {
		onStateChanged(false);
	}
	
	protected void onStateChanged(boolean working) {
		mProgressBar.setIndeterminate(working);
	}

	@Override
	public void onClick(View v) {
		mProgressBar.setIndeterminate(true);
		final LoaderManager loaderManager = getLoaderManager();
		loaderManager.getLoader(LOADER_ID).forceLoad();
	}

	@Override
	public int getTitleId() {
		return R.string.fragment_asynctaskloader;
	}
	
	private WorkerLoader getWorkerLoader() {
		return (WorkerLoader) getLoaderManager().getLoader(LOADER_ID);
	}
}
