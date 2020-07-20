package com.edney.cursospringbootionic.seguranca;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.edney.cursospringbootionic.servicos.ServicoDetalhesDoUsuario;

public class JWTFiltroDeAutorizacao extends BasicAuthenticationFilter {
	
	private JWTUtil jwtUtil;
	
	private ServicoDetalhesDoUsuario servicoDetalhesDoUsuario;
	
	public JWTFiltroDeAutorizacao(AuthenticationManager authenticationManager, JWTUtil jwtUtil, ServicoDetalhesDoUsuario servicoDetalhesDoUsuario) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
		this.servicoDetalhesDoUsuario = servicoDetalhesDoUsuario;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest requisicao, HttpServletResponse resposta, FilterChain chain) throws IOException, ServletException {
		String header = requisicao.getHeader("Authorization");
		
		if (header != null && header.startsWith("Bearer ")) {
			UsernamePasswordAuthenticationToken autorizacao = obterAutenticacao(header.substring(7));
			if (autorizacao != null) {
				SecurityContextHolder.getContext().setAuthentication(autorizacao);
			}
		}
		chain.doFilter(requisicao, resposta);
	}

	private UsernamePasswordAuthenticationToken obterAutenticacao(String token) {
		if (jwtUtil.tokenValido(token)) {
			String usuario = jwtUtil.getUsuario(token);
			UserDetails user = servicoDetalhesDoUsuario.loadUserByUsername(usuario);
			return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		}
		return null;
	}
}
