package com.vogella.android.bgp;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

/**
 * This service communicates the status back via a LocalBroadcastManager
 * @author sergej
 */
public class WorkerIntentService1 extends IntentService {

	public static final String ON_STATE_ACTION = "com.vogella.bgp.WORKING_STATE";
	public static final String EXT_STATE = "state";
	
	public static final int STATE_WORKING = 1;
	public static final int STATE_DONE = 0;
	
	public WorkerIntentService1() {
		super("WorkerIntentService1");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		// notify we started to process a task
		sendState(STATE_WORKING);
		
		// pretend working
		try { Thread.sleep(Constants.WORKING_DELAY); } catch (InterruptedException e) { }
		
		sendState(STATE_DONE);
	}

	private void sendState(int state) {
		Intent stateIntent = new Intent(ON_STATE_ACTION).putExtra(EXT_STATE, state);
		LocalBroadcastManager.getInstance(this).sendBroadcast(stateIntent);
	}
	
}
