package com.fiveware.messaging;

import com.fiveware.exception.ExceptionBot;
import com.fiveware.model.StatuProcess;
import com.fiveware.model.message.MessageAgent;
import com.fiveware.model.message.MessageBot;
import com.fiveware.service.ServiceItemTask;
import com.fiveware.service.ServiceTask;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Consumer;

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

	@Override
	public void process(MessageAgent message) {
		logger.info("Purge Queue {}",message);
		message.getNameQueues().forEach((q)-> {
			MessageBot messageBot;
			while ((messageBot = receiveMessage(q))!=null){
					logger.debug("purge: {}",messageBot);

				StatuProcess statuProcess = new StatuProcess(messageBot.getStatuProcessEnum().getId(),
														     messageBot.getStatuProcessEnum().getName());
				serviceItemTask.updateStatus(messageBot.getItemTaskId(),statuProcess);
				serviceTask.updateStatus(messageBot.getTaskId(),statuProcess);

			}
		});
	}


	private MessageBot receiveMessage(String queueName) {
		return receiver.receive(queueName);
	}
}
