package com.edney.cursospringbootionic.servicos.validacao;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.edney.cursospringbootionic.dominios.Cliente;
import com.edney.cursospringbootionic.dominios.enums.TipoCliente;
import com.edney.cursospringbootionic.dto.DTONovoCliente;
import com.edney.cursospringbootionic.recursos.excecoes.MensagensDosCampos;
import com.edney.cursospringbootionic.repositorios.RepositorioCliente;
import com.edney.cursospringbootionic.servicos.validacao.uteis.BR;

public class InserirClienteValidador implements ConstraintValidator<InserirCliente, DTONovoCliente> {
	
	@Autowired
	private RepositorioCliente repositorioCliente;
	
	@Override
	public void initialize(InserirCliente ann) {
	}

	@Override
	public boolean isValid(DTONovoCliente objDto, ConstraintValidatorContext context) {
		List<MensagensDosCampos> list = new ArrayList<>();
		
		//Validações personalizadas de erros
		
		if (objDto.getTipo().equals(TipoCliente.PESSOA_FISICA.getCodigo()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new MensagensDosCampos("cpfOuCnpj", "CPF Inválido"));
		}
		
		if (objDto.getTipo().equals(TipoCliente.PESSOA_JURIDICA.getCodigo()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new MensagensDosCampos("cpfOuCnpj", "CNPJ Inválido"));
		}
		
		Cliente clienteAux = repositorioCliente.findByEmail(objDto.getEmail());
		
		if (clienteAux != null) {
			list.add(new MensagensDosCampos("email", "email já existente"));
		}
		
		for (MensagensDosCampos e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMensagem()).addPropertyNode(e.getNomeDoCampo()).addConstraintViolation();
		}
		return list.isEmpty();
	}
}

