package com.fiveware.scheduler;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fiveware.messaging.ConsumerTypeMessage;
import com.fiveware.messaging.Receiver;
import com.fiveware.messaging.TypeMessage;

@Component
public class ConsumerScheduler {

	private Map<String, ConsumerTypeMessage> consumersMap;
	
	@Autowired
	private Receiver<String> receiver;
	
	@Autowired
	@Qualifier("keepAliveMessage")
	private ConsumerTypeMessage keepAliveMessage;
	
	@Autowired
	@Qualifier("stopAgentMessage")
	private ConsumerTypeMessage stopAgentMessage;
	
	@PostConstruct
	public void init(){		
		consumersMap = new ConcurrentHashMap<String, ConsumerTypeMessage>();
		consumersMap.put(TypeMessage.KEEP_ALIVE.name(), keepAliveMessage);
		consumersMap.put(TypeMessage.STOP_AGENT.name(), stopAgentMessage);
	}
	
	@Scheduled(fixedDelay = 5000)
	public void execute() {
		Stream<TypeMessage> types = Arrays.stream(TypeMessage.values());
		Optional<TypeMessage> any = types
				.filter(TypeMessage.KEEP_ALIVE::equals)
				.findAny();
		receiver.receive(any.get());

	}
}