package com.edney.cursospringbootionic;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.edney.cursospringbootionic.dominios.Categoria;
import com.edney.cursospringbootionic.dominios.Cidade;
import com.edney.cursospringbootionic.dominios.Cliente;
import com.edney.cursospringbootionic.dominios.Endereco;
import com.edney.cursospringbootionic.dominios.Estado;
import com.edney.cursospringbootionic.dominios.ItemPedido;
import com.edney.cursospringbootionic.dominios.Pagamento;
import com.edney.cursospringbootionic.dominios.PagamentoComBoleto;
import com.edney.cursospringbootionic.dominios.PagamentoComCartao;
import com.edney.cursospringbootionic.dominios.Pedido;
import com.edney.cursospringbootionic.dominios.Produto;
import com.edney.cursospringbootionic.dominios.enums.EstadoPagamento;
import com.edney.cursospringbootionic.dominios.enums.TipoCliente;
import com.edney.cursospringbootionic.repositorios.RepositorioCategoria;
import com.edney.cursospringbootionic.repositorios.RepositorioCidade;
import com.edney.cursospringbootionic.repositorios.RepositorioCliente;
import com.edney.cursospringbootionic.repositorios.RepositorioEndereco;
import com.edney.cursospringbootionic.repositorios.RepositorioEstado;
import com.edney.cursospringbootionic.repositorios.RepositorioItemPedido;
import com.edney.cursospringbootionic.repositorios.RepositorioPagamento;
import com.edney.cursospringbootionic.repositorios.RepositorioPedido;
import com.edney.cursospringbootionic.repositorios.RepositorioProduto;

@SpringBootApplication
public class CursoSpringMcApplication implements CommandLineRunner {
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	@Autowired
	private RepositorioCategoria repositorioCategoria;

	@Autowired
	private RepositorioProduto repositorioProduto;

	@Autowired
	private RepositorioEstado repositorioEstado;

	@Autowired
	private RepositorioCidade repositorioCidade;
	
	@Autowired
	private RepositorioCliente repositorioCliente;
	
	@Autowired
	private RepositorioEndereco repositorioEndereco;
	
	@Autowired
	private RepositorioPedido repositorioPedido;
	
	@Autowired
	private RepositorioPagamento repositorioPagamento;
	
	@Autowired
	private RepositorioItemPedido repositorioItemPedido;

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
		
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36278912377", TipoCliente.PESSOA_FISICA);
		
		cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));
				
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardim", "38220834", c1, cli1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", c2, cli1);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		repositorioCliente.saveAll(Arrays.asList(cli1));
		repositorioEndereco.saveAll(Arrays.asList(e1, e2));
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
	
		repositorioPedido.saveAll(Arrays.asList(ped1, ped2));
		repositorioPagamento.saveAll(Arrays.asList(pagto1, pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		repositorioItemPedido.saveAll(Arrays.asList(ip1, ip2, ip3));
		
	}
}
