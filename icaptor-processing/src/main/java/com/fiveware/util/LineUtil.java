package com.fiveware.util;

import com.fiveware.model.Record;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LineUtil {

	public Record linesFrom(String line, String[] fields, String separator) throws IOException {
		Record lines = getLines(line, fields, separator);
		return lines;
	}

	public Record getLines(String line, String[] fields, String separator) {
		Record record = new Record();

			String[] recordArray = StringUtils.split(line, separator);
			if (recordArray.length == fields.length) {
				Record recordLines = new Record();
				for (int i = 0; i < fields.length; i++) {
					record.addRecordLine(fields[i], recordArray[i]);
				}
			}
		return record;
	}
}
