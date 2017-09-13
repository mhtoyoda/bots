package com.fiveware.util;

import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Consumer;

import org.springframework.stereotype.Service;

@Service
public class CsvConvertUtil {

	public Consumer<Collection<Object>> convertMapToCSVLine(List<String> lines, String separatorFile) {
		return line -> {
			final StringJoiner joiner = new StringJoiner(separatorFile);
			line.forEach((column) -> joiner.add((CharSequence) column));
			lines.add(joiner.toString());
		};
	}
}
