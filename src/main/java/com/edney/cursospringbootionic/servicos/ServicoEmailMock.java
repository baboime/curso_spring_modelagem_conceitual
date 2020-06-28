package com.edney.cursospringbootionic.servicos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

public class ServicoEmailMock extends ServicoAbstratoEmail {
	
	private static final Logger LOG = LoggerFactory.getLogger(ServicoEmailMock.class);

	@Override
	public void enviarEmail(SimpleMailMessage msg) {
		LOG.info("Simulando envio de email...");
		LOG.info(msg.toString());
		LOG.info("Email enviado");
	}
}
