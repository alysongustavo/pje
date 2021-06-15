package br.jus.pje.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class EscritorArquivo {

	private File processos = null; 

	public EscritorArquivo() throws IOException {

		processos = new File(Configuracoes.CAMINHO_ARQUIVO.getValor(), Configuracoes.NOME_ARQUIVO.getValor());
		inicializarArquivo();
	}

	private void inicializarArquivo() throws IOException {

		if (!processos.exists()) {

			processos.createNewFile();
		}
	}

	public void escreverLinha(String[] dados) throws IOException {

		BufferedWriter escritor = null;

		try {

			escritor = new BufferedWriter(new FileWriter(processos, true));

			for (int i = 0; i < dados.length; i++) {

				escritor.append(dados[i]);
				escritor.newLine();
			}
		} finally {

			escritor.close();
		}
	}
}
