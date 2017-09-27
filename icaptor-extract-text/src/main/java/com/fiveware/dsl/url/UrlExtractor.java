package com.fiveware.dsl.url;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;

public class UrlExtractor {

	public static byte[] fromUrlToByteArray(String location) throws Exception {
		
		  URL url = new URL(location);
		  InputStream is = null;
		  byte[] bytes = null;
		  try {
		    is = url.openStream ();
		    bytes = IOUtils.toByteArray(is);
		  } catch (IOException e) {
		    //handle errors
		  }
		  finally {
		    if (is != null) is.close();
		  }
		  return bytes;
		}
	
}
