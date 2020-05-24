package com.edney.cursospringbootionic.recursos.excecoes;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidacaoDeErros extends PadraoDeErro {

	private static final long serialVersionUID = 1L;
	
	private List<MensagensDosCampos> erros = new ArrayList<>();

	public ValidacaoDeErros(Instant timestamp, Integer status, String erro, String mensagem, String path) {
		super(timestamp, status, erro, mensagem, path);
	}

	public List<MensagensDosCampos> getErros() {
		return erros;
	}

	public void adicionarErro(String nomeDoCampo, String mensagem) {
		erros.add(new MensagensDosCampos(nomeDoCampo, mensagem));
	}
}
