package com.edney.cursospringbootionic.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edney.cursospringbootionic.dominios.Pagamento;

@Repository
public interface RepositorioPagamento extends JpaRepository<Pagamento, Integer> {
	
}
