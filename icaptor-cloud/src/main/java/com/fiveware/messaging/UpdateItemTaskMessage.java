package com.fiveware.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fiveware.model.message.MessageTaskAgent;
import com.fiveware.service.ServiceItemTask;
import com.fiveware.task.StatuProcessItemTaskEnum;

@Component("ITEM_TASK_PROCESSING")
public class UpdateItemTaskMessage implements ConsumerTypeMessage<MessageTaskAgent> {

	private static Logger logger = LoggerFactory.getLogger(UpdateItemTaskMessage.class);

	@Autowired
	private ServiceItemTask serviceItemTask;

	@Override
	public void process(MessageTaskAgent message) {
		logger.info("Update Item Task: {}", message.getItemTaskId());
		serviceItemTask.updateStatus(message.getItemTaskId(), StatuProcessItemTaskEnum.PROCESSING.getStatuProcess());
	}
}
