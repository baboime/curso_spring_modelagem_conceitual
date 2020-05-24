package com.edney.cursospringbootionic.servicos.excecoes;

public class ExcecaoIntegracaoBandoDeDados extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ExcecaoIntegracaoBandoDeDados(String msg) {
		super(msg);
	}
	
	public ExcecaoIntegracaoBandoDeDados(String msg, Throwable causa) {
		super(msg, causa);
	}

}
