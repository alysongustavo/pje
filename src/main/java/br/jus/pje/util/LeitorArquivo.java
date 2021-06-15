package br.jus.pje.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class LeitorArquivo {

	private String delimitador = "|";
	private File processos = null;

	public LeitorArquivo() {

		processos = new File(Configuracoes.CAMINHO_ARQUIVO.getValor(), Configuracoes.NOME_ARQUIVO.getValor());
	}

	public int[] obterUltimaPaginaLinha() throws IOException {

		StringTokenizer dado = null;
		String linha = null;

		int[] retorno = {0, 0};

		int contador = 0;

		BufferedReader buffer = null;

		try {

			if (processos.exists() && processos.isFile()) {

				buffer = new BufferedReader(new FileReader(processos));

				linha = buffer.readLine();

				do {

					dado = new StringTokenizer(linha, delimitador);
					contador = dado.countTokens();

					for (int i = 0; i < contador; i++) {

						if (i == (contador -2)) {

							retorno[0] = Integer.valueOf(dado.nextToken()).intValue();
							retorno[1] = Integer.valueOf(dado.nextToken()).intValue();

							break;
						}

						dado.nextToken();
					}
				} while ((linha = buffer.readLine()) != null);
			}
		} finally {

			if (buffer != null) {

				buffer.close();
			}
		}

		return retorno;
	}
}
