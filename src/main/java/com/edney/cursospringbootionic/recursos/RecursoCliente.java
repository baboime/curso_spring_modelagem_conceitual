package com.edney.cursospringbootionic.recursos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edney.cursospringbootionic.dominios.Cliente;
import com.edney.cursospringbootionic.servicos.ServicoCliente;

@RestController
@RequestMapping(value = "/clientes")
public class RecursoCliente {
	
	@Autowired
	ServicoCliente servicoCliente;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Cliente> buscarPeloId(@PathVariable Integer id) {
		Cliente obj = servicoCliente.buscarPeloId(id);
		return ResponseEntity.ok(obj);
	}
}
