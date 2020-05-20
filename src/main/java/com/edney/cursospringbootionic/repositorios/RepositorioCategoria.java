package com.edney.cursospringbootionic.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edney.cursospringbootionic.dominios.Categoria;

@Repository
public interface RepositorioCategoria extends JpaRepository<Categoria, Integer> {
	
}
