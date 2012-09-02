package com.inventitech.rateverything.utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class AlertMessagePreparer {
	public static String MESSAGE_STRING = "message";
	public static String FATAL_FLAG = "fatal";

	private static AlertMessagePreparer alert;

	private AlertMessagePreparer() {
	}

	public static AlertMessagePreparer getInstance() {
		if (alert == null) {
			alert = new AlertMessagePreparer();
		}
		return alert;
	}

	public void setHanlder(Handler handler) {
		this.handler = handler;
	}

	// The handler which handles our message in its view in its own way
	private Handler handler;

	public void createAlert(String message) {
		createAlert(message, false);
	}

	public void createAlert(String message, boolean isFatal) {
		Bundle bundle = new Bundle();
		bundle.putString(MESSAGE_STRING, message);
		bundle.putBoolean(FATAL_FLAG, isFatal);
		Message preparedMessage = new Message();
		preparedMessage.setData(bundle);
		handler.sendMessage(preparedMessage);
	}
}
