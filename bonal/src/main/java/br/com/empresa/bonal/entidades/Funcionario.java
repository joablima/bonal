package br.com.empresa.bonal.entidades;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import br.com.empresa.bonal.util.enums.EnumFormacao;

@Entity
@DiscriminatorValue("funcionario")
public class Funcionario extends PessoaFisica {

	@Column(name = "formacao")
	@Enumerated(EnumType.STRING)
	private EnumFormacao formacao;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "qualificacao")
	private QualificacaoProfissional qualificacao;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cargo")
	private Cargo cargo;

	public EnumFormacao getFormacao() {
		return formacao;
	}

	public void setFormacao(EnumFormacao formacao) {
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getClass().getSimpleName() + " {");
		builder.append("\n\tid= " + getId());
		builder.append("\n\tnome= " + getNome());
		builder.append("\n\tcpf= " + getCpf());
		builder.append("\n\tsexo= " + getSexo());
		builder.append("\n\temail= " + getEmail());
		builder.append("\n\ttelefone= " + getTelefone());
		builder.append("\n\tdataCadastro=" + getDataNascimento().getTime());
		builder.append("\n\tdataCadastro=" + getDataCadastro().getTime());
		builder.append("\n\tversion=" + getVersion());
		builder.append("\n }");
		return builder.toString();
	}

}
