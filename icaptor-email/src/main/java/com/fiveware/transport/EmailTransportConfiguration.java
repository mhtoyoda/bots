package com.fiveware.transport;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author valdisnei
 */
public class EmailTransportConfiguration {

	private static final String PROPERTIES_FILE = "application.properties";
	private static final String KEY_SMTP_SERVER = "smtp.server";
	private static final String KEY_AUTH_REQUIRED = "auth.required";
	private static final String KEY_USE_SECURE_SMTP = "use.secure.smtp";
	private static final String KEY_USERNAME = "smtp.username";
	private static final String KEY_PASSWORD = "smtp.password";

	private static String smtpServer = "";
	private static boolean authenticationRequired = false;
	private static boolean useSecureSmtp = false;
	private static String username = null;
	private static String password = null;
	private static int  port;
	private static int socketFactoryPort;
	private static String socketFactory= "javax.net.ssl.SSLSocketFactory";
    private static Boolean fallback =false;

	static {
		Properties properties = loadProperties();

		String smtpServer = properties.getProperty(KEY_SMTP_SERVER);
		boolean authenticationRequired = Boolean
				.parseBoolean(KEY_AUTH_REQUIRED);
		boolean useSecureSmtp = Boolean.parseBoolean(KEY_USE_SECURE_SMTP);
		String username = properties.getProperty(KEY_USERNAME);
		String password = properties.getProperty(KEY_PASSWORD);

		configure(smtpServer, authenticationRequired, useSecureSmtp, username,
				password);
	}
	
	private static Properties loadProperties() {
		Properties properties = new Properties();

		InputStream inputStream = EmailTransportConfiguration.class
				.getResourceAsStream(PROPERTIES_FILE);

		if (inputStream == null) {
			inputStream = EmailTransportConfiguration.class
					.getResourceAsStream("/" + PROPERTIES_FILE);
		}

		try {
			properties.load(inputStream);
		} catch (Exception e) {
			// Properties file not found, no problem.
		}

		return properties;
	}

	public static void configure(String smtpServer,
			boolean authenticationRequired, boolean useSecureSmtp,
			String username, String password) {
		EmailTransportConfiguration.smtpServer = smtpServer;
		EmailTransportConfiguration.authenticationRequired = authenticationRequired;
		EmailTransportConfiguration.useSecureSmtp = useSecureSmtp;
		EmailTransportConfiguration.username = username;
		EmailTransportConfiguration.password = password;
	}


	public static void configure(InputStream in) {
		Properties prop = new Properties();
		try {
			prop.load(in);
			configure(prop);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void configure(Properties properties) {
		EmailTransportConfiguration.smtpServer = properties.getProperty("smtpServer");
		EmailTransportConfiguration.authenticationRequired = Boolean.valueOf(properties.getProperty("authenticationRequired"));
		EmailTransportConfiguration.useSecureSmtp = Boolean.valueOf(properties.getProperty("useSecureSmtp"));
		EmailTransportConfiguration.username =  properties.getProperty("username");
		EmailTransportConfiguration.password = properties.getProperty("password");
		EmailTransportConfiguration.port =  Integer.parseInt(properties.getProperty("mail.smtp.port"));
		EmailTransportConfiguration.socketFactoryPort = Integer.parseInt(properties.getProperty("mail.smtp.port"));
		EmailTransportConfiguration.fallback = Boolean.valueOf(properties.getProperty("mail.smtp.socketFactory.fallback"));
	}


	public String getSmtpServer() {
		return smtpServer;
	}

	public boolean isAuthenticationRequired() {
		return authenticationRequired;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public boolean useSecureSmtp() {
		return useSecureSmtp;
	}

}
