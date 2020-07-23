package com.edney.cursospringbootionic.servicos;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.edney.cursospringbootionic.servicos.excecoes.ExcecaoDeArquivo;

@Service
public class ServicoDeImagem {

	public BufferedImage obterImagemJpgDoArquivo(MultipartFile arquivoCarregado) {
		String extensao = FilenameUtils.getExtension(arquivoCarregado.getOriginalFilename());
		if (!"png".equalsIgnoreCase(extensao) && !"jpg".equalsIgnoreCase(extensao)) {
			throw new ExcecaoDeArquivo("Somente imagens PNG e JPG são permitidas");
		}
		
		try {
			BufferedImage imagem = ImageIO.read(arquivoCarregado.getInputStream());
			if ("png".equalsIgnoreCase(extensao)) {
				imagem = pngParaJpg(imagem);
			}
			return imagem;
		} 
		catch (IOException e) {
			throw new ExcecaoDeArquivo("Erro ao ler arquivo");
		}
	}

	public BufferedImage pngParaJpg(BufferedImage imagem) {
		BufferedImage imagemJpg = new BufferedImage(imagem.getWidth(), imagem.getHeight(), BufferedImage.TYPE_INT_RGB);
		imagemJpg.createGraphics().drawImage(imagem, 0, 0, Color.WHITE, null);
		return imagemJpg;
	}
	
	public InputStream getInputStream(BufferedImage imagem, String extensao) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(imagem, extensao, os);
			return new ByteArrayInputStream(os.toByteArray());
		}
		catch (IOException e) {
			throw new ExcecaoDeArquivo("Erro ao ler arquivo");
		}
	}
	
	public BufferedImage recortarQuadrado(BufferedImage imagemOrigem) {
		int minimo = (imagemOrigem.getHeight() <= imagemOrigem.getWidth()) ? imagemOrigem.getHeight() : imagemOrigem.getWidth();
		return Scalr.crop(imagemOrigem, (imagemOrigem.getWidth()/2) - (minimo/2), (imagemOrigem.getHeight()/2) - (minimo/2), minimo, minimo);
	}
	
	public BufferedImage redimensionar(BufferedImage imagemOrigem, int tamanho) {
		return Scalr.resize(imagemOrigem, Scalr.Method.ULTRA_QUALITY, tamanho);
	}
}