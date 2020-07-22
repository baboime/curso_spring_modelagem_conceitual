package com.edney.cursospringbootionic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.edney.cursospringbootionic.servicos.ServicoS3;

@SpringBootApplication
public class CursoSpringMcApplication implements CommandLineRunner {

	
	@Autowired
	private ServicoS3 servicoS3;
	
	public static void main(String[] args) {
		SpringApplication.run(CursoSpringMcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		servicoS3.uploadArquivo("C:\\Users\\ednne\\OneDrive\\Documentos\\Cursos\\Springboot e Ionic\\Fotos\\minions.jpg");
	}
}
