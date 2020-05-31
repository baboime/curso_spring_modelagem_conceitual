package com.edney.cursospringbootionic.servicos.validacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.edney.cursospringbootionic.dominios.Cliente;
import com.edney.cursospringbootionic.dto.DTOCliente;
import com.edney.cursospringbootionic.recursos.excecoes.MensagensDosCampos;
import com.edney.cursospringbootionic.repositorios.RepositorioCliente;

public class AtualizarClienteValidador implements ConstraintValidator<AtualizarCliente, DTOCliente> {
	
	@Autowired
	private HttpServletRequest requisicao;
	
	@Autowired
	private RepositorioCliente repositorioCliente;
	
	@Override
	public void initialize(AtualizarCliente ann) {
	}

	@Override
	public boolean isValid(DTOCliente objDto, ConstraintValidatorContext context) {
		
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) requisicao.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));
		
		List<MensagensDosCampos> list = new ArrayList<>();
		
		//Validações personalizadas de erros
		

		Cliente clienteAux = repositorioCliente.findByEmail(objDto.getEmail());
		
		if (clienteAux != null && !clienteAux.getId().equals(uriId)) {
			list.add(new MensagensDosCampos("email", "email já existente"));
		}
		
		for (MensagensDosCampos e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMensagem()).addPropertyNode(e.getNomeDoCampo()).addConstraintViolation();
		}
		return list.isEmpty();
	}
}

