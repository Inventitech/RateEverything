package com.inventitech.rateverything;

import android.app.Activity;
import android.os.Bundle;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.inventitech.rateverything.utils.AlertMessagePreparer;

public class FacebookSignOn extends Activity {

	private AlertMessagePreparer alert;
	private String facebookAppId;
	private Facebook facebook;

	FacebookSignOn(AlertMessagePreparer alert, String facebookAppId) {
		this.facebookAppId = facebookAppId;
		this.alert = alert;
	}

	public void signOn() {
		facebook = new Facebook(facebookAppId);
		facebook.authorize(this, new DialogListener() {
			@Override
			public void onComplete(Bundle values) {
			}

			@Override
			public void onFacebookError(FacebookError error) {
				alert.createAlert("Facebook Login failed (Incorrect username/password?)");
			}

			@Override
			public void onError(DialogError e) {
				alert.createAlert("Facebook Login failed (No connection?)");
			}

			@Override
			public void onCancel() {
			}
		});
	}
}
