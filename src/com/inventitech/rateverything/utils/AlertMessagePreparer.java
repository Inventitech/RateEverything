package com.inventitech.rateverything.utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class AlertMessagePreparer {
	public static String MESSAGE_STRING = "message";

	public AlertMessagePreparer(Handler handler) {
		this.handler = handler;
	}

	// The handler which handles our message in its view in its own way
	private Handler handler;

	public void createAlert(String message) {
		Bundle bundle = new Bundle();
		bundle.putString(MESSAGE_STRING, message);
		Message preparedMessage = new Message();
		preparedMessage.setData(bundle);
		handler.sendMessage(preparedMessage);
	}
}
