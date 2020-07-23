package com.edney.cursospringbootionic.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.edney.cursospringbootionic.dominios.Estado;

@Repository
public interface RepositorioEstado extends JpaRepository<Estado, Integer> {
	
	@Transactional(readOnly = true)
	public List<Estado> findAllByOrderByNome();
	
}
