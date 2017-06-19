package com.fiveware.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fiveware.model.OutTextRecord;

@Service
public class ListJoinUtil {

	public void joinRecord(String separator, OutTextRecord result, List<String> results) throws IOException {
		List<Map<String, Object>> collect = Optional
				.ofNullable(Arrays.stream(result.getMap()).collect(Collectors.toList()))
				.orElse(Collections.emptyList());
		
		collect.stream().map(Map::values).forEach(v -> consumer(v, separator, results));
	}

	private void consumer(Collection<Object> list, String separator, List<String> results) {
		StringJoiner joiner = new StringJoiner(separator);
		list.forEach((v) -> joiner.add((CharSequence) v));
		results.add(joiner.toString());
	}



}
