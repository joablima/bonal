package br.com.empresa.bonal.entidades;

import javax.persistence.Embeddable;

@Embeddable
public class Endereco {

	private String logradouro;
	private Integer numero;
	private String bairro;
	private String cidade;
	private String uf;
	private String complemento;
	private String cep;

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getClass().getSimpleName() + " {");
		builder.append("\n\tLogradouro= " + getLogradouro());
		builder.append("\n\tnumero= " + getNumero());
		builder.append("\n\tbairro= " + getBairro());
		builder.append("\n\tcomplemento= " + getComplemento());
		builder.append("\n\tcep= " + getCep());
		builder.append("\n\tcidade= " + getCidade());
		builder.append("\n\tuf= " + getUf());
		builder.append("\n }");
		return builder.toString();
	}

	// TODO falta implementar equal e hashcode
}
