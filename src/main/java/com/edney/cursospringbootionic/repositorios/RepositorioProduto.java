package com.edney.cursospringbootionic.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edney.cursospringbootionic.dominios.Produto;

@Repository
public interface RepositorioProduto extends JpaRepository<Produto, Integer> {
	
}
