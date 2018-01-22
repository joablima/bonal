package br.com.empresa.bonal.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Arquivo {

	public byte[] lerArquivo(String arquivo) {
		Path path = Paths.get(arquivo);
		try {
			return Files.readAllBytes(path);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private void escreverEmArquivo(String arquivo, byte[] bytes) throws IOException {
		Path path = Paths.get(arquivo);
		Files.write(path, bytes);
	}

	public void imprimirConsole(String arquivo) throws IOException {
		Files.lines(Paths.get(arquivo)).forEach(System.out::println);
	}

	private void copiar(InputStream origem, OutputStream destino) {
		int bite = 0;
		byte[] tamanhoMaximo = new byte[1024 * 1024 * 25]; // 25MB
		try {
			// enquanto bytes forem sendo lidos
			while ((bite = origem.read(tamanhoMaximo)) >= 0) {
				// pegue o byte lido e escreva no destino
				destino.write(tamanhoMaximo, 0, bite);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void upload(String pasta, String nomeDoArquivo, InputStream arquivoCarregado) {
		String caminhoArquivo = pasta + "/" + nomeDoArquivo;
		File novoArquivo = new File(caminhoArquivo);
		FileOutputStream saida = null;
		try {
			saida = new FileOutputStream(novoArquivo);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		copiar(arquivoCarregado, saida);
	}
}
