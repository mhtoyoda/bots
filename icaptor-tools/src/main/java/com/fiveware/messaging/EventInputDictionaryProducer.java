package com.fiveware.messaging;

import com.fiveware.model.MessageInputDictionary;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by valdisnei on 13/06/17.
 */
@Service("eventInputDictionaryProducer")
public class EventInputDictionaryProducer implements Producer<MessageInputDictionary> {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void send(MessageInputDictionary message) {
        rabbitTemplate.convertAndSend("BOT", message);
    }
}
