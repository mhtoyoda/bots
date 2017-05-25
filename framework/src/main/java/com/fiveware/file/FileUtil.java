package com.fiveware.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.fiveware.model.Record;
import com.google.common.collect.Lists;

@Component
public class FileUtil {

	public List<Record> linesFrom(File file, String[] fields, String separator) {
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

		List<Record> lines = getLines(linhas, fields, separator);
		return lines;
	}

	public void writeFile(List<Record> results) throws IOException {
		File fout = new File("cep_out.txt");
		FileOutputStream fos = new FileOutputStream(fout);

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

		for (Record result : results) {
			Map<String, String> recordMap = result.getRecordMap();
			StringBuilder builder = new StringBuilder(recordMap.get(""));
			bw.write(builder.toString());
			bw.newLine();
		}

		bw.close();
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