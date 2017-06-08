package com.fiveware.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class BrokerMessageConfiguration {

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
		return new Queue(QueueName.EVENTS.name(), true);
	}

	@Bean
	public TopicExchange exchange() {
		return new TopicExchange("event-exchange");
	}

	@Bean
	public Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(QueueName.EVENTS.name());
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