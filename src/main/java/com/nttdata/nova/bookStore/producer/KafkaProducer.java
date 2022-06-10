package com.nttdata.nova.bookStore.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

public class KafkaProducer {
	
	@Value("${kafka.topic.name}")
	private String topicName;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void sendMessage(String msg) {
		ListenableFuture<SendResult<String, String>> future =  kafkaTemplate.send(topicName, msg);
		
		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

	        @Override
	        public void onSuccess(SendResult<String, String> result) {
	            System.out.println("Sent message=[" + msg + 
	              "] with offset=[" + result.getRecordMetadata().offset() + "]");
	        }
	        @Override
	        public void onFailure(Throwable e) {
	            System.out.println("Unable to send message=[" 
	              + msg + "] due to : " + e.getMessage());
	        }
	    });
		
		
	}
}
