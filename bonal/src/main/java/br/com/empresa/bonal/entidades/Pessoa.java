package br.com.empresa.bonal.entidades;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo")
public class Pessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;
	private String email;
	private String telefone;

	/*
	 * version e dataRegistro devem estar em todos as Entidades que possuem
	 * repositório
	 */
	@Version
	private Integer version;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_registro")
	private Calendar dataRegistro = Calendar.getInstance();

	// ---------- Inicio Relacionamentos ----------
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "endereco_id")
	private Endereco endereco;

	// @OneToOne(mappedBy = "pessoa")
	// private Funcionario funcionario;
	// ---------- Fim Relacionamentos ----------

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Integer getVersion() {
		return version;
	}

	public Calendar getDataRegistro() {
		return dataRegistro;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getClass().getSimpleName() + " {");
		builder.append("\n\tid= " + getId());
		builder.append("\n\tnome= " + getNome());
		builder.append("\n\temail= " + getEmail());
		builder.append("\n\ttelefone= " + getTelefone());
		builder.append("\n\tdataRegistro= " + getDataRegistro());
		builder.append("\n\tversion= " + getVersion());
		builder.append("\n }");
		return builder.toString();
	}

	// Falta HashCode

}
