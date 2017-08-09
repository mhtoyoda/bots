package com.fiveware.util;

import com.fiveware.model.MessageBot;
import com.fiveware.model.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Scanner;

@Component
public class FileUtil {

	@Value("${io.worker.dir}")
	private String workDir;

	@Value("${io.file-read}")
	private String workerFileRead;

	static Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	@Autowired
	private LineUtil lineUtil;
	
	public Record linesFrom(InputStream file, String[] fields, String separator) throws IOException {
		String line =buildScanner(file);
		Record record = lineUtil.linesFrom(line, fields, separator);
		return record;
	}	

	private String buildScanner(InputStream file) {
		Scanner scanner = new Scanner(file);
		while (scanner.hasNextLine())
			return scanner.nextLine();

		return "";
	}

	public void writeFile(MessageBot messageBot) {

		String path = workerFileRead + File.separator + messageBot.getMessageHeader().getPathFile();

		File fileOut = new File(path);
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileOut, true))) {
			//FIXME corrigir bug para fazer append por task ID
//			messageBot.getLineResult().forEach((line)->{
//				try {
//					bw.write(line);
//					bw.newLine();
//
//				} catch (IOException e) {
//					logger.error("{}", e);
//				}
//			});
		} catch (IOException e) {
			logger.error("{}", e);
		}
	}
}