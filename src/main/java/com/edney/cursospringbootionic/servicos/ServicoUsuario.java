package com.edney.cursospringbootionic.servicos;

import org.springframework.security.core.context.SecurityContextHolder;

import com.edney.cursospringbootionic.seguranca.UsuarioSS;

public class ServicoUsuario {
	
	public static UsuarioSS autenticado( ) {
		try {
			return (UsuarioSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch (Exception e) {
			return null;
		}
	}
}
