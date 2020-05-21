package com.edney.cursospringbootionic.servicos;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edney.cursospringbootionic.dominios.Cliente;
import com.edney.cursospringbootionic.repositorios.RepositorioCliente;
import com.edney.cursospringbootionic.servicos.excecoes.ExcecaoObjetoNaoEncontrato;

@Service
public class ServicoCliente {
	
	@Autowired
	private RepositorioCliente repositorioCliente;
	
	public Cliente buscarPeloId (Integer id) {
		Optional<Cliente> obj = repositorioCliente.findById(id);
		return obj.orElseThrow(() -> new ExcecaoObjetoNaoEncontrato("Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

}
