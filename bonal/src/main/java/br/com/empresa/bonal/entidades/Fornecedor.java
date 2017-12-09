package br.com.empresa.bonal.entidades;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Fornecedor {
	@Id
	private long cnpj;
	private String nome, endereco;
	
	
	public long getCnpj() {
		return cnpj;
	}
	public void setCnpj(long cnpj) {
		this.cnpj = cnpj;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	
}
