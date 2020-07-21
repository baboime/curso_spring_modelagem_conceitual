package com.edney.cursospringbootionic.servicos;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.edney.cursospringbootionic.dominios.Cliente;
import com.edney.cursospringbootionic.repositorios.RepositorioCliente;
import com.edney.cursospringbootionic.servicos.excecoes.ExcecaoObjetoNaoEncontrato;

@Service
public class ServicoAutorizacao {
	
	@Autowired
	private RepositorioCliente repositorioCliente;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private ServicoEmail servicoEmail;
	
	private Random rand = new Random();

	public void enviarNovaSenha(String email) {
		
		Cliente cliente = repositorioCliente.findByEmail(email);
		if (cliente == null) {
			throw new ExcecaoObjetoNaoEncontrato("Email não encontrado");
		}
		
		String novaSenha = novaSenha();
		cliente.setSenha(pe.encode(novaSenha));
		
		repositorioCliente.save(cliente);
		servicoEmail.enviarNovaSenhaEmail(cliente, novaSenha);
	}

	private String novaSenha() {
		char[] vet = new char[10];
		for (int i=0; i<10; i++) {
			vet[i] = caracterAleatorio();
		}
		return new String(vet);
	}

	private char caracterAleatorio() {
		int opcao = rand.nextInt(3);
		if (opcao == 0) { // gerar um digito
			return (char) (rand.nextInt(10) + 48);
		}
		else if (opcao == 1) {// gerar letra maiuscula
			return (char) (rand.nextInt(26) + 65);
		}
		else {// gerar letra minuscula
			return (char) (rand.nextInt(26) + 97);
		}
	}
}
