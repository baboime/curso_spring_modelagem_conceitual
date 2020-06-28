package com.edney.cursospringbootionic.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.edney.cursospringbootionic.servicos.ServicoDB;
import com.edney.cursospringbootionic.servicos.ServicoEmail;
import com.edney.cursospringbootionic.servicos.ServicoEmailSmtp;

@Configuration
@Profile("dev")
public class DesenvolvimentoConfig {
	
	@Autowired
	private ServicoDB servicoDB;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String estrategia;
	
	@Bean
	public boolean instanciarBancoDeDados() throws ParseException {
		
		if (!"create".equals(estrategia)) {
			return false;
		}
		servicoDB.instanciarBandoDeDadosDeTestes();
		return true;
	}
	
	@Bean
	public ServicoEmail servicoEmail() {
		return new ServicoEmailSmtp();
	}
}