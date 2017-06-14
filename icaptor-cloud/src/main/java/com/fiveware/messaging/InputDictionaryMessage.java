package com.fiveware.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fiveware.model.MessageBot;

@Component("INPUT_DICTIONARY")
public class InputDictionaryMessage implements ConsumerTypeMessage<MessageBot> {

	private static Logger log = LoggerFactory.getLogger(InputDictionaryMessage.class);


	@Override
	public void process(MessageBot message) {
		log.debug("{}",message.toString());
	}

}
