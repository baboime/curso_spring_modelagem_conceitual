package com.edney.cursospringbootionic.repositorios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.edney.cursospringbootionic.dominios.Cliente;
import com.edney.cursospringbootionic.dominios.Pedido;

@Repository
public interface RepositorioPedido extends JpaRepository<Pedido, Integer> {
	
	@Transactional(readOnly = true)
	Page<Pedido> findByCliente(Cliente cliente, Pageable requisicaoPagina);
}
