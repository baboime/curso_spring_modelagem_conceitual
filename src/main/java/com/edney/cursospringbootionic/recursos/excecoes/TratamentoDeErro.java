package com.edney.cursospringbootionic.recursos.excecoes;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.edney.cursospringbootionic.servicos.excecoes.ExcecaoObjetoNaoEncontrato;

@ControllerAdvice
public class TratamentoDeErro {
	
	@ExceptionHandler(ExcecaoObjetoNaoEncontrato.class)
	public ResponseEntity<PadraoDeErro> objetoNaoEncontrado(ExcecaoObjetoNaoEncontrato e, HttpServletRequest requisicao) {
		String erro = "Objeto não encontrado";
		HttpStatus status = HttpStatus.NOT_FOUND;
		PadraoDeErro padraoErro = new PadraoDeErro(Instant.now(), status.value(), erro, e.getMessage(), requisicao.getRequestURI());
		return ResponseEntity.status(status).body(padraoErro);
	}
	
//	@ExceptionHandler(ExcecaoBancoDeDados.class)
//	public ResponseEntity<PadraoDeErro> bancoDeDados(ExcecaoBancoDeDados e, HttpServletRequest requisicao) {
//		String erro = "Erro ao acessar o banco de dados";
//		HttpStatus status = HttpStatus.BAD_REQUEST;
//		PadraoDeErro padraoErro = new PadraoDeErro(Instant.now(), status.value(), erro, e.getMessage(), requisicao.getRequestURI());
//		return ResponseEntity.status(status).body(padraoErro);
//	}
	
}
