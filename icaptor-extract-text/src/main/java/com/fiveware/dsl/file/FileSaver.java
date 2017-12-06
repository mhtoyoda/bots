package com.fiveware.dsl.file;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.apache.commons.lang3.SerializationUtils;

public class FileSaver {

	public static void saveFile(String content, String filename) throws IOException {

		PrintStream out = new PrintStream(new FileOutputStream(filename));

		try {

			out.print(content);

		} finally {
			out.close();
		}
	}

	public static void saveFile(byte[] content, String filename) throws IOException {
		FileOutputStream fos = new FileOutputStream(filename);
		try {
			fos.write(content);
		} finally {
			fos.close();
		}
	}

	

}
