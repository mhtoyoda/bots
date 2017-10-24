package com.fiveware.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Zip {

	public static byte[] zipBytes(String filename, byte[] input) throws IOException {
		// I'll try no error page at any causes. 
		if(input == null){
			input = new byte[2];
			input[0] = 'n';
			input[1] = 'o';
		}
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(baos);
		ZipEntry entry = new ZipEntry(filename);
		entry.setSize(input.length);
		zos.putNextEntry(entry);
		zos.write(input);
		zos.closeEntry();
		zos.close();
		return baos.toByteArray();
	}
	
	public static byte[] zipBytes(List<String> filename, List<byte[]> input) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(baos);
		
		for (int i = 0; i < input.size(); i++) {
			ZipEntry entry = new ZipEntry(filename.get(i));
			entry.setSize(input.get(i).length);
			zos.putNextEntry(entry);
			zos.write(input.get(i));
			zos.closeEntry();
		}
		
		zos.close();
		return baos.toByteArray();
	}
	public static byte[] zipBytes(String filename, List<byte[]> input) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(baos);

		for (int i = 0; i < input.size(); i++) {
			ZipEntry entry = new ZipEntry(filename+"_"+i+".txt");
			entry.setSize(input.get(i).length);
			zos.putNextEntry(entry);
			zos.write(input.get(i));
			zos.closeEntry();
		}

		zos.close();
		return baos.toByteArray();
	}
	
	public static boolean isZipFile(byte[] file) {
		
		if (file == null || file.length < 2)
			return false;
		
		byte[] auxHeader = new byte[2];
		auxHeader[0] = file[0];
		auxHeader[1] = file[1];
		
		
		String inicio = new String(auxHeader);
		
		if (inicio.equals("PK"))
			return true;
		
		return false;
	}
	
	public static byte[] unzip(byte[] arquivoZipado) throws Exception {
		ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(arquivoZipado);
		
		ZipInputStream zipInputStream = new ZipInputStream(arrayInputStream);
		ZipEntry zipEntry = zipInputStream.getNextEntry();
		BufferedInputStream bufferedInputStream = new BufferedInputStream(zipInputStream);

		if (zipEntry != null) {
			byte[] buffer = new byte[5000000];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        int len;
	        while ((len = bufferedInputStream.read(buffer)) > 0) {
	        	baos.write(buffer, 0, len);
	        }
	        return baos.toByteArray();
		}
		
		return null;
	}

}
