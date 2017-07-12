package com.fiveware.parameter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.model.entities.ParameterBot;
import com.fiveware.model.entities.ParameterValueBot;
import com.fiveware.repository.ParameterBotRepository;
import com.google.common.collect.Lists;


@Component
public class BotParameter {

	private List<Map<String, String>> parameters;
	
	@Autowired
	private ParameterBotRepository parameterBotRepository;
	
	public void loadParameterBot(String nameBot){
		Optional<List<ParameterBot>> optional = parameterBotRepository.findByNameBotAndAtivoIsTrue(nameBot);
		if(optional.isPresent()){
			parameters = Lists.newArrayList();
			Map<String, String> map = new LinkedHashMap<String, String>();
			optional.get().forEach(param -> {				
				List<ParameterValueBot> parameterValues = (List<ParameterValueBot>) param.getParameterValues();
				if(CollectionUtils.isNotEmpty(parameterValues)){
					parameterValues.forEach(paramValue -> {
						map.put(paramValue.getParameter(), paramValue.getValue());
					});
				}
				parameters.add(map);
			});			
		}
	}

	public class BotParameterKeyValue {

		private String key;
		private String value;

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}
}
