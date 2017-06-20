package com.fiveware.util;

import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import com.fiveware.model.MessageBot;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fiveware.model.OutTextRecord;
import com.fiveware.model.Record;
import com.google.common.collect.Lists;

@Component
public class FileUtil {


	@Value("${io.worker.dir}")
	private String workDir;

	@Value("${io.file-read}")
	private String workerFileRead;


	static Logger logger = LoggerFactory.getLogger(FileUtil.class);

	public List<Record> linesFrom(InputStream file, String[] fields, String separator) throws IOException {
		List<String> linhas = Lists.newArrayList();

		buildScanner(file, linhas);

		List<Record> lines = getLines(linhas, fields, separator);
		return lines;
	}


	public List<Record> linesFrom(List<String> linhas, String[] fields, String separator) throws IOException {
		List<Record> lines = getLines(linhas, fields, separator);
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


	private List<Record> getLines(List<String> lines, String[] fields, String separator) {
		List<Record> recordsLines = Lists.newArrayList();

		lines.forEach(record -> {
			String[] recordArray = StringUtils.split(record, separator);//record.split(separator);
			if (recordArray.length == fields.length) {
				Record recordLines = new Record();
				for (int i = 0; i < fields.length; i++) {
					recordLines.addRecordLine(fields[i], recordArray[i]);
				}
				recordsLines.add(recordLines);
			}
		});
		return recordsLines;
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