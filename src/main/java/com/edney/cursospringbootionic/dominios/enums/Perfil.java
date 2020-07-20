package com.edney.cursospringbootionic.dominios.enums;

public enum Perfil {
	
	ADMINISTRADOR(1, "ROLE_ADMINISTRADOR"),
	CLIENTE(2, "ROLE_CLIENTE");
	
	private int codigo;
	private String descricao;
	
	private Perfil(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static Perfil paraEnum(Integer codigo) {
		
		if (codigo == null) {
			return null;
		}
		
		for (Perfil x : Perfil.values()) {
			if (codigo.equals(x.getCodigo())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Codigo inválido" + codigo);
	}

}
