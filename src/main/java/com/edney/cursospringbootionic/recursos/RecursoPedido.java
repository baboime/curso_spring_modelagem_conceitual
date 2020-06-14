package com.edney.cursospringbootionic.recursos;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.edney.cursospringbootionic.dominios.Pedido;
import com.edney.cursospringbootionic.servicos.ServicoPedido;

@RestController
@RequestMapping(value = "/pedidos")
public class RecursoPedido {
	
	@Autowired
	private ServicoPedido servicoPedido;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Pedido> buscarPeloId(@PathVariable Integer id) {
		Pedido obj = servicoPedido.buscarPeloId(id);
		return ResponseEntity.ok(obj);		
	}
	
	@PostMapping
	public ResponseEntity<Void> inserir(@Valid @RequestBody Pedido obj) {
		obj = servicoPedido.inserir(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
}
