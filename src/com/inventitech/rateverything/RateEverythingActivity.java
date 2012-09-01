package com.inventitech.rateverything;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.google.android.gcm.GCMRegistrar;
import com.inventitech.rateverything.json.RatingTransfer;
import com.inventitech.rateverything.json.RatingTransfer.RATING;
import com.inventitech.rateverything.utils.AlertMessagePreparer;

public class RateEverythingActivity extends Activity {
	public static String RATING_STRING_EXTRA = "com.inventitech.rateverything.rating";

	private Handler alertHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			displayAlert((String) msg.getData().get(
					AlertMessagePreparer.MESSAGE_STRING));
		}
	};

	private AlertMessagePreparer alert = AlertMessagePreparer.getInstance();

	private RatingTransferSender ratingSender = new RatingTransferSender(alert);

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			GCMRegistrar.checkDevice(this);
		} catch (UnsupportedOperationException e) {
			displayAlert("Please install Android GCM service. This typically means you don't use");
			return;
		}
		alert.setHanlder(alertHandler);
		Intent facebookSignOnIntent = new Intent(this,
				FacebookSignOnActivity.class);
		startActivity(facebookSignOnIntent);
		GCMRegistrar.checkManifest(this);
		final String regId = GCMRegistrar.getRegistrationId(this);
		if (regId.equals("")) {
			GCMRegistrar.register(this, getString(R.string.google_project_id));
		} else {
			Log.v("RateEverything", "Already registered");
		}
		setContentView(R.layout.main);
	}

	public void onClickedRatingButton(View view) {
		RATING rating = RATING.RATING_XD;
		switch (view.getId()) {
		case R.id.button10:
			new AlertDialog.Builder(view.getContext())
					.setMessage("Art thou sure to rate 10?")
					.setPositiveButton("Yes", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							ratingSender.sendRating(new RatingTransfer(
									RATING.RATING_10));
							;
						}
					}).setNegativeButton("No", null).show();
			return;
		case R.id.button9:
			rating = RATING.RATING_9;
			break;
		case R.id.button8:
			rating = RATING.RATING_8;
			break;
		case R.id.button7:
			rating = RATING.RATING_7;
			break;
		case R.id.button6:
			rating = RATING.RATING_6;
			break;
		case R.id.button5:
			rating = RATING.RATING_5;
			break;
		case R.id.buttonxD:
			rating = RATING.RATING_XD;
			break;

		}
		ratingSender.sendRating(new RatingTransfer(rating));
	}

	private void displayAlert(String message) {
		AlertDialog alert = new AlertDialog.Builder(this).setNegativeButton(
				"OK", null).create();
		alert.setMessage(message);
		alert.setCanceledOnTouchOutside(true);
		alert.show();
	}
}