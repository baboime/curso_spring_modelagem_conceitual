package com.edney.cursospringbootionic.servicos;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.edney.cursospringbootionic.dominios.Pedido;

public abstract class ServicoAbstratoEmail implements ServicoEmail {
	
	@Value("${default.sender}")
	private String remetente;
	
	@Override
	public void enviarPedidoDeConfirmacaoDeEmail(Pedido obj) {
		SimpleMailMessage sm = prepararSimpleMailMessageDoPedido(obj);
		enviarEmail(sm);
	}

	protected SimpleMailMessage prepararSimpleMailMessageDoPedido(Pedido obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());
		sm.setFrom(remetente);
		sm.setSubject("Pedido Confirmado! Código: " + obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		return sm;
	}
}
