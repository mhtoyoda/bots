package com.fiveware.message.validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.model.Task;
import com.fiveware.service.ServiceTask;
import com.fiveware.model.StatusProcessTaskEnum;

@Component("validateMessageTask")
public class ValidateMessageTask implements ValidateMessage {

	private String queue;
	
	@Autowired
	private ServiceTask serviceTask;
	
	@Override
	public void setParameter(String parameter) {
		this.queue = parameter;
	}
	
	@Override
	public boolean validateStatus() {
		String[] values = this.queue.split("\\.");
		Long taskId = Long.parseLong(values[1]);
		Task task = serviceTask.getTaskById(taskId);
		if(null != task){
			return task.getStatusProcess().getName().equals(StatusProcessTaskEnum.PROCESSING.getName());
		}
		return false;
	}

}
