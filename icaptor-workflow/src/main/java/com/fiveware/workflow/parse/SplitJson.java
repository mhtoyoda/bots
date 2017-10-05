package com.fiveware.workflow.parse;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.common.collect.Lists;

@Component
public class SplitJson {

	public List<String> splitArray(String json) {
		List<String> list = Lists.newArrayList();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode;
		try {
			jsonNode = mapper.readTree(json);
			if (jsonNode.isArray()) {
				ArrayNode arrayNode = (ArrayNode) jsonNode;
				for (int i = 0; i < arrayNode.size(); i++) {
					JsonNode individualElement = arrayNode.get(i);
					list.add(individualElement.toString());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
}
