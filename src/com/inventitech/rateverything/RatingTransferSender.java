package com.inventitech.rateverything;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

import android.util.Log;

import com.google.gson.Gson;
import com.inventitech.rateverything.json.RatingTransfer;
import com.inventitech.rateverything.utils.AlertMessagePreparer;

public class RatingTransferSender {

	private static String server = "http://10.0.2.2:9000";
	private static String operation = "add";

	private AlertMessagePreparer alert;

	public RatingTransferSender(AlertMessagePreparer alert) {
		this.alert = alert;
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
					if (response.getStatusLine().getStatusCode() != 200) {
						alert.createAlert("Could not save rating (Server problem).");
					}
					Log.d("RateEverything", "POST SUCCESSFUL");

				} catch (UnsupportedEncodingException e) {
					// This means that the string returned by Gson cannot be
					// converted to a String Entity. This should never happen
				} catch (ClientProtocolException e) {
					alert.createAlert("Could not send rating (Protocol problem).");
				} catch (IOException e) {
					alert.createAlert("Could not send rating (Connection timeout).");
				}
			}
		}).start();
	}
}
