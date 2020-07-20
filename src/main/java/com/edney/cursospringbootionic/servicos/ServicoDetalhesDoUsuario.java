package com.edney.cursospringbootionic.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.edney.cursospringbootionic.dominios.Cliente;
import com.edney.cursospringbootionic.repositorios.RepositorioCliente;
import com.edney.cursospringbootionic.seguranca.UsuarioSS;


@Service
public class ServicoDetalhesDoUsuario implements UserDetailsService {

	@Autowired
	private RepositorioCliente repositorioCliente;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente cli = repositorioCliente.findByEmail(email);
		if (cli == null) {
			throw new UsernameNotFoundException(email);
		}
		return new UsuarioSS(cli.getId(), cli.getEmail(), cli.getSenha(), cli.getPerfis());
	}
}
