package com.nttdata.nova.bookStore.listener;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.nttdata.nova.bookStore.dto.BookRegistryDTOJson;
import com.nttdata.nova.bookStore.service.IBookRegistryService;

import org.springframework.kafka.support.KafkaHeaders;

@Component
public class KafkaConsumer {
	
	@Autowired
	private IBookRegistryService bookRegistryService;

	@KafkaListener(
			  topics = "${kafka.topic.name}"
			  //containerFactory = "kafkaListenerContainerFactory"
			  )
	public void listenerToMongo(
			@Payload String message, 
			@Header(KafkaHeaders.RECEIVED_TOPIC) String topic
		) {
		System.out.println("Received Message: " + message  
							+ " from topic: " + topic);
		
		bookRegistryService.save(new BookRegistryDTOJson(message, new Date()));
	}
}
