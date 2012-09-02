package com.inventitech.rateverything;

import android.os.Bundle;

import com.facebook.android.DialogError;
import com.facebook.android.FacebookError;
import com.facebook.android.Facebook.DialogListener;
import com.inventitech.rateverything.utils.AlertMessagePreparer;

/* package */class FacebookDialogListener implements DialogListener {
	private AlertMessagePreparer alertPreparer = AlertMessagePreparer
			.getInstance();

	@Override
	public void onComplete(Bundle values) {
		// successful facebook login - don't bother the user
	}

	@Override
	public void onFacebookError(FacebookError error) {
		alertPreparer
				.createAlert("Facebook Login failed (Incorrect username/password?)");

	}

	@Override
	public void onError(DialogError e) {
		alertPreparer.createAlert("Facebook Login failed (No connection?)");
	}

	@Override
	public void onCancel() {
		alertPreparer
				.createAlert(
						"Facebook Login canceled. You need to login via Facebook to use RateEverything",
						true);
	}
}