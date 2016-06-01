package com.say.domain;

import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

	@Value("${email.smtp}")
	private String smtp;

	@Value("${email.port}")
	private Integer port;

	@Value("${email.from}")
	private String from;

	@Value("${email.username}")
	private String username;

	@Value("${email.password}")
	private String password;

	JavaMailSenderImpl mailSender;

	@PostConstruct
	private void setup(){
		Properties props = new Properties();

		props.put("mail.smtps.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");

		mailSender = new JavaMailSenderImpl();
		mailSender.setHost(smtp);
		mailSender.setPort(port);
		mailSender.setProtocol("smtps");
		mailSender.setUsername(username);
		mailSender.setPassword(password);
		mailSender.setJavaMailProperties(props);
	}

	@Async
	public void bulkSend(List<Email> emails) {
		for(Email email: emails){
			send(email);
		}
	}

	@Async
	public void send(Email email) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, email.getTo());
			message.setSubject(email.getSubject() );
			message.setContent(email.getMessage(), "text/html");
			mailSender.send(message);

		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
