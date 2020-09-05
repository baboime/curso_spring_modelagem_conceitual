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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.edney.cursospringbootionic.dominios.Categoria;
import com.edney.cursospringbootionic.dto.DTOCategoria;
import com.edney.cursospringbootionic.servicos.ServicoCategoria;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/categorias")
public class RecursoCategoria {
	
	@Autowired
	private ServicoCategoria servicoCategoria;
	
	@ApiOperation(value="Busca por id")
	@GetMapping(value = "/{id}")
	public ResponseEntity<Categoria> buscarPeloId(@PathVariable Integer id) {
		Categoria obj = servicoCategoria.buscarPeloId(id);
		return ResponseEntity.ok().body(obj);		
	}
	
	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	@ApiOperation(value="Insere categoria")
	@PostMapping
	public ResponseEntity<Void> inserir(@Valid @RequestBody DTOCategoria objDTO) {
		Categoria obj = servicoCategoria.aPartirDoDTO(objDTO);
		obj = servicoCategoria.inserir(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	@ApiOperation(value="Atualiza categoria")
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> atualizar(@Valid @RequestBody DTOCategoria objDTO, @PathVariable Integer id) {
		Categoria obj = servicoCategoria.aPartirDoDTO(objDTO);
		obj.setId(id);
		obj = servicoCategoria.atualizar(obj);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMINISTRADOR')")
	@ApiOperation(value="Remove categoria")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Não é possível excluir uma categoria que possui produtos"),
			@ApiResponse(code = 404, message = "Código inexistente") })
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Integer id) {
		servicoCategoria.excluir(id);
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value="Retorna todas categorias")
	@GetMapping
	public ResponseEntity<List<DTOCategoria>> buscarTudo() {
		List<Categoria> lista = servicoCategoria.buscarTudo();
		List<DTOCategoria> listaDTO = lista.stream().map(obj -> new DTOCategoria(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDTO);	
	}
	
	@ApiOperation(value="Retorna todas categorias com paginação")
	@GetMapping(value = "/pagina")
	public ResponseEntity<Page<DTOCategoria>> buscarPagina(
			@RequestParam(value = "pagina", defaultValue = "0") Integer pagina, 
			@RequestParam(value = "linhasPorPagina", defaultValue = "24")Integer linhasPorPagina, 
			@RequestParam(value = "ordenarPor", defaultValue = "nome")String ordenarPor, 
			@RequestParam(value = "tipoOrdenacao", defaultValue = "ASC")String tipoOrdenacao) {
		Page<Categoria> lista = servicoCategoria.buscarPagina(pagina, linhasPorPagina, ordenarPor, tipoOrdenacao);
		Page<DTOCategoria> listaDTO = lista.map(obj -> new DTOCategoria(obj));
		return ResponseEntity.ok().body(listaDTO);	
	}
}
