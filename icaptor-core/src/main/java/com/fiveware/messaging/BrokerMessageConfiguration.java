package com.fiveware.messaging;

import com.fiveware.config.ICaptorApiProperty;
import com.fiveware.exception.RuntimeBotException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.listener.exception.ListenerExecutionFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BrokerMessageConfiguration {

	static Logger logger = LoggerFactory.getLogger(BrokerMessageConfiguration.class);

	@Autowired
	private ICaptorApiProperty iCaptorApiProperty;


	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(iCaptorApiProperty.getBroker().getHost());
		connectionFactory.setUsername(iCaptorApiProperty.getBroker().getUser());
		connectionFactory.setPassword(iCaptorApiProperty.getBroker().getPass());
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
	public RabbitAdmin rabbitTemplate(){
		return new RabbitAdmin(connectionFactory());

//		final AtomicReference<CorrelationData> confirmCD = new AtomicReference<CorrelationData>();


//		RabbitTemplate rabbitTemplate = new RabbitTemplate( connectionFactory() );
//		((CachingConnectionFactory)rabbitTemplate.getConnectionFactory()).setPublisherConfirms( true );
//		((CachingConnectionFactory)rabbitTemplate.getConnectionFactory()).setPublisherReturns( true );
//
//		rabbitTemplate.setConfirmCallback( new RabbitTemplate.ConfirmCallback() {
//			@Override
//			public void confirm(CorrelationData corData, boolean ack, String cause ) {
////				logger.debug("correlationData = {} - ack = {}",corData,ack);
//			}
//		} );
//
//
//		rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
//			@Override
//			public void returnedMessage(Message message, int i, String s, String s1, String s2) {
//				System.out.println("message = " + message);
//			}
//		});


//		return rabbitTemplate;

	}


	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setErrorHandler(new ConditionalRejectingErrorHandler(
				t -> t instanceof ListenerExecutionFailedException && t.getCause() instanceof RuntimeBotException));
		return factory;
	}

}