package com.edney.cursospringbootionic.servicos;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edney.cursospringbootionic.dominios.Categoria;
import com.edney.cursospringbootionic.repositorios.RepositorioCategoria;
import com.edney.cursospringbootionic.servicos.excecoes.ExcecaoObjetoNaoEncontrato;

@Service
public class ServicoCategoria {
	
	@Autowired
	private RepositorioCategoria repositorioCategoria;
	
	public Categoria buscarPeloId(Integer id) {
		Optional<Categoria> obj = repositorioCategoria.findById(id);
		
		return obj.orElseThrow(() -> new ExcecaoObjetoNaoEncontrato("Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
}
