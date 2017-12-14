package br.com.empresa.bonal.entidades;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("funcionario")
public class Funcionario extends PessoaFisica {

	private String formacao;

	
	private QualificacaoProfissional qualificacao;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cargo")
	private Cargo cargo;

	public String getFormacao() {
		return formacao;
	}

	public void setFormacao(String formacao) {
		this.formacao = formacao;
	}

	public QualificacaoProfissional getQualificacao() {
		return qualificacao;
	}

	public void setQualificacao(QualificacaoProfissional qualificacao) {
		this.qualificacao = qualificacao;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

}
