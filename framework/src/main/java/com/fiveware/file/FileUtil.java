package com.fiveware.file;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

@Component
public class FileUtil {

	public List<RecordLine> getLines(File file, String[] fields, String separator) {
		List<RecordLine> recordsLines = Lists.newArrayList();

		List<String> lines = linesFrom(file);
		lines.forEach(record -> {
			String[] recordArray = record.split(separator);
			if (recordArray.length == fields.length) {
				RecordLine recordLines = new RecordLine();
				for (int i = 0; i < fields.length; i++) {
					recordLines.addRecordLine(fields[i], recordArray[i]);
				}
				recordsLines.add(recordLines);
			}
		});
		return recordsLines;
	}

	public File getFile(String fileName) {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());
		return file;
	}

	private List<String> linesFrom(File file) {
		List<String> linhas = Lists.newArrayList();
		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				linhas.add(line);
			}
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return linhas;
	}

}
