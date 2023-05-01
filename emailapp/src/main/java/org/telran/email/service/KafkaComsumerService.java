package org.telran.email.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import org.telran.email.entity.EmailDetails;

import java.util.List;

@Service
public class KafkaComsumerService {

	@Autowired
	private EmailService emailService;
	
	@Value("${mail.recipient}")
	private String recipient;

	@Value("${mail.subject}")
	private String subject;

	@KafkaListener(topics = "${cloudkarafka.topic}")
	    public void processMessage(String message,
	                               @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
	                               @Header(KafkaHeaders.RECEIVED_TOPIC) List<String> topics,
	                               @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
	    	System.err.printf("%s-%d[%d] \"%s\"\n", topics.get(0), partitions.get(0), offsets.get(0), message.toString());
	    	
	    	EmailDetails details = new EmailDetails();
	    	details.setRecipient(recipient);
	    	details.setSubject(subject);
	    	details.setMsgBody(message.toString());
	    	
	    	String result = emailService.sendSimpleMail(details);
	    	System.err.println(result);
	    }

}
