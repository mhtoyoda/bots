package com.fiveware.util;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.fiveware.model.Record;
import com.google.common.collect.Lists;

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
