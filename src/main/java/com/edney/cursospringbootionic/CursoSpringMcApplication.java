package com.edney.cursospringbootionic;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.edney.cursospringbootionic.dominios.Categoria;
import com.edney.cursospringbootionic.dominios.Cidade;
import com.edney.cursospringbootionic.dominios.Estado;
import com.edney.cursospringbootionic.dominios.Produto;
import com.edney.cursospringbootionic.repositorios.RepositorioCategoria;
import com.edney.cursospringbootionic.repositorios.RepositorioCidade;
import com.edney.cursospringbootionic.repositorios.RepositorioEstado;
import com.edney.cursospringbootionic.repositorios.RepositorioProduto;

@SpringBootApplication
public class CursoSpringMcApplication implements CommandLineRunner {
	
	@Autowired
	private RepositorioCategoria repositorioCategoria;
	
	@Autowired
	private RepositorioProduto repositorioProduto;
	
	@Autowired
	private RepositorioEstado repositorioEstado;
	
	@Autowired
	private RepositorioCidade repositorioCidade;

	public static void main(String[] args) {
		SpringApplication.run(CursoSpringMcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		
		repositorioCategoria.saveAll(Arrays.asList(cat1, cat2));
		repositorioProduto.saveAll(Arrays.asList(p1, p2, p3));
		
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		repositorioEstado.saveAll(Arrays.asList(est1, est2));
		repositorioCidade.saveAll(Arrays.asList(c1, c2, c3));
	
	}
}
