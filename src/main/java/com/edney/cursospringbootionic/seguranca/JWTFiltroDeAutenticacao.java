package com.edney.cursospringbootionic.seguranca;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.edney.cursospringbootionic.dto.DTOCredenciais;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTFiltroDeAutenticacao extends UsernamePasswordAuthenticationFilter {
	
	private AuthenticationManager gerenciadorDeAutenticacao;
	
	private JWTUtil jwtUtil;
	
	public JWTFiltroDeAutenticacao(AuthenticationManager gerenciadorDeAutenticacao, JWTUtil jwtUtil) {
		setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
        this.gerenciadorDeAutenticacao = gerenciadorDeAutenticacao;
        this.jwtUtil = jwtUtil;
    }
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest requisicao, HttpServletResponse resposta) throws AuthenticationException {
		
		try {
			DTOCredenciais credenciais = new ObjectMapper().readValue(requisicao.getInputStream(), DTOCredenciais.class);
			
			UsernamePasswordAuthenticationToken tokenAutorizacao = new UsernamePasswordAuthenticationToken(credenciais.getEmail(), credenciais.getSenha(), new ArrayList<>());
			
			Authentication autorizacao = gerenciadorDeAutenticacao.authenticate(tokenAutorizacao);
			
			return autorizacao;
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest requisicao, HttpServletResponse resposta, FilterChain chain, Authentication autorizacao) throws IOException, ServletException {
		String usuario = ((UsuarioSS) autorizacao.getPrincipal()).getUsername();
		String token = jwtUtil.gerarToken(usuario);
		resposta.addHeader("Authorization", "Bearer " + token);
		resposta.addHeader("access-control-expose-headers", "Authorization");
	}
	
	private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {
		 
        @Override
        public void onAuthenticationFailure(HttpServletRequest requisicao, HttpServletResponse resposta, AuthenticationException exception)
                throws IOException, ServletException {
        	resposta.setStatus(401);
        	resposta.setContentType("application/json"); 
        	resposta.getWriter().append(json());
        }
        
        private String json() {
            long date = new Date().getTime();
            return "{\"timestamp\": " + date + ", "
                + "\"status\": 401, "
                + "\"error\": \"Não autorizado\", "
                + "\"message\": \"Email ou senha inválidos\", "
                + "\"path\": \"/login\"}";
        }
    }
}
