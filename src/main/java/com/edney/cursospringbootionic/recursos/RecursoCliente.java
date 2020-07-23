package com.edney.cursospringbootionic.recursos;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.edney.cursospringbootionic.dominios.Cliente;
import com.edney.cursospringbootionic.dto.DTOCliente;
import com.edney.cursospringbootionic.dto.DTONovoCliente;
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
	
	@PostMapping
	public ResponseEntity<Void> inserir(@Valid @RequestBody DTONovoCliente objDTO) {
		Cliente obj = servicoCliente.aPartirDoDTO(objDTO);
		obj = servicoCliente.inserir(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> atualizar(@Valid @RequestBody DTOCliente objDTO, @PathVariable Integer id) {
		Cliente obj = servicoCliente.aPartirDoDTO(objDTO);
		obj.setId(id);
		obj = servicoCliente.atualizar(obj);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Integer id) {
		servicoCliente.excluir(id);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	@GetMapping
	public ResponseEntity<List<DTOCliente>> buscarTudo() {
		List<Cliente> lista = servicoCliente.buscarTudo();
		List<DTOCliente> listaDTO = lista.stream().map(obj -> new DTOCliente(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);	
	}
	
	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	@GetMapping(value = "/pagina")
	public ResponseEntity<Page<DTOCliente>> buscarPagina(
			@RequestParam(value = "pagina", defaultValue = "0") Integer pagina, 
			@RequestParam(value = "linhasPorPagina", defaultValue = "24")Integer linhasPorPagina, 
			@RequestParam(value = "ordenarPor", defaultValue = "nome")String ordenarPor, 
			@RequestParam(value = "tipoOrdenacao", defaultValue = "ASC")String tipoOrdenacao) {
		Page<Cliente> lista = servicoCliente.buscarPagina(pagina, linhasPorPagina, ordenarPor, tipoOrdenacao);
		Page<DTOCliente> listaDTO = lista.map(obj -> new DTOCliente(obj));
		return ResponseEntity.ok().body(listaDTO);	
	}
	
	@PostMapping(value="/fotoperfil")
	public ResponseEntity<Void> uploadFotoDoPerfil(@RequestParam(name = "foto") MultipartFile foto) {
		URI uri = servicoCliente.uploadFotoDoPerfil(foto);
		return ResponseEntity.created(uri).build();
	}
}
