package com.edney.cursospringbootionic.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.edney.cursospringbootionic.dominios.Cidade;

@Repository
public interface RepositorioCidade extends JpaRepository<Cidade, Integer> {
	
	// Opção utilizando query
	//@Transactional(readOnly = true)
	//@Query("SELECT obj FROM Cidade obj WHERE obj.estado.id = :estadoId ORDER BY obj.nome")
	//public List<Cidade> buscarCidades(@Param("estadoId") Integer estado_id);
	
	//Opção utilizando metodos do JPA
	@Transactional(readOnly = true)
	public List<Cidade> findCidadesByEstadoId(Integer estadoId);
	
}