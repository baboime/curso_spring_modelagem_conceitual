package com.edney.cursospringbootionic.recursos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edney.cursospringbootionic.dominios.Categoria;
import com.edney.cursospringbootionic.servicos.ServicoCategoria;

@RestController
@RequestMapping(value = "/categorias")
public class RecursoCategoria {
	
	@Autowired
	private ServicoCategoria servicoCategoria;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> buscarPeloId(@PathVariable Integer id) {
		Categoria obj = servicoCategoria.buscarPeloId(id);
		return ResponseEntity.ok(obj);		
	}
}
