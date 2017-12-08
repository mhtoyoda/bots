package com.fiveware.dsl.file;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FileExtractUtil {

	static Logger logger = LoggerFactory.getLogger(FileExtractUtil.class);

	public byte[] fileToByteArray(String filePath) {
		byte[] fileByteArray = null;
		try {
			fileByteArray = Files.readAllBytes(Paths.get(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileByteArray;
	}

	@SuppressWarnings("resource")
	public byte[] generateByteToFile(String path) throws IOException {
		logger.info("Generate byte to path {}", path);
		File file = new File(path);
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
			throw e;
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buf = new byte[4096];
		try {
			for (int readNum; (readNum = fileInputStream.read(buf)) != -1;) {
				bos.write(buf, 0, readNum);
			}
		} catch (IOException ex) {
			logger.error(ex.getMessage());
			throw ex;
		}
		byte[] bytes = bos.toByteArray();
		return bytes;
	}

	public byte[] createZipFile(Map<String, List<byte[]>> files) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		BufferedInputStream source = null;
		InputStream streamInput = null;
		ZipOutputStream out = null;
		ZipEntry entry = null;

		try {
			out = new ZipOutputStream(byteArrayOutputStream);
			out.setLevel(Deflater.DEFAULT_COMPRESSION);
			Iterator<Entry<String, List<byte[]>>> iterator = files.entrySet().iterator();
			while(iterator.hasNext()){
				int index = 0;
				Entry<String, List<byte[]>> next = iterator.next();
				String nameFile = next.getKey();
				List<byte[]> filesBytes = next.getValue();
				for (byte[] bs : filesBytes) {
					streamInput = new ByteArrayInputStream(bs);
					source = new BufferedInputStream(streamInput, 4096);
					entry = new ZipEntry(StringUtils.replaceFirst(nameFile, "\\.", "_"+index+"."));
					entry.setSize(bs.length);
					out.putNextEntry(entry);
					out.write(bs);
					index++;
				}
			}						
		} catch (IOException e) {
			throw new IOException(e.getMessage());
		} finally{
			if(null != source){
				source.close();
			}
			if(null != out){
				out.close();
			}
		}
		return byteArrayOutputStream.toByteArray();
	}
	
	public File transformByteToFile(byte[] bytes, String nameFile) throws IOException{		
		File file = new File(nameFile);		
		FileOutputStream out = new FileOutputStream(file);
		out.write(bytes);
		out.close();		
		return file;
	}
	
	public byte[] unzip(byte[] file) throws Exception {
		ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(file);
		
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