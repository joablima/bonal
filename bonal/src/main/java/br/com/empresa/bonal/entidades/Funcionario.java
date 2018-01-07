package br.com.empresa.bonal.entidades;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Entity
@DiscriminatorValue("Editor")
public class Funcionario extends Pessoa{
	
	private Calendar dataDeAdmissao;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cargo")
	private Cargo cargo;

	public Calendar getDataDeAdmissao() {
		return dataDeAdmissao;
	}

	public void setDataDeAdmissao(Calendar dataDeAdmissao) {
		this.dataDeAdmissao = dataDeAdmissao;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}
	
	
	
}
