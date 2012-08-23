package com.inventitech.rateverything;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.facebook.android.Facebook;
import com.inventitech.rateverything.json.RatingTransfer;
import com.inventitech.rateverything.json.RatingTransfer.RATING;
import com.inventitech.rateverything.utils.AlertMessagePreparer;

public class RateEverythingActivity extends Activity {
	public static String RATING_STRING_EXTRA = "com.inventitech.rateverything.rating";

	private Facebook facebook;

	private Handler alertHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			displayAlert((String) msg.getData().get(
					AlertMessagePreparer.MESSAGE_STRING));
		}
	};

	private AlertMessagePreparer alert = new AlertMessagePreparer(alertHandler);

	private RatingRequestSender ratingSender = new RatingRequestSender(alert);

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		changeRating();

	}

	public void clicked10(View view) {
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
	}

	public void clicked9(View view) {
		ratingSender.sendRating(new RatingTransfer(RATING.RATING_9));
	}

	public void clicked8(View view) {
		ratingSender.sendRating(new RatingTransfer(RATING.RATING_8));
	}

	public void clicked7(View view) {
		ratingSender.sendRating(new RatingTransfer(RATING.RATING_7));
	}

	public void clicked6(View view) {
		ratingSender.sendRating(new RatingTransfer(RATING.RATING_6));
	}

	public void clicked5(View view) {
		ratingSender.sendRating(new RatingTransfer(RATING.RATING_5));
	}

	public void clickedXD(View view) {
		ratingSender.sendRating(new RatingTransfer(RATING.RATING_XD));
	}

	private void changeRating() {
		String rating = getIntent().getStringExtra(RATING_STRING_EXTRA);
		TextView ratingTextView = (TextView) getWindow().findViewById(
				R.id.rating);
		ratingTextView.setText(rating);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		facebook.authorizeCallback(requestCode, resultCode, data);
	}

	private void displayAlert(String message) {
		AlertDialog alert = new AlertDialog.Builder(this).setNegativeButton(
				"OK", null).create();
		alert.setMessage(message);
		alert.setCanceledOnTouchOutside(true);
		alert.show();
	}
}