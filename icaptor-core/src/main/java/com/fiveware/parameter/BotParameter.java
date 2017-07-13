package com.fiveware.parameter;

import com.fiveware.model.BotParameterKeyValue;
import com.fiveware.model.entities.ParameterValueBot;
import com.fiveware.service.ServiceParameterBotValue;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Component
public class BotParameter {
	
	@Autowired
	private ServiceParameterBotValue serviceParameterBotValue;

	
	public List<Map<String, BotParameterKeyValue>> loadParameterBot(String nameBot){
		List<Map<String, BotParameterKeyValue>> parameters = Lists.newArrayList();
		List<ParameterValueBot> list = serviceParameterBotValue.findByParameterBotValues(nameBot);
		if(CollectionUtils.isNotEmpty(list)){
			Map<String, BotParameterKeyValue> map = new LinkedHashMap<String, BotParameterKeyValue>();
			list.forEach(param -> {
				String key = param.getParameterBot().getName();
				if(map.containsKey(key)){
					map.get(key).add(param.getParameter(), param.getValue());
				}else{
					map.put(key ,new BotParameterKeyValue().add(param.getParameter(), param.getValue()));					
				}
			});			
			parameters.add(map);
		}
		return parameters;
	}
}
