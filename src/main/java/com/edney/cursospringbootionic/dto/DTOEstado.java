package com.edney.cursospringbootionic.dto;

import java.io.Serializable;

import com.edney.cursospringbootionic.dominios.Estado;

public class DTOEstado implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	
	public DTOEstado() {
	}

	public DTOEstado(Estado obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}