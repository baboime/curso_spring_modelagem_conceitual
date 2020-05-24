package com.edney.cursospringbootionic.recursos.excecoes;

import java.io.Serializable;

public class MensagensDosCampos implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String nomeDoCampo;
	private String mensagem;
	
	public MensagensDosCampos() {
	}

	public MensagensDosCampos(String nomeDoCampo, String mensagem) {
		super();
		this.nomeDoCampo = nomeDoCampo;
		this.mensagem = mensagem;
	}

	public String getNomeDoCampo() {
		return nomeDoCampo;
	}

	public void setNomeDoCampo(String nomeDoCampo) {
		this.nomeDoCampo = nomeDoCampo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}
