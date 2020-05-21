package com.edney.cursospringbootionic.servicos;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edney.cursospringbootionic.dominios.Pedido;
import com.edney.cursospringbootionic.repositorios.RepositorioPedido;
import com.edney.cursospringbootionic.servicos.excecoes.ExcecaoObjetoNaoEncontrato;

@Service
public class ServicoPedido {
	
	@Autowired
	private RepositorioPedido repositorioPedido;
	
	public Pedido buscarPeloId(Integer id) {
		Optional<Pedido> obj = repositorioPedido.findById(id);
		
		return obj.orElseThrow(() -> new ExcecaoObjetoNaoEncontrato("Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
	
}
