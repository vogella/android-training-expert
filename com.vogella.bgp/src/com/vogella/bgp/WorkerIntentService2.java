package com.vogella.bgp;

import android.app.IntentService;
import android.content.Intent;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

/**
 * This service communicates the status back via a Messenger
 * @author sergej
 */
public class WorkerIntentService2 extends IntentService {

	public static final int STATE_WORKING = 1;
	public static final int STATE_DONE = 0;
	
	public WorkerIntentService2() {
		super("WorkerIntentService2");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// notify working
		sendState(intent, STATE_WORKING);
		// pretend working
		try { Thread.sleep(Constants.WORKING_DELAY); } catch (InterruptedException e) { }
		// notify done
		sendState(intent, STATE_DONE);
	}

	private void sendState(Intent intent, int state) {
		Messenger medium = intent.getParcelableExtra("messanger");
		Message message = Message.obtain();
		message.arg1 = state;
		try {
			medium.send(message);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
	}
	
}
