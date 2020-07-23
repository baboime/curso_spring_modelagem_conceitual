package com.edney.cursospringbootionic.recursos.excecoes;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.edney.cursospringbootionic.servicos.excecoes.ExcecaoDeArquivo;
import com.edney.cursospringbootionic.servicos.excecoes.ExcecaoDeAutorizacao;
import com.edney.cursospringbootionic.servicos.excecoes.ExcecaoIntegracaoBandoDeDados;
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
	
	@ExceptionHandler(ExcecaoIntegracaoBandoDeDados.class)
	public ResponseEntity<PadraoDeErro> bancoDeDados(ExcecaoIntegracaoBandoDeDados e, HttpServletRequest requisicao) {
		String erro = "Erro ao acessar o banco de dados";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		PadraoDeErro padraoErro = new PadraoDeErro(Instant.now(), status.value(), erro, e.getMessage(), requisicao.getRequestURI());
		return ResponseEntity.status(status).body(padraoErro);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<PadraoDeErro> validacoesBasicas(MethodArgumentNotValidException e, HttpServletRequest requisicao) {
		String erro = "Erro em validação de campos";
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		ValidacaoDeErros padraoErro = new ValidacaoDeErros(Instant.now(), status.value(), erro, "Erro de validação", requisicao.getRequestURI());
		for (FieldError x : e.getBindingResult().getFieldErrors()) {
			padraoErro.adicionarErro(x.getField(), x.getDefaultMessage());
		}
		return ResponseEntity.status(status).body(padraoErro);
	}
	
	@ExceptionHandler(ExcecaoDeAutorizacao.class)
	public ResponseEntity<PadraoDeErro> autorizacao(ExcecaoDeAutorizacao e, HttpServletRequest requisicao) {
		String erro = "Acesso a informação não autorizada para o seu perfil";
		HttpStatus status = HttpStatus.FORBIDDEN;
		PadraoDeErro padraoErro = new PadraoDeErro(Instant.now(), status.value(), erro, e.getMessage(), requisicao.getRequestURI());
		return ResponseEntity.status(status).body(padraoErro);
	}
	
	@ExceptionHandler(ExcecaoDeArquivo.class)
	public ResponseEntity<PadraoDeErro> arquivo(ExcecaoDeArquivo e, HttpServletRequest requisicao) {
		String erro = "Erro no tratamento do arquivo";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		PadraoDeErro padraoErro = new PadraoDeErro(Instant.now(), status.value(), erro, e.getMessage(), requisicao.getRequestURI());
		return ResponseEntity.status(status).body(padraoErro);
	}
	
	@ExceptionHandler(AmazonServiceException.class)
	public ResponseEntity<PadraoDeErro> servicoAmazon(AmazonServiceException e, HttpServletRequest requisicao) {
		String erro = "Erro ao acessar o serviço da AWS";
		HttpStatus status = HttpStatus.valueOf(e.getErrorCode());
		PadraoDeErro padraoErro = new PadraoDeErro(Instant.now(), status.value(), erro, e.getMessage(), requisicao.getRequestURI());
		return ResponseEntity.status(status).body(padraoErro);
	}
	
	@ExceptionHandler(AmazonClientException.class)
	public ResponseEntity<PadraoDeErro> clientAmazon(AmazonClientException e, HttpServletRequest requisicao) {
		String erro = "Erro ao acessar o client da AWS";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		PadraoDeErro padraoErro = new PadraoDeErro(Instant.now(), status.value(), erro, e.getMessage(), requisicao.getRequestURI());
		return ResponseEntity.status(status).body(padraoErro);
	}
	
	@ExceptionHandler(AmazonS3Exception.class)
	public ResponseEntity<PadraoDeErro> s3Amazon(AmazonS3Exception e, HttpServletRequest requisicao) {
		String erro = "Erro ao acessar o AWS S3";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		PadraoDeErro padraoErro = new PadraoDeErro(Instant.now(), status.value(), erro, e.getMessage(), requisicao.getRequestURI());
		return ResponseEntity.status(status).body(padraoErro);
	}
	
}
