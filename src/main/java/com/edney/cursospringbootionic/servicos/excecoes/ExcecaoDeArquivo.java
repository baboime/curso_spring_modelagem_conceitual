package com.edney.cursospringbootionic.servicos.excecoes;

public class ExcecaoDeArquivo extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ExcecaoDeArquivo(String msg) {
		super(msg);
	}
	
	public ExcecaoDeArquivo(String msg, Throwable causa) {
		super(msg, causa);
	}
}
