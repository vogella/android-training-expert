package com.vogella.bgp;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;
import com.squareup.otto.Produce;

/**
 * This service communicates the status back via Event Bus
 * @author sergej
 */
public class WorkerIntentService3 extends IntentService {
	
	// handler for reporting events in Main Thread
	private Handler handler = new Handler(Looper.getMainLooper());
	
	// event to be reported back
	public static class StateEvent {
		public boolean isWorking;
		public StateEvent(boolean isWorking) {
			this.isWorking = isWorking;
		}
	}
	
	// working state to be changed and read in Main Thread
	private boolean mIsWorking;
	
	public WorkerIntentService3() {
		super("WorkerIntentService3");
	}

	@Override
	public void onCreate() {
		super.onCreate();
		getBus().register(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		getBus().unregister(this);
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		// set new state and post event
		postState(true);
		
		// pretend working
		try { Thread.sleep(Constants.WORKING_DELAY); } catch (InterruptedException e) { }

		// set new state and post event
		postState(false);
	}

	private Bus getBus() {
		WorkerApp app = (WorkerApp) getApplication();
		return app.getBus();
	}
	
	private void postState(final boolean working) {
		handler.post(new Runnable() {
			@Override public void run() {
				mIsWorking = working;
				getBus().post(new StateEvent(working));
			}
		});
	}
	
	@Produce
	public StateEvent getCurrentState() {
		return new StateEvent(mIsWorking);
	}
}
