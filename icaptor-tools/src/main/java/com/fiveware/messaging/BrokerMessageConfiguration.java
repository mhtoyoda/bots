package com.fiveware.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fiveware.messaging.MessageReceiver;

@Configuration
@EnableAutoConfiguration
public class BrokerMessageConfiguration {

	@Value("${queue.alive}")
	private String messageQueue;
	
	@Value("${broker.user}")
	private String brokerUser;
	
	@Value("${broker.pass}")
	private String brokerPass;
	
	@Value("${broker.host}")
	private String brokerHost;
	
	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(brokerHost);
		connectionFactory.setUsername(brokerUser);
		connectionFactory.setPassword(brokerPass);
		return connectionFactory;
	}
	
	@Bean
	public Queue queue() {
		return new Queue(messageQueue, true);
	}

	@Bean
	public TopicExchange exchange() {
		return new TopicExchange("spring-boot-exchange");
	}

	@Bean
	public Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(messageQueue);
	}

//	@Bean
//	public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
//	MessageListenerAdapter listenerAdapter) {
//		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//		container.setConnectionFactory(connectionFactory);
//		container.setQueueNames(messageQueue);
//		container.setMessageListener(listenerAdapter);
//		return container;
//	}
//	
//	@Bean
//	public MessageReceiver MessageReceiver(){
//		return new MessageReceiver();
//	}
//	
//	@Bean
//	public MessageListenerAdapter listenerAdapter(MessageReceiver messageReceiver) {
//		return new MessageListenerAdapter(messageReceiver, "receive");
//	}
}