package com.inventitech.rateverything;

import java.lang.ref.WeakReference;

import android.util.Log;

import com.google.android.gcm.GCMRegistrar;
import com.inventitech.rateverything.utils.AlertMessagePreparer;

/* package */class GCMThread extends Thread {
	public GCMThread(RateEverythingActivity mainActivity) {
		this.referencedActivity = new WeakReference<RateEverythingActivity>(
				mainActivity);
	}

	private WeakReference<RateEverythingActivity> referencedActivity;

	@Override
	public void run() {
		RateEverythingActivity mainActivity = referencedActivity.get();
		try {
			GCMRegistrar.checkDevice(mainActivity);
		} catch (UnsupportedOperationException e) {
			AlertMessagePreparer
					.getInstance()
					.createAlert(
							"Google's GCM API couldn't be reached. This normally means you don't use Google Services, so please enable them in order to RateEverything.",
							true);
			return;
		}
		// signals that GCM setup was successful
		mainActivity.gcmHandler.sendEmptyMessage(0);
		GCMRegistrar.checkManifest(mainActivity);
		final String regId = GCMRegistrar.getRegistrationId(mainActivity);
		if (regId.equals("")) {
			GCMRegistrar.register(mainActivity,
					mainActivity.getString(R.string.google_project_id));
		} else {
			Log.v("RateEverything", "Already registered");
		}

	}
}
