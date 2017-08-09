package com.fiveware.util;

import com.fiveware.model.Record;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class LineUtil {

	public List<Record> linesFrom(List<String> linhas, String[] fields, String separator) throws IOException {
		List<Record> lines = getLines(linhas, fields, separator);
		return lines;
	}

	public List<Record> getLines(List<String> lines, String[] fields, String separator) {
		List<Record> recordsLines = Lists.newArrayList();

		lines.forEach(record -> {
			String[] recordArray = StringUtils.split(record, separator);
			Record recordLines = new Record();
			for (int i = 0; i < fields.length; i++) {
				recordLines.addRecordLine(fields[i], recordArray[i]);
			}
			recordsLines.add(recordLines);
		});
		return recordsLines;
	}

	public Record linesFrom(String line, String[] fields, String separator) {
		Record record = new Record();
		String[] recordArray = StringUtils.split(line, separator);
		if (recordArray.length == fields.length) {
			for (int i = 0; i < fields.length; i++) {
				record.addRecordLine(fields[i], recordArray[i]);
			}
		}
		return record;
	}


}
