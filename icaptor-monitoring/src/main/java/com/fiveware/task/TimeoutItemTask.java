package com.fiveware.task;

import com.fiveware.model.ItemTask;
import com.fiveware.model.Parameter;
import com.fiveware.model.StatusProcessItemTaskEnum;
import com.fiveware.parameter.ParameterResolver;
import com.fiveware.service.ServiceElasticSearch;
import com.google.common.base.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

@Component
public class TimeoutItemTask {

	@Autowired
	private TaskManager taskManager;
	
	@Autowired
	private ParameterResolver parameterResolver;

	@Autowired
	private ServiceElasticSearch serviceElasticSearch;

	public void checkTimeout() {
		int timeout = getTimeoutParameter();
		List<ItemTask> itemTaskProcessing = taskManager.allItemTaskProcessing(StatusProcessItemTaskEnum.PROCESSING.getName());
		itemTaskProcessing.stream().forEach(item -> {
			LocalTime startAt = item.getStartAt().toLocalTime();
			LocalTime now = LocalTime.now();
			long secondsDuration = Duration.between(startAt, now).getSeconds();
			if (secondsDuration > timeout) {
				taskManager.updateItemTask(item.getId(), StatusProcessItemTaskEnum.ERROR);
				serviceElasticSearch.log(item,item.getId());
			}
		});
	}
	
	private int getTimeoutParameter() {
		Parameter parameterCloud = parameterResolver.getParameterCloud("timeout");
		Optional<Parameter> optional = Optional.fromNullable(parameterCloud);
		if (optional.isPresent()) {
			Parameter parameter = optional.get();
			return Integer.parseInt(parameter.getFieldValue());
		}
		return 15;
	}
}