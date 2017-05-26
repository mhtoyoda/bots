package com.fiveware.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.fiveware.model.OutTextRecord;
import com.fiveware.model.Record;
import com.google.common.collect.Lists;

@Component
public class FileUtil {

	public List<Record> linesFrom(File file, String[] fields, String separator) throws IOException {
		List<String> linhas = Lists.newArrayList();
		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				linhas.add(line);
			}
			scanner.close();
		} catch (IOException exception) {
			throw exception;
		}

		List<Record> lines = getLines(linhas, fields, separator);
		return lines;
	}

	public void writeFile(String fileNameOut, String separator, OutTextRecord result) throws IOException {		
		File fileOut = new File(fileNameOut);
		FileWriter fileWriter = new FileWriter(fileOut, true);
		try (BufferedWriter bw = new BufferedWriter(fileWriter);) {
			if (null != result.getMap()) {
				StringBuilder line = new StringBuilder();
				result.getMap().entrySet().forEach(entry -> {
					line.append(entry.getValue()).append(separator);
				});
				bw.write(StringUtils.removeEnd(line.toString(), separator));
			} else {
				bw.write("");
			}
			bw.newLine();
		}
	}

	private List<Record> getLines(List<String> lines, String[] fields, String separator) {
		List<Record> recordsLines = Lists.newArrayList();
		lines.forEach(record -> {
			String[] recordArray = record.split(separator);
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
}