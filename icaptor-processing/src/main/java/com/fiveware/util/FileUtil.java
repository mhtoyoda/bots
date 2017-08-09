package com.fiveware.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fiveware.model.MessageBot;
import com.fiveware.model.Record;

@Component
public class FileUtil {

	@Value("${io.worker.dir}")
	private String workDir;

	@Value("${io.file-read}")
	private String workerFileRead;

	static Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	@Autowired
	private LineUtil lineUtil;
	
	public List<String> linesFrom(InputStream file){
		List<String> linhas = Lists.newArrayList();
		buildLines(file, linhas);
		return linhas;
	}
	
	public List<Record> linesFrom(List<String> lines, String[] fields, String separator) throws IOException {
		List<Record> lineRecords = lineUtil.getLines(lines, fields, separator);
		return lineRecords;
	}	

	private void buildLines(InputStream file, List<String> linhas) {
		Scanner scanner = new Scanner(file);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			linhas.add(line);
		}
		scanner.close();
	}	

	public Record linesFrom(InputStream file, String[] fields, String separator) throws IOException {
		String line = buildScanner(file);
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