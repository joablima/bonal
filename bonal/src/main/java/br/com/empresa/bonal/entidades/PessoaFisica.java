package br.com.empresa.bonal.entidades;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.validator.constraints.br.CPF;

@MappedSuperclass
public abstract class PessoaFisica extends Pessoa {

	@CPF
	@Column(name = "cpf", length = 14)
	private String cpf;

	private String sexo;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

}