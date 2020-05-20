package com.edney.cursospringbootionic.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edney.cursospringbootionic.dominios.Estado;

@Repository
public interface RepositorioEstado extends JpaRepository<Estado, Integer> {
	
}
