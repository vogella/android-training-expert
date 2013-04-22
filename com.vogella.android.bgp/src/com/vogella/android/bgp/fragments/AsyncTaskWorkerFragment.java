package com.vogella.android.bgp.fragments;

import java.lang.ref.WeakReference;

import com.vogella.android.bgp.R;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

public class AsyncTaskWorkerFragment extends WorkerFragment {

	static class WorkerTask extends AsyncTask<String, Void, Object> {
	    private final WeakReference<ProgressBar> ref;
	    private String action = null;

	    public WorkerTask(ProgressBar progressBar) {
	    		ref = new WeakReference<ProgressBar>(progressBar);
	    }

	    @Override protected Object doInBackground(String... params) {
	        action = params[0];
	        pretendToBeWorking();
	        return "executed";
	    }

	    @Override protected void onPostExecute(Object data) {
	        if (ref != null && data != null) {
	            final ProgressBar progressBar = ref.get();
	            if (progressBar != null) {
		            	progressBar.setIndeterminate(false);
		            	Log.d(TAG, "Action '" + action + "' " + data.toString());
	            }
	        }
	    }
	}
	
	@Override
	public void onClick(View v) {
		mProgressBar.setIndeterminate(true);
		new WorkerTask(mProgressBar).execute("process image");
	}

	@Override
	public int getTitleId() {
		return R.string.fragment_asynctask;
	}
    
}
