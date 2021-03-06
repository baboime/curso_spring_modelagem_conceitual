package com.edney.cursospringbootionic.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edney.cursospringbootionic.dominios.Endereco;

@Repository
public interface RepositorioEndereco extends JpaRepository<Endereco, Integer> {
	
}
