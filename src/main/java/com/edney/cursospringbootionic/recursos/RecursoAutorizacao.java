package com.edney.cursospringbootionic.recursos;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edney.cursospringbootionic.seguranca.JWTUtil;
import com.edney.cursospringbootionic.seguranca.UsuarioSS;
import com.edney.cursospringbootionic.servicos.ServicoUsuario;

@RestController
@RequestMapping(value = "/autorizacao")
public class RecursoAutorizacao {
	
	@Autowired
	private JWTUtil jwtUtil;

	@PostMapping(value = "/refresh_token")
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UsuarioSS usuario = ServicoUsuario.autenticado();
		String token = jwtUtil.gerarToken(usuario.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		return ResponseEntity.noContent().build();
	}
}
