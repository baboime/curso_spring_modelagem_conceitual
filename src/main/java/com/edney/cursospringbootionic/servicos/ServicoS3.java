package com.edney.cursospringbootionic.servicos;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.edney.cursospringbootionic.servicos.excecoes.ExcecaoDeArquivo;

@Service
public class ServicoS3 {

	private Logger LOG = LoggerFactory.getLogger(ServicoS3.class);

	@Autowired
	private AmazonS3 s3client;

	@Value("${s3.bucket}")
	private String nomeBucket;

	public URI uploadArquivo(MultipartFile multipartFile) {
		try {
			String nomeDoArquivo = multipartFile.getOriginalFilename();
			InputStream is = multipartFile.getInputStream();
			String tipoDoConteudo = multipartFile.getContentType();
			return uploadArquivo(is, nomeDoArquivo, tipoDoConteudo);
		} 
		catch (IOException e) {
			throw new ExcecaoDeArquivo("Erro de IO: " + e.getMessage());
		}
	}
	
	public URI uploadArquivo(InputStream is, String nomeDoArquivo, String tipoDoConteudo) {
		try {
			ObjectMetadata om = new ObjectMetadata();
			om.setContentType(tipoDoConteudo);
			LOG.info("Iniciando upload");
			s3client.putObject(nomeBucket, nomeDoArquivo, is, om);
			LOG.info("Upload finalizado");
			return s3client.getUrl(nomeBucket, nomeDoArquivo).toURI();
		} 
		catch (URISyntaxException e) {
			throw new ExcecaoDeArquivo("Erro ao converter URL para URI");
		}
	}
}
