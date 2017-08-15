package com.fiveware.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fiveware.model.message.MessageAgent;
import com.fiveware.model.message.MessageBot;
import com.fiveware.service.ServiceItemTask;
import com.fiveware.service.ServiceTask;
import com.fiveware.task.StatuProcessItemTaskEnum;
import com.fiveware.task.StatuProcessTaskEnum;

@Component("PURGE_QUEUES")
public class PurgeQueuesMessage implements ConsumerTypeMessage<MessageAgent> {

	private static Logger logger = LoggerFactory.getLogger(PurgeQueuesMessage.class);


	@Autowired
	@Qualifier("eventBotReceiver")
	private Receiver<MessageBot> receiver;

	@Autowired
	private ServiceTask serviceTask;

	@Autowired
	private ServiceItemTask serviceItemTask;

	@Autowired
	private BrokerManager brokerManager;

	@Override
	public void process(MessageAgent message) {
		logger.info("Purge Queue {}",message);
		message.getNameQueues().forEach((q)-> {
			MessageBot messageBot;
			while ((messageBot = receiveMessage(q))!=null){
				logger.debug("purge: {}",messageBot);
				serviceItemTask.updateStatus(messageBot.getItemTaskId(),StatuProcessItemTaskEnum.ERROR.getStatuProcess());
				serviceTask.updateStatus(messageBot.getTaskId(),StatuProcessTaskEnum.ERROR.getStatuProcess());
			}
			brokerManager.deleteQueue(q);
		});
	}


	private MessageBot receiveMessage(String queueName) {
		return receiver.receive(queueName);
	}
}
