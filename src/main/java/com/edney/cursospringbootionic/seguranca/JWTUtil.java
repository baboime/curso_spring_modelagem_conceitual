package com.edney.cursospringbootionic.seguranca;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
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
	
	public boolean tokenValido(String token) {
		Claims claims = getClaims(token);
		
		if (claims != null) {
			String usuario = claims.getSubject();
			Date dataExpiracao = claims.getExpiration();
			Date dataAgora = new Date(System.currentTimeMillis());
			if (usuario != null && dataExpiracao != null && dataAgora.before(dataExpiracao)) {
				return true;
			}
		}
		return false;		
	}
	
	public String getUsuario(String token) {
		Claims claims = getClaims(token);
		
		if (claims != null) {
			return claims.getSubject();
		}
		return null;
	}

	private Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(segredo.getBytes()).parseClaimsJws(token).getBody();
		}
		catch (Exception e) {
			return null;
		}
	}
}
