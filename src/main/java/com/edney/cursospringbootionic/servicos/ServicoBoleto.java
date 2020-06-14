package com.edney.cursospringbootionic.servicos;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.edney.cursospringbootionic.dominios.PagamentoComBoleto;

@Service
public class ServicoBoleto {
	
	public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instanteDoPedido) {
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(instanteDoPedido);
		calendario.add(Calendar.DAY_OF_MONTH, 7);
		pagto.setDataVencimento(calendario.getTime());
	}
}
