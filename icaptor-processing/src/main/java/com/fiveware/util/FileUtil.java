package com.fiveware.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fiveware.model.MessageBot;
import com.fiveware.model.Record;
import com.google.common.collect.Lists;

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

	public void writeFile(List<String> linesResult, MessageBot obj) {

		String path = workerFileRead + File.separator + obj.getPathFile();

		File fileOut = new File( path);
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileOut, true))) {

			linesResult.forEach((line)->{
				try {
					bw.write(line);
					bw.newLine();

				} catch (IOException e) {
					logger.error("{}", e);
				}
			});
		} catch (IOException e) {
			logger.error("{}", e);
		}
	}
}