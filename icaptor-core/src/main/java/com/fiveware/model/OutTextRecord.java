package com.fiveware.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fiveware.converter.ArrayMapDeserializer;
import org.springframework.context.annotation.Bean;

import java.util.Map;

public class OutTextRecord {

	@JsonProperty("result")
	@JsonDeserialize(using = ArrayMapDeserializer.class)
	private final Map<String, Object> map[];



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
}
