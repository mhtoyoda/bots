package com.fiveware.task;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.model.ItemTask;
import com.fiveware.model.Parameter;
import com.fiveware.model.StatusProcessItemTaskEnum;
import com.fiveware.parameter.ParameterResolver;
import com.google.common.base.Optional;

@Component
public class TimeoutItemTask {

	@Autowired
	private TaskManager taskManager;
	
	@Autowired
	private ParameterResolver parameterResolver;

	public void checkTimeout() {
		int timeout = getTimeoutParameter();
		List<ItemTask> itemTaskProcessing = taskManager.allItemTaskProcessing(StatusProcessItemTaskEnum.PROCESSING.getName());
		itemTaskProcessing.stream().forEach(item -> {
			Instant instantStartAt = Instant.ofEpochMilli(item.getStartAt().getTime());
			LocalTime startAt = LocalDateTime.ofInstant(instantStartAt, ZoneId.systemDefault()).toLocalTime();
			LocalTime now = LocalTime.now();
			long secondsDuration = Duration.between(startAt, now).getSeconds();
			if (secondsDuration > timeout) {
				taskManager.updateItemTask(item.getId(), StatusProcessItemTaskEnum.ERROR);
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