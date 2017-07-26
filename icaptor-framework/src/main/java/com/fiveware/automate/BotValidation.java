package com.fiveware.automate;

import java.net.HttpURLConnection;
import java.net.URL;

import com.fiveware.exception.ExceptionBot;

public class BotValidation {

	public static void verifyUrl(String URLName) throws ExceptionBot {
		try {
			HttpURLConnection.setFollowRedirects(false);
			HttpURLConnection con = (HttpURLConnection) new URL(URLName).openConnection();
			con.setRequestMethod("HEAD");
			if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
				String msg = String.format("Erro Status Code %d - Msg: {%s}", con.getResponseCode(), con.getResponseMessage());
				throw new ExceptionBot(msg);
			}
		} catch (Exception e) {
			throw new ExceptionBot("Url Error: "+e);
		}
	}
}
