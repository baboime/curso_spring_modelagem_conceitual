package com.edney.cursospringbootionic.recursos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edney.cursospringbootionic.dominios.Produto;
import com.edney.cursospringbootionic.dto.DTOProduto;
import com.edney.cursospringbootionic.recursos.utils.URL;
import com.edney.cursospringbootionic.servicos.ServicoProduto;

@RestController
@RequestMapping(value = "/produtos")
public class RecursoProduto {
	
	@Autowired
	private ServicoProduto servicoProduto;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Produto> buscarPeloId(@PathVariable Integer id) {
		Produto obj = servicoProduto.buscarPeloId(id);
		return ResponseEntity.ok(obj);		
	}
	
	@GetMapping
	public ResponseEntity<Page<DTOProduto>> buscarPagina(
			@RequestParam(value = "nome", defaultValue = "") String nome,
			@RequestParam(value = "categorias", defaultValue = "") String categorias,
			@RequestParam(value = "pagina", defaultValue = "0") Integer pagina, 
			@RequestParam(value = "linhasPorPagina", defaultValue = "24")Integer linhasPorPagina, 
			@RequestParam(value = "ordenarPor", defaultValue = "nome")String ordenarPor, 
			@RequestParam(value = "tipoOrdenacao", defaultValue = "ASC")String tipoOrdenacao) {
		String nomeDecodificado = URL.decodificarParametro(nome);
		List<Integer> ids = URL.decodificarIntList(categorias);
		Page<Produto> lista = servicoProduto.buscar(nomeDecodificado, ids, pagina, linhasPorPagina, ordenarPor, tipoOrdenacao);
		Page<DTOProduto> listaDTO = lista.map(obj -> new DTOProduto(obj));
		return ResponseEntity.ok().body(listaDTO);	
	}
}
