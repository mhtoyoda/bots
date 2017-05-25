package com.fiveware.file;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.assertj.core.util.Lists;
import org.springframework.stereotype.Component;

@Component
public class FileUtil {

	public List<RecordLine> getLines(File file, String[] fields) {
		List<RecordLine> recordsLines = Lists.newArrayList();
		RecordLine recordLines = new RecordLine();
		List<String> lines = getLines(file);
		lines.forEach(record -> recordLines.addRecordLine("cep", record));
		return recordsLines;
	}

	public File getFile(String fileName) {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());
		return file;
	}

	private List<String> getLines(File file) {
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
