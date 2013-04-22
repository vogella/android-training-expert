package com.vogella.android.bgp;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.ResultReceiver;

/**
 * This service communicates the status back via a ResultReceiver
 * @author sergej
 */
public class WorkerIntentService4 extends IntentService {

	public static final int RESULT_OK = 0;
	public static final String EXT_STATE = "state";
	
	public static final int STATE_WORKING = 1;
	public static final int STATE_DONE = 0;
	
	public WorkerIntentService4() {
		super("WorkerIntentService4");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// send initial state
		sendState(intent, STATE_WORKING);
		// pretend working
		try { Thread.sleep(Constants.WORKING_DELAY); } catch (InterruptedException e) { }
		// send final state
		sendState(intent, STATE_DONE);
	}

	private void sendState(Intent intent, int state) {
		ResultReceiver receiver = intent.getParcelableExtra("result");
		Bundle bundle = new Bundle();
		bundle.putBoolean(EXT_STATE, state == STATE_WORKING);
		receiver.send(RESULT_OK, bundle);
	}
	
}
