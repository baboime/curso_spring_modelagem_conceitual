package com.edney.cursospringbootionic.servicos.excecoes;

public class ExcecaoDeAutorizacao extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ExcecaoDeAutorizacao(String msg) {
		super(msg);
	}
	
	public ExcecaoDeAutorizacao(String msg, Throwable causa) {
		super(msg, causa);
	}

}
