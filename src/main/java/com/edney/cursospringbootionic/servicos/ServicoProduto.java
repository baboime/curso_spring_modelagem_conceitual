package com.edney.cursospringbootionic.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.edney.cursospringbootionic.dominios.Categoria;
import com.edney.cursospringbootionic.dominios.Produto;
import com.edney.cursospringbootionic.repositorios.RepositorioCategoria;
import com.edney.cursospringbootionic.repositorios.RepositorioProduto;
import com.edney.cursospringbootionic.servicos.excecoes.ExcecaoObjetoNaoEncontrato;

@Service
public class ServicoProduto {
	
	@Autowired
	private RepositorioProduto repositorioProduto;
	
	@Autowired
	private RepositorioCategoria repositorioCategoria;
	
	public Produto buscarPeloId(Integer id) {
		Optional<Produto> obj = repositorioProduto.findById(id);
		
		return obj.orElseThrow(() -> new ExcecaoObjetoNaoEncontrato("Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
	}
	
	public Page<Produto> buscar(String nome, List<Integer> ids, Integer pagina, Integer linhasPorPagina, String ordenarPor, String tipoOrdenacao) {
		PageRequest requisicaoPorPagina = PageRequest.of(pagina, linhasPorPagina, Direction.valueOf(tipoOrdenacao), ordenarPor);
		List<Categoria> categorias = repositorioCategoria.findAllById(ids);
		return repositorioProduto.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, requisicaoPorPagina);
	}

}
