package com.fiveware.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
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

	@Bean
	public RabbitAdmin rabbitAdmin(){
		return new RabbitAdmin(connectionFactory());
	}

	@Bean
	public RabbitTemplate rabbitTemplate(){
//		return new RabbitAdmin(connectionFactory());

		RabbitTemplate rabbitTemplate = new RabbitTemplate( connectionFactory() );
		((CachingConnectionFactory)rabbitTemplate.getConnectionFactory()).setPublisherConfirms( true );
		rabbitTemplate.setConfirmCallback( new RabbitTemplate.ConfirmCallback() {

			@Override
			public void confirm(CorrelationData corData, boolean ack, String cause ) {
				System.out.println( "devconfig.rabbitTemplate(...).new ConfirmCallback() {...}.confirm()"+corData );
				System.out.println( "devconfig.rabbitTemplate(...).new ConfirmCallback() {...}.confirm()"+ack );
			}
		} );

		return rabbitTemplate;

	}

}