package com.edney.cursospringbootionic.repositorios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.edney.cursospringbootionic.dominios.Categoria;
import com.edney.cursospringbootionic.dominios.Produto;

@Repository

public interface RepositorioProduto extends JpaRepository<Produto, Integer> {
	
	//@Transactional(readOnly = true)
	//@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
	//Page<Produto> search(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable requisicaoPorPagina);
	
	//outra maneira sem utilizar a notação Query utilizando palavras chaves dentro dos nomes de métodos
	@Transactional(readOnly = true)
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(String nome, List<Categoria> categorias, Pageable requisicaoPorPagina);
	}
