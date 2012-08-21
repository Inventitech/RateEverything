package com.inventitech.rateverything;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

public class RateEverythingActivity extends Activity {
	public static String RATING_STRING_EXTRA = "com.inventitech.rateverything.rating";

	public static int inst = 0;

	private Facebook facebook;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		changeRating();
		facebook = new Facebook(getString(R.string.facebook_app_id));
		facebook.authorize(this, new DialogListener() {
			@Override
			public void onComplete(Bundle values) {
			}

			@Override
			public void onFacebookError(FacebookError error) {
			}

			@Override
			public void onError(DialogError e) {
			}

			@Override
			public void onCancel() {
			}
		});
	}

	public void clicked10(View view) {
		new AlertDialog.Builder(view.getContext())
				.setMessage("Art thou sure to rate 10?")
				.setPositiveButton("Yes", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						sendRating("10");
					}
				}).setNegativeButton("No", null).show();
	}

	public void clicked9(View view) {
		sendRating("9");
	}

	public void clicked8(View view) {
		sendRating("8");
	}

	public void clicked7(View view) {
		sendRating("7");
	}

	public void clicked6(View view) {
		sendRating("6");
	}

	public void clicked5(View view) {
		sendRating("5");
	}

	public void clickedXD(View view) {
		sendRating("xD");
	}

	public void sendRating(String rating) {
		Intent intent = new Intent(this, RateEverythingActivity.class);
		intent.putExtra(RATING_STRING_EXTRA, rating);
		startActivity(intent);
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
}