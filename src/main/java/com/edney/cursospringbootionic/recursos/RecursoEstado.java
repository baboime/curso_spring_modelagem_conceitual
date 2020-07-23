package com.edney.cursospringbootionic.recursos;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edney.cursospringbootionic.dominios.Cidade;
import com.edney.cursospringbootionic.dominios.Estado;
import com.edney.cursospringbootionic.dto.DTOCidade;
import com.edney.cursospringbootionic.dto.DTOEstado;
import com.edney.cursospringbootionic.servicos.ServicoCidade;
import com.edney.cursospringbootionic.servicos.ServicoEstado;

@RestController
@RequestMapping(value = "/estados")
public class RecursoEstado {

	@Autowired
	private ServicoEstado servicoEstado;
	
	@Autowired
	private ServicoCidade servicoCidade;
	
	@GetMapping
	public ResponseEntity<List<DTOEstado>> buscarTudo() {
		List<Estado> lista = servicoEstado.buscarTudo();
		List<DTOEstado> listaDTO = lista.stream().map(obj -> new DTOEstado(obj)).collect(Collectors.toList());
		return ResponseEntity.ok(listaDTO);
	}
	
	@GetMapping(value = "/{estado_id}/cidades")
	public ResponseEntity<List<DTOCidade>> buscarCidades(@PathVariable("estado_id") Integer estadoId) {
		List<Cidade> lista = servicoCidade.buscarPorEstado(estadoId);
		List<DTOCidade> listaDTO = lista.stream().map(obj -> new DTOCidade(obj)).collect(Collectors.toList());
		return ResponseEntity.ok(listaDTO);
	}	
}
