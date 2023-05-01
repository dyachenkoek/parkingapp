package org.telran.email.service;

import org.telran.email.entity.EmailDetails;

public interface EmailService {

	String sendSimpleMail(EmailDetails details);
}
