package com.inventitech.rateverything;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.inventitech.rateverything.utils.AlertMessagePreparer;

public class FacebookSignOnActivity extends Activity {

	private AlertMessagePreparer alert;
	private String facebookAppId;
	private Facebook facebook;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		this.facebookAppId = getString(R.string.facebook_app_id);
		alert = AlertMessagePreparer.getInstance();

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

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		facebook.authorizeCallback(requestCode, resultCode, data);
	}
}
