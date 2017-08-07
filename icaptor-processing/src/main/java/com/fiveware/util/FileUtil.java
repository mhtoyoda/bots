package com.fiveware.util;

import com.fiveware.model.MessageBot;
import com.fiveware.model.Record;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;
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
	
	public List<Record> linesFrom(InputStream file, String[] fields, String separator) throws IOException {
		List<String> linhas = Lists.newArrayList();

		buildScanner(file, linhas);

		List<Record> lines = lineUtil.getLines(linhas, fields, separator);
		return lines;
	}	

	private void buildScanner(InputStream file, List<String> linhas) {

		Scanner scanner = new Scanner(file);

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			linhas.add(line);
		}
		scanner.close();
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