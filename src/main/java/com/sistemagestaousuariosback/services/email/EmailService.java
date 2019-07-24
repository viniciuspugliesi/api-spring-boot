package com.sistemagestaousuariosback.services.email;

import javax.mail.MessagingException;

import com.sistemagestaousuariosback.mails.Mail;

public interface EmailService {

	public void send(Mail mail) throws MessagingException;
}
