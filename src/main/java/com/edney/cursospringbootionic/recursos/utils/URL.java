package com.edney.cursospringbootionic.recursos.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {
	
	public static String decodificarParametro(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} 
		catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	public static List<Integer> decodificarIntList(String s) {
		String[] vetor = s.split(",");
		List<Integer> lista = new ArrayList<>();
		for (int i=0; i<vetor.length; i++) {
			lista.add(Integer.parseInt(vetor[i]));
		}
		return lista;
		//seria possivel fazer este procedimento utilizando lambda, conforme abaixo:
		//return Arrays.asList(s.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
	}
}