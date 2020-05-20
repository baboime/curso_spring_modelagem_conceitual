package com.edney.cursospringbootionic.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edney.cursospringbootionic.dominios.Cidade;

@Repository
public interface RepositorioCidade extends JpaRepository<Cidade, Integer> {
	
}
