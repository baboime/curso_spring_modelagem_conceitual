package com.edney.cursospringbootionic.servicos;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class ServicoEmailSmtp extends ServicoAbstratoEmail {

	@Autowired
	private MailSender mailSender;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	private static final Logger LOG = LoggerFactory.getLogger(ServicoEmailSmtp.class);
	
	@Override
	public void enviarEmail(SimpleMailMessage msg) {
		LOG.info("Enviando email...");
		mailSender.send(msg);
		LOG.info("Email enviado");
	}

	@Override
	public void enviarEmailHtml(MimeMessage msg) {
		LOG.info("Enviando email html...");
		javaMailSender.send(msg);
		LOG.info("Email html enviado");
	}
}
