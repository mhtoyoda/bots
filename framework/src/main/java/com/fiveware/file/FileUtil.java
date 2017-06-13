package com.fiveware.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fiveware.model.OutTextRecord;
import com.fiveware.model.Record;
import com.google.common.collect.Lists;

@Component
public class FileUtil {

	static Logger logger = LoggerFactory.getLogger(FileUtil.class);

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
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileOut, true))) {

			List<Map<String, Object>> collect = Optional
					.ofNullable(Arrays.stream(result.getMap()).collect(Collectors.toList()))
					.orElse(Collections.emptyList());

			collect.stream().map(Map::values).forEach(v -> {
				try {
					consumer3(v, separator, bw);
				} catch (IOException e) {
					logger.error("{}", e);
				}
			});
		}
	}

	private Consumer<? super Collection<Object>> consumer3(Collection<Object> list, String separator, BufferedWriter bw)
			throws IOException {
		StringJoiner joiner = new StringJoiner(separator);
		list.forEach((v) -> joiner.add((CharSequence) v));
		bw.write(joiner.toString());
		bw.newLine();

		return null;
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