package br.jus.pje.util;

public enum Configuracoes {

	URL_PJE("http://pje.jus.br/navegador/"),
	URL_CENPROT("https://site.cenprotnacional.org.br/"),
	PROCESSO_POR_PAGINA("20"),
	CAMINHO_ARQUIVO("C:\\Users\\gdasi\\IdeaProject\\pje\\resultado\\"),
	NOME_ARQUIVO("processos.txt");
	

	private String url = null;

	Configuracoes(String url) {

		this.url = url;
	}

	public String getValor() {

		return this.url;
	}
}
