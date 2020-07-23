package com.edney.cursospringbootionic.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edney.cursospringbootionic.dominios.Cidade;
import com.edney.cursospringbootionic.repositorios.RepositorioCidade;

@Service
public class ServicoCidade {
	
	@Autowired
	private RepositorioCidade repositorioCidade;
	
	public List<Cidade> buscarPorEstado(Integer estadoId) {
		return repositorioCidade.findCidadesByEstadoId(estadoId);
	}
}
