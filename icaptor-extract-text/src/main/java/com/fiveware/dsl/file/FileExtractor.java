package com.fiveware.dsl.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;

public class FileExtractor {
	
	public static byte[] fromFileToByteArray(String location) throws Exception {
		
		  
		  FileInputStream is = new FileInputStream(new File(location));
		  byte[] bytes = null;
		  try {
		 
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
