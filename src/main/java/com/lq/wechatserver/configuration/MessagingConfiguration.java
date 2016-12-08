package com.lq.wechatserver.configuration;

import java.util.Arrays;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

//@Configuration
public class MessagingConfiguration {

	private static final String DEFAULT_BROKER_URL = "tcp://123.56.233.132:61616";
	
	private static final String PAYINFO_QUEUE = "payinfo-queue";

//	@Bean
	public ActiveMQConnectionFactory connectionFactory(){
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL(DEFAULT_BROKER_URL);
		connectionFactory.setTrustedPackages(Arrays.asList("com.lq.wechatserver"));
		connectionFactory.setTransactedIndividualAck(true);
		return connectionFactory;
	}
	
//	@Bean 
	public JmsTemplate jmsTemplate(){
		JmsTemplate template = new JmsTemplate();
		template.setConnectionFactory(connectionFactory());
		template.setDefaultDestinationName(PAYINFO_QUEUE);
		template.setSessionTransacted(true);
		return template;
	}
	
}
