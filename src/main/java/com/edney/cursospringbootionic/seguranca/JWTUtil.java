package com.edney.cursospringbootionic.seguranca;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {

	@Value("${jwt.secret}")
	private String segredo;
	
	@Value("${jwt.expiration}")
	private Long expiracao;
	
	public String gerarToken(String usuario) {
		return Jwts.builder()
				.setSubject(usuario)
				.setExpiration(new Date(System.currentTimeMillis() + expiracao))
				.signWith(SignatureAlgorithm.HS512, segredo.getBytes())
				.compact();
	}
}
