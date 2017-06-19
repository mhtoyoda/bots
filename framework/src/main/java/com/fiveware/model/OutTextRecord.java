package com.fiveware.model;

import java.util.*;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fiveware.converter.ArrayMapDeserializer;

public class OutTextRecord {

	@JsonProperty("result")
	@JsonDeserialize(using = ArrayMapDeserializer.class)
	private final Map<String, Object> map[];

	private static HashMap<String,Object> hashMap = new HashMap<>();
	static {
		hashMap.put("registro","registro nao encontrado");
	}

	public static OutTextRecord EMPTY_RECORD = new OutTextRecord(new HashMap[]{hashMap});


	public OutTextRecord(final Map<String, Object> map[]) {
		this.map = map;
	}

	public  Map<String, Object>[] getMap() {
		return map;
	}


	@Bean
	public ArrayMapDeserializer arrayMapDeserializer(){
		return new ArrayMapDeserializer();
	}

	public static void main(String[] args) {
		OutTextRecord EMPTY_RECORD = new OutTextRecord(new HashMap[]{
				(HashMap<String, Object>) new HashMap<>().put("registro","registro nao encontrado")
		});

		List<Map<String, Object>> collect = Optional
				.ofNullable(Arrays.stream(EMPTY_RECORD.getMap()).collect(Collectors.toList()))
				.orElse(Collections.emptyList());

		ArrayList<Object> results = Lists.newArrayList();

		collect.stream().map(Map::values).forEach(list -> {
			StringJoiner joiner = new StringJoiner(",");
			list.forEach((v) -> joiner.add((CharSequence) v));
			results.add(joiner.toString());
		});

		System.out.println("results = " + results);

	}
}
