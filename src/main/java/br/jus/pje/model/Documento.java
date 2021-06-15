package br.jus.pje.model;

public class Documento {

	private String cpf;
	private boolean protestado = false;

	public Documento() {

	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public boolean isProtestado() {
		return protestado;
	}

	public void setProtestado(boolean protestado) {
		this.protestado = protestado;
	}
}
