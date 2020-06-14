package com.edney.cursospringbootionic.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.edney.cursospringbootionic.servicos.ServicoDB;

@Configuration
@Profile("test")
public class TesteConfig {
	
	@Autowired
	private ServicoDB servicoDB;
	
	@Bean
	public boolean instanciarBancoDeDados() throws ParseException {
		servicoDB.instanciarBandoDeDadosDeTestes();
		return true;
	}
}