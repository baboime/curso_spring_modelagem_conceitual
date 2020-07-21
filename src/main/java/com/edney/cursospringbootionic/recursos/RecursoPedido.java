package com.edney.cursospringbootionic.recursos;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.edney.cursospringbootionic.dominios.Categoria;
import com.edney.cursospringbootionic.dominios.Pedido;
import com.edney.cursospringbootionic.dto.DTOCategoria;
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
	
	@GetMapping
	public ResponseEntity<Page<Pedido>> buscarPagina(
			@RequestParam(value = "pagina", defaultValue = "0") Integer pagina, 
			@RequestParam(value = "linhasPorPagina", defaultValue = "24")Integer linhasPorPagina, 
			@RequestParam(value = "ordenarPor", defaultValue = "instante")String ordenarPor, 
			@RequestParam(value = "tipoOrdenacao", defaultValue = "DESC")String tipoOrdenacao) {
		Page<Pedido> lista = servicoPedido.buscarPagina(pagina, linhasPorPagina, ordenarPor, tipoOrdenacao);
		return ResponseEntity.ok().body(lista);	
	}
}
