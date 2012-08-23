package com.inventitech.rateverything;

import android.content.Context;
import android.content.Intent;

import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {

	@Override
	protected void onError(Context arg0, String errorId) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void onMessage(Context context, Intent arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onRegistered(Context context, String arg1) {
		// TODO Auto-generated method stub
		// TODO (MMB) send regId to server and regsiter
	}

	@Override
	protected void onUnregistered(Context context, String regId) {
		// TODO Auto-generated method stub
		// TODO (MMB) send regId to server and unregsiter


	}

}
