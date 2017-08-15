package com.fiveware.automate;

import java.net.HttpURLConnection;
import java.net.URL;

import com.fiveware.exception.RuntimeBotException;

public class BotValidation {

	public static void verifyUrl(String URLName) throws RuntimeBotException {
		try {
			HttpURLConnection.setFollowRedirects(false);
			HttpURLConnection con = (HttpURLConnection) new URL(URLName).openConnection();
			con.setRequestMethod("HEAD");
			if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
				String msg = String.format("Erro Status Code %d - Msg: {%s}", con.getResponseCode(), con.getResponseMessage());
				throw new RuntimeBotException(msg);
			}
		} catch (Exception e) {
			throw new RuntimeBotException("Url Error: "+e);
		}
	}
}
