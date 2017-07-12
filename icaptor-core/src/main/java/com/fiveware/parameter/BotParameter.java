package com.fiveware.parameter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.model.entities.ParameterBot;
import com.fiveware.model.entities.ParameterValueBot;
import com.fiveware.repository.ParameterBotRepository;

@Component
public class BotParameter {

	private List<Map<String, String>> parameters;
	
	@Autowired
	private ParameterBotRepository parameterBotRepository;
	
	public void loadParameterBot(String nameBot){
		Optional<List<ParameterBot>> optional = parameterBotRepository.findByNameBotAndAtivoIsTrue(nameBot);
		optional.ifPresent(list -> {
			parameters = Lists.newArrayList();
			Map<String, String> map = new LinkedHashMap<String, String>();
			list.forEach(param -> {
				List<ParameterValueBot> parameterValues = param.getParameterValues();
				if(CollectionUtils.isNotEmpty(parameterValues)){
					parameterValues.forEach(paramValue -> {
						map.put(paramValue.getKey(), paramValue.getValue());
					});
				}
				parameters.add(map);
			});
		});
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
