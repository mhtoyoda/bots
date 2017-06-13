package com.fiveware.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fiveware.model.MessageInputDictionary;

@Component("INPUT_DICTIONARY")
public class InputDictionaryMessage implements ConsumerTypeMessage<MessageInputDictionary> {

	private static Logger log = LoggerFactory.getLogger(InputDictionaryMessage.class);


	@Override
	public void process(MessageInputDictionary message) {
		log.info("{}",message.toString());
	}

}
