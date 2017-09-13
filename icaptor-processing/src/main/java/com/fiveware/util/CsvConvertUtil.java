package com.fiveware.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fiveware.model.Record;
import com.google.common.collect.Lists;

@Service
public class CsvConvertUtil {

	public List<String> convertMapToCsvLine(String separatorFile, List<Record> recordList) {
		final List<String> lines = Lists.newArrayList();
		Consumer<List<Record>> consumer = records -> records.stream().map(Record::getRecordMap)
				.collect(Collectors.toList()).stream().map(Map::values)
				.forEach(convertMapToCSVLine(lines, separatorFile));
		consumer.accept(recordList);
		return lines;
	}

	private Consumer<Collection<Object>> convertMapToCSVLine(List<String> lines, String separatorFile) {
		return line -> {
			final StringJoiner joiner = new StringJoiner(separatorFile);
			line.forEach((column) -> joiner.add((CharSequence) column));
			lines.add(joiner.toString());
		};
	}
}