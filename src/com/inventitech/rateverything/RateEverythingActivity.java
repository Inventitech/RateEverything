package com.inventitech.rateverything;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.facebook.android.Facebook;
import com.inventitech.rateverything.json.RatingTransfer;
import com.inventitech.rateverything.json.RatingTransfer.RATING;
import com.inventitech.rateverything.utils.AlertMessagePreparer;

public class RateEverythingActivity extends Activity {
	public static String RATING_STRING_EXTRA = "com.inventitech.rateverything.rating";

	static class AlertHandler extends Handler {
		WeakReference<RateEverythingActivity> referencedActivity;

		public AlertHandler(RateEverythingActivity activity) {
			this.referencedActivity = new WeakReference<RateEverythingActivity>(
					activity);
		}

		@Override
		public void handleMessage(Message msg) {
			referencedActivity.get().displayAlert(
					(String) msg.getData().get(
							AlertMessagePreparer.MESSAGE_STRING),
					(Boolean) msg.getData()
							.get(AlertMessagePreparer.FATAL_FLAG));
		}
	};

	static class GCMAvailableHandler extends Handler {
		WeakReference<RateEverythingActivity> referencedActivity;

		public GCMAvailableHandler(RateEverythingActivity activity) {
			this.referencedActivity = new WeakReference<RateEverythingActivity>(
					activity);
		}

		public void handleMessage(Message msg) {
			// We wait for the gcm available message because we don't want the
			// user to input her facebook credentials only to find out later
			// that GCM isn't available
			referencedActivity.get().setupFacebook();
		}
	}

	/* package */AlertHandler alertHandler = new AlertHandler(this);
	/* package */GCMAvailableHandler gcmHandler = new GCMAvailableHandler(this);

	/* package */boolean gcmAvailable = false;

	private AlertMessagePreparer alertPreparer = AlertMessagePreparer
			.getInstance();

	private RatingTransferSender ratingSender = new RatingTransferSender(
			alertPreparer);

	private Facebook facebook;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		alertPreparer.setHanlder(alertHandler);

		// Start GCM Thread
		new GCMThread(this).start();
		setContentView(R.layout.main);
	}

	public void setupFacebook() {
		facebook = new Facebook(getString(R.string.facebook_app_id));
		facebook.authorize(this, new FacebookDialogListener());
	}

	public void onClickedRatingButton(View view) {
		RATING rating = null;
		switch (view.getId()) {
		case R.id.button10:
			new AlertDialog.Builder(view.getContext())
					.setMessage("Art thou sure to rate 10?")
					.setPositiveButton("Yes", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							ratingSender.sendRating(new RatingTransfer(
									RATING.RATING_10));
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

	private void displayAlert(String message, boolean fatal) {
		OnClickListener listener = null;
		if (fatal) {
			listener = new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			};
		}
		AlertDialog alert = new AlertDialog.Builder(this).setNegativeButton(
				"OK", listener).create();

		alert.setMessage(message);
		alert.setCanceledOnTouchOutside(true);
		alert.show();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		facebook.authorizeCallback(requestCode, resultCode, data);
	}
}