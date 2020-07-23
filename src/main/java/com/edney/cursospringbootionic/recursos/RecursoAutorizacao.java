package com.edney.cursospringbootionic.recursos;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edney.cursospringbootionic.dto.DTOEmail;
import com.edney.cursospringbootionic.seguranca.JWTUtil;
import com.edney.cursospringbootionic.seguranca.UsuarioSS;
import com.edney.cursospringbootionic.servicos.ServicoAutorizacao;
import com.edney.cursospringbootionic.servicos.ServicoUsuario;

@RestController
@RequestMapping(value = "/autorizacao")
public class RecursoAutorizacao {
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private ServicoAutorizacao servicoAutorizacao;

	@PostMapping(value = "/refresh_token")
	public ResponseEntity<Void> refreshToken(HttpServletResponse resposta) {
		UsuarioSS usuario = ServicoUsuario.autenticado();
		String token = jwtUtil.gerarToken(usuario.getUsername());
		resposta.addHeader("Authorization", "Bearer " + token);
		resposta.addHeader("access-control-expose-headers", "Authorization");
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping(value = "/esqueci")
	public ResponseEntity<Void> esqueciSenha(@Valid @RequestBody DTOEmail objDTO) {
		servicoAutorizacao.enviarNovaSenha(objDTO.getEmail());		
		return ResponseEntity.noContent().build();
	}
}
