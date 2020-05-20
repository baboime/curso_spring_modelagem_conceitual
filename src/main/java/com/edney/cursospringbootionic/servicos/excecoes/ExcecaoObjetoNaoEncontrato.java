package com.edney.cursospringbootionic.servicos.excecoes;

public class ExcecaoObjetoNaoEncontrato extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ExcecaoObjetoNaoEncontrato(String msg) {
		super(msg);
	}
	
	public ExcecaoObjetoNaoEncontrato(String msg, Throwable causa) {
		super(msg, causa);
	}

}
