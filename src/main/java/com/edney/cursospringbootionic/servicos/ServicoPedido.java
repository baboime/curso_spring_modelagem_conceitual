package com.edney.cursospringbootionic.servicos;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edney.cursospringbootionic.dominios.ItemPedido;
import com.edney.cursospringbootionic.dominios.PagamentoComBoleto;
import com.edney.cursospringbootionic.dominios.Pedido;
import com.edney.cursospringbootionic.dominios.enums.EstadoPagamento;
import com.edney.cursospringbootionic.repositorios.RepositorioItemPedido;
import com.edney.cursospringbootionic.repositorios.RepositorioPagamento;
import com.edney.cursospringbootionic.repositorios.RepositorioPedido;
import com.edney.cursospringbootionic.servicos.excecoes.ExcecaoObjetoNaoEncontrato;

@Service
public class ServicoPedido {
	
	@Autowired
	private RepositorioPedido repositorioPedido;
	
	@Autowired
	private ServicoBoleto servicoBoleto;
	
	@Autowired
	private RepositorioPagamento repositorioPagamento;
	
	@Autowired
	private ServicoProduto servicoProduto;
	
	@Autowired
	private RepositorioItemPedido repositorioItemPedido;
	
	@Autowired
	private ServicoCliente servicoCliente;
	
	@Autowired
	private ServicoEmail servicoEmail;
	
	
	public Pedido buscarPeloId(Integer id) {
		Optional<Pedido> obj = repositorioPedido.findById(id);
		
		return obj.orElseThrow(() -> new ExcecaoObjetoNaoEncontrato("Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
	
	@Transactional
	public Pedido inserir(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(servicoCliente.buscarPeloId(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			servicoBoleto.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repositorioPedido.save(obj);
		repositorioPagamento.save(obj.getPagamento());
		for(ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(servicoProduto.buscarPeloId(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		repositorioItemPedido.saveAll(obj.getItens());
		servicoEmail.enviarPedidoDeConfirmacaoDeEmailHtml(obj);
		return obj;
	}
}
