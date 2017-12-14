package br.com.empresa.bonal.entidades;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.br.CPF;

@MappedSuperclass
public abstract class PessoaFisica extends Pessoa {

	@CPF
	@Column(name = "cpf", length = 14)
	private String cpf;

	private String sexo;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_nascimento")
	private Calendar dataNascimento = Calendar.getInstance();

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

	public Calendar getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Calendar dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

}