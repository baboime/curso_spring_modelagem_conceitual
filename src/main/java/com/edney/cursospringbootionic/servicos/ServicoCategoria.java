package com.edney.cursospringbootionic.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.edney.cursospringbootionic.dominios.Categoria;
import com.edney.cursospringbootionic.dto.DTOCategoria;
import com.edney.cursospringbootionic.repositorios.RepositorioCategoria;
import com.edney.cursospringbootionic.servicos.excecoes.ExcecaoIntegracaoBandoDeDados;
import com.edney.cursospringbootionic.servicos.excecoes.ExcecaoObjetoNaoEncontrato;

@Service
public class ServicoCategoria {
	
	@Autowired
	private RepositorioCategoria repositorioCategoria;
	
	public Categoria buscarPeloId(Integer id) {
		Optional<Categoria> obj = repositorioCategoria.findById(id);
		
		return obj.orElseThrow(() -> new ExcecaoObjetoNaoEncontrato("Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	public Categoria inserir(Categoria obj) {
		//para garantir que é um objeto novo informa null para o Id, pois trata-se de um metodo de inclusão
		obj.setId(null);
		return repositorioCategoria.save(obj);
	}
	
	public Categoria atualizar(Categoria obj) {
		buscarPeloId(obj.getId());
		return repositorioCategoria.save(obj);
	}
	
	public void excluir(Integer id) {
		buscarPeloId(id);
		try {
			repositorioCategoria.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new ExcecaoIntegracaoBandoDeDados("Não é possível excluir uma categoria que possui produtos");
		}
	}
	
	public List<Categoria> buscarTudo() {
		return repositorioCategoria.findAll();
	}
	
	public Page<Categoria> buscarPagina(Integer pagina, Integer linhasPorPagina, String ordenarPor, String tipoOrdenacao) {
		PageRequest requisicaoPorPagina = PageRequest.of(pagina, linhasPorPagina, Direction.valueOf(tipoOrdenacao), ordenarPor);
		return repositorioCategoria.findAll(requisicaoPorPagina);
	}
	
	//Método auxiliar para instanciar uma categoria a partir do objeto DTO
	public Categoria aPartirDoDTO(DTOCategoria objDTO) {
		return new Categoria(objDTO.getId(), objDTO.getNome());
	}
}
