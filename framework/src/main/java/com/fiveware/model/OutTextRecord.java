package com.fiveware.model;

import java.util.Map;

import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fiveware.converter.ArrayMapDeserializer;

public class OutTextRecord {

	@JsonProperty("bot")
	@JsonDeserialize(using = ArrayMapDeserializer.class)
	private final Map<String, Object> map;
	
	public OutTextRecord(final Map<String, Object> map) {
		this.map = map;
	}

	public Map<String, Object> getMap() {
		return map;
	}



	@Bean
	public ArrayMapDeserializer arrayMapDeserializer(){
		return new ArrayMapDeserializer();
	}
}
