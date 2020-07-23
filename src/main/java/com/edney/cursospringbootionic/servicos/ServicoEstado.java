package com.edney.cursospringbootionic.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edney.cursospringbootionic.dominios.Estado;
import com.edney.cursospringbootionic.repositorios.RepositorioEstado;

@Service
public class ServicoEstado {
	
	@Autowired
	private RepositorioEstado repositorioEstado;
	
	public List<Estado> buscarTudo() {
		return repositorioEstado.findAllByOrderByNome();
	}
}
