package com.fiveware.config;

import org.springframework.amqp.core.AmqpManagementOperations;
import org.springframework.amqp.rabbit.core.RabbitManagementTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class MonitoringConfig {


    @Autowired
    private ICaptorApiProperty iCaptorApiProperty;

    @Bean
    public AmqpManagementOperations amqpManagementOperations(){
        String uri = String.format("http://%s:%d/api/", iCaptorApiProperty.getBroker().getHost(), iCaptorApiProperty.getBroker().getPort());
        return new RabbitManagementTemplate(uri, iCaptorApiProperty.getBroker().getUser(), iCaptorApiProperty.getBroker().getPass());
    }

    @Bean(name = "messageSource")
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("i18n/messages");
        messageSource.setFallbackToSystemLocale(false);
        messageSource.setCacheSeconds(0);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
