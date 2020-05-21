package com.edney.cursospringbootionic.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edney.cursospringbootionic.dominios.Cliente;

@Repository
public interface RepositorioCliente extends JpaRepository<Cliente, Integer> {
	
}
