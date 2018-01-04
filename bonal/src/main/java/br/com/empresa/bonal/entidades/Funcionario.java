package br.com.empresa.bonal.entidades;

import java.util.Calendar;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Funcionario extends Pessoa{
	
	private Calendar dataDeAdmissao;

	public Calendar getDataDeAdmissao() {
		return dataDeAdmissao;
	}

	public void setDataDeAdmissao(Calendar dataDeAdmissao) {
		this.dataDeAdmissao = dataDeAdmissao;
	}
	
	
	
}
