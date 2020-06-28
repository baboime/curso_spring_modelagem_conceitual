package com.edney.cursospringbootionic.servicos;

import org.springframework.mail.SimpleMailMessage;

import com.edney.cursospringbootionic.dominios.Pedido;

public interface ServicoEmail {
	
	void enviarPedidoDeConfirmacaoDeEmail(Pedido obj);
	
	void enviarEmail(SimpleMailMessage msg);
}
