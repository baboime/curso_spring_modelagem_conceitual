package com.edney.cursospringbootionic.servicos;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.edney.cursospringbootionic.dominios.Pedido;

public interface ServicoEmail {
	
	void enviarPedidoDeConfirmacaoDeEmail(Pedido obj);
	
	void enviarEmail(SimpleMailMessage msg);
	
	void enviarPedidoDeConfirmacaoDeEmailHtml(Pedido obj);
	
	void enviarEmailHtml(MimeMessage msg);
}
