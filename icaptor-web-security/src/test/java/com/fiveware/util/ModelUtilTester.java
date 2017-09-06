package com.fiveware.util;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.fiveware.controller.helper.WebModelUtil;
import com.fiveware.model.Bot;

public class ModelUtilTester {

	private WebModelUtil modelUtil = new WebModelUtil();

	@Test
	public void testConvertBotToMap() {
		Bot b = new Bot();
		b.setEndpoint("http://localhost");
		b.setFieldsInput("IN1|IN2");
		b.setFieldsOutput("OUT1|OUT2");
		b.setId(5801L);
		b.setMethod("execute");
		b.setNameBot("Megazorde");
		b.setSeparatorFile("|");

		Map<String, Object> converted = modelUtil.convertToMap(b, "endpoint", "fieldInput", "fieldOutput", "method",
				"separatorFile");
	
		System.out.println(converted);

		Assert.assertTrue(converted.keySet().size() == 2);

	}
}
