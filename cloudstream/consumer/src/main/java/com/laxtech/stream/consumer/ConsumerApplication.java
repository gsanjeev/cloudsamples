package com.laxtech.stream.consumer;

import org.apache.zookeeper.server.quorum.Vote;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;

import org.slf4j.Logger;

@EnableBinding(SinkChannel.class)
@SpringBootApplication
public class ConsumerApplication {

/*	@Bean
	@Scope("prototype")
	Logger logger(InjectionPoint ip) {
		return Logger.getLogger((ip.getDeclaredType().getName()));
	}*/

	private static Logger logger = LoggerFactory.getLogger(ConsumerApplication.class);

/*	@Bean
	IntegrationFlow integrationFlow(Logger logger, SinkChannel sinkChannel) {
		return IntegrationFlows.from(sinkChannel.inputChannel())
				.handle(String.class, (payload, headers) -> {
					 logger.info("New Message:" + payload);
					return null;
		})
		.get();
	}*/


	@StreamListener(SinkChannel.INPUT)
	public void handle(String payload) {
		//System.out.println("New Message:" + payload);
		logger.info("New Message:" + payload);
	}

	public static void main(String[] args)
	{
		SpringApplication.run(ConsumerApplication.class, args);
	}
}
