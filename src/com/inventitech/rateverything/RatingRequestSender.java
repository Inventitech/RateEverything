package com.inventitech.rateverything;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.inventitech.rateverything.json.RatingTransfer;

public class RatingRequestSender {
	public static String MESSAGE_STRING = "message";

	private static String server = "http://10.0.2.2:9000";
	private static String operation = "add";

	// The Handler of the activity
	private Handler activityHandler;

	RatingRequestSender(Handler handler) {
		this.activityHandler = handler;
	}

	public void sendRating(final RatingTransfer rating) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpClient client = new DefaultHttpClient();
				HttpConnectionParams.setConnectionTimeout(client.getParams(),
						10000);
				HttpPost post = new HttpPost(server + "/" + operation);
				try {
					post.setHeader("Accept", "application/json");
					post.setHeader("Content-type", "application/json");
					post.setEntity(new StringEntity(new Gson().toJson(rating,
							RatingTransfer.class)));

					HttpResponse response = client.execute(post);
					Log.d("RateEverything", "POST SUCCESSFUL");

				} catch (UnsupportedEncodingException e) {
					// This means that the string returned by Gson cannot be
					// converted to a String Entity. This should never happen
				} catch (ClientProtocolException e) {
					createAlert("Could not send rating (protocol problem).");
				} catch (IOException e) {
					createAlert("Could not send rating (Connection timeout).");
				}
			}
		}).start();
	}

	private void createAlert(String message) {
		Bundle bundle = new Bundle();
		bundle.putString(MESSAGE_STRING, message);
		Message preparedMessage = new Message();
		preparedMessage.setData(bundle);
		activityHandler.sendMessage(preparedMessage);
	}
}
