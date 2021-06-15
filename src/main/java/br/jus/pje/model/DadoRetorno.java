package br.jus.pje.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class DadoRetorno {

	private String nrProcesso = null;
	private String orgaoJulgador = null;
	private String jurisdicao = null;
	private String valorCausa = null;
	private Map<String, String> documentos = null;

	public DadoRetorno() {

		documentos = new LinkedHashMap<String, String>();
	}

	public String getNrProcesso() {
		return nrProcesso;
	}

	public void setNrProcesso(String nrProcesso) {
		this.nrProcesso = nrProcesso.trim();
	}

	public String getOrgaoJulgador() {
		return orgaoJulgador;
	}

	public void setOrgaoJulgador(String orgaoJulgador) {
		this.orgaoJulgador = orgaoJulgador.trim();
	}

	public String getJurisdicao() {
		return jurisdicao;
	}

	public void setJurisdicao(String jurisdicao) {
		this.jurisdicao = jurisdicao.trim();
	}

	public String getValorCausa() {
		return valorCausa;
	}

	public void setValorCausa(String valorCausa) {
		this.valorCausa = valorCausa.substring(2);
	}

	public Map<String, String> getPartes() {
		return documentos;
	}

	public void addParte(String documento, String nome) {
		this.documentos.put(documento.trim(), nome.trim());
	}
}
