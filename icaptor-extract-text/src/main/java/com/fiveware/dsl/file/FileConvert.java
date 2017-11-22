package com.fiveware.dsl.file;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FileConvert {

	static Logger logger = LoggerFactory.getLogger(FileConvert.class);
	
	public byte[] readByte(byte[] bytes, String nameFile, String typeFile) throws IOException{
		typeFile = typeFile.toLowerCase();		
		String pathFile = nameFile+"."+typeFile;
		File file = new File(pathFile);		
		FileInputStream fileInputStream = new FileInputStream(file);
		fileInputStream.read(bytes);
		fileInputStream.close();
        return bytes;		
	}
	
	@SuppressWarnings("resource")
	public byte[] generateByteToFile(String path) throws IOException{
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
        byte[] buf = new byte[1024];
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
}