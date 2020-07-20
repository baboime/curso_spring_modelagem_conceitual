package com.edney.cursospringbootionic.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edney.cursospringbootionic.dominios.Cidade;
import com.edney.cursospringbootionic.dominios.Cliente;
import com.edney.cursospringbootionic.dominios.Endereco;
import com.edney.cursospringbootionic.dominios.enums.Perfil;
import com.edney.cursospringbootionic.dominios.enums.TipoCliente;
import com.edney.cursospringbootionic.dto.DTOCliente;
import com.edney.cursospringbootionic.dto.DTONovoCliente;
import com.edney.cursospringbootionic.repositorios.RepositorioCliente;
import com.edney.cursospringbootionic.repositorios.RepositorioEndereco;
import com.edney.cursospringbootionic.seguranca.UsuarioSS;
import com.edney.cursospringbootionic.servicos.excecoes.ExcecaoDeAutorizacao;
import com.edney.cursospringbootionic.servicos.excecoes.ExcecaoIntegracaoBandoDeDados;
import com.edney.cursospringbootionic.servicos.excecoes.ExcecaoObjetoNaoEncontrato;

@Service
public class ServicoCliente {
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private RepositorioCliente repositorioCliente;
	
	@Autowired
	private RepositorioEndereco repositorioEndereco;
	
	public Cliente buscarPeloId (Integer id) {
		
		UsuarioSS usuario = ServicoUsuario.autenticado();
		if (usuario == null || !usuario.possuiPerfil(Perfil.ADMINISTRADOR) && !id.equals(usuario.getId())) {
			throw new ExcecaoDeAutorizacao("Acesso negado");
		}
		
		Optional<Cliente> obj = repositorioCliente.findById(id);
		return obj.orElseThrow(() -> new ExcecaoObjetoNaoEncontrato("Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	@Transactional
	public Cliente inserir(Cliente obj) {
		obj.setId(null);
		obj = repositorioCliente.save(obj);
		repositorioEndereco.saveAll(obj.getEnderecos());
		return obj;
	}
	
	public Cliente atualizar(Cliente obj) {
		Cliente atualizarObj = buscarPeloId(obj.getId());
		atualizarDados(atualizarObj, obj);
		return repositorioCliente.save(atualizarObj);
	}

	public void excluir(Integer id) {
		buscarPeloId(id);
		try {
			repositorioCliente.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new ExcecaoIntegracaoBandoDeDados("Não é possível excluir porque há pedidos para este clientes");
		}
	}
	
	public List<Cliente> buscarTudo() {
		return repositorioCliente.findAll();
	}
	
	public Page<Cliente> buscarPagina(Integer pagina, Integer linhasPorPagina, String ordenarPor, String tipoOrdenacao) {
		PageRequest requisicaoPorPagina = PageRequest.of(pagina, linhasPorPagina, Direction.valueOf(tipoOrdenacao), ordenarPor);
		return repositorioCliente.findAll(requisicaoPorPagina);
	}
	
	//Método auxiliar para instanciar uma categoria a partir do objeto DTO
	public Cliente aPartirDoDTO(DTOCliente objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null, null);
	}
	
	public Cliente aPartirDoDTO(DTONovoCliente objDTO) {
		Cliente cliente = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(), TipoCliente.paraEnum(objDTO.getTipo()), pe.encode(objDTO.getSenha()));
		Cidade cidade = new Cidade(objDTO.getCidadeId(), null, null);
		Endereco endereco = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(), objDTO.getBairro(), objDTO.getCep(), cidade, cliente);
		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(objDTO.getTelefone1());
		if (objDTO.getTelefone2() != null) {
			cliente.getTelefones().add(objDTO.getTelefone2());
		}
		if (objDTO.getTelefone3() != null) {
			cliente.getTelefones().add(objDTO.getTelefone3());
		}
		return cliente;
	}
	
	private void atualizarDados(Cliente atualizarObj, Cliente obj) {
		atualizarObj.setNome(obj.getNome());
		atualizarObj.setEmail(obj.getEmail());
	}

}
