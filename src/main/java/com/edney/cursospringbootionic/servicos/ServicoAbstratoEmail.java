package com.edney.cursospringbootionic.servicos;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.edney.cursospringbootionic.dominios.Cliente;
import com.edney.cursospringbootionic.dominios.Pedido;

public abstract class ServicoAbstratoEmail implements ServicoEmail {
	
	@Value("${default.sender}")
	private String remetente;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
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
	
	protected String  templateHtmlDoPedido(Pedido obj) {
		Context context = new Context();
		context.setVariable("pedido", obj);
		return templateEngine.process("email/confirmacaoPedido", context);
	}
	
	@Override
	public void enviarPedidoDeConfirmacaoDeEmailHtml(Pedido obj) {
		try {
			MimeMessage mm = prepararMimeMessageDoPedido(obj);
			enviarEmailHtml(mm);
		}
		catch (MessagingException e) {
			enviarPedidoDeConfirmacaoDeEmail(obj);
		}
	}

	private MimeMessage prepararMimeMessageDoPedido(Pedido obj) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		mmh.setTo(obj.getCliente().getEmail());
		mmh.setFrom(remetente);
		mmh.setSubject("Pedido confirmado! Código: " + obj.getId());
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(templateHtmlDoPedido(obj), true);
		return mimeMessage;
	}
	
	@Override
	public void enviarNovaSenhaEmail(Cliente cliente, String novaSenha) {
		SimpleMailMessage sm = prepararEmailNovaSenha(cliente, novaSenha);
		enviarEmail(sm);
	}

	protected SimpleMailMessage prepararEmailNovaSenha(Cliente cliente, String novaSenha) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(cliente.getEmail());
		sm.setFrom(remetente);
		sm.setSubject("Solicitação de nova senha");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("Nova senha: " + novaSenha);
		return sm;
	}
}
