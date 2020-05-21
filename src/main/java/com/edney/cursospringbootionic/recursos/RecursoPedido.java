package com.edney.cursospringbootionic.recursos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edney.cursospringbootionic.dominios.Pedido;
import com.edney.cursospringbootionic.servicos.ServicoPedido;

@RestController
@RequestMapping(value = "/pedidos")
public class RecursoPedido {
	
	@Autowired
	private ServicoPedido servicoPedido;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> buscarPeloId(@PathVariable Integer id) {
		Pedido obj = servicoPedido.buscarPeloId(id);
		return ResponseEntity.ok(obj);		
	}
}
