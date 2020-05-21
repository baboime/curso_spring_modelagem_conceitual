package com.edney.cursospringbootionic.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edney.cursospringbootionic.dominios.ItemPedido;

@Repository
public interface RepositorioItemPedido extends JpaRepository<ItemPedido, Integer> {
	
}
