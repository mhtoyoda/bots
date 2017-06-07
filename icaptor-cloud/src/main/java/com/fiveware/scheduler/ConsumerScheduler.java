package com.fiveware.scheduler;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fiveware.messaging.ConsumerTypeMessage;
import com.fiveware.messaging.Receiver;
import com.fiveware.messaging.TypeMessage;
import com.google.common.collect.Lists;

@Component
public class ConsumerScheduler {

	private Map<String, ConsumerTypeMessage> consumersMap;
	
	private List<TypeMessage> types;
	
	@Autowired
	private Receiver<String> receiver;
	
	@Autowired
	@Qualifier("keepAliveMessage")
	private ConsumerTypeMessage keepAliveMessage;
	
	@Autowired
	@Qualifier("stopAgentMessage")
	private ConsumerTypeMessage stopAgentMessage;
	
	public void init(){
		if( null == consumersMap ){
			consumersMap = new ConcurrentHashMap<String, ConsumerTypeMessage>();
			types = Lists.newArrayList(TypeMessage.values());
			consumersMap.put(TypeMessage.KEEP_ALIVE.name(), keepAliveMessage);
			consumersMap.put(TypeMessage.KEEP_ALIVE.name(), stopAgentMessage);
		}
	}
	
	@Scheduled(fixedDelay = 5000)
	public void execute(){
		init();
		types.forEach(typeMessage -> {
//			Optional<String> optionalMessage = receiver.receive(typeMessage);
//			optionalMessage.ifPresent( msg -> {
//				consumersMap.get(typeMessage).process(msg);
//			});			
		});
	}
}