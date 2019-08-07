package com.sgu.services.email;

import javax.mail.MessagingException;

import com.sgu.mails.Mail;

public interface EmailService {

	public void send(Mail mail) throws MessagingException;
}
