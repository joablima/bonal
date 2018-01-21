package br.com.empresa.bonal.entidades;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
public class UnidadeDeMedida implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Nome da Unidade de Medida é obrigatória")
	private String nome;
	
	@Column(unique=true)
	@NotNull(message = "Sigla da Unidade de Medida é obrigatória")
	private String sigla;

	private Boolean status;

	@Version
	private Integer version;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_cadastro")
	private Calendar dataCadastro = Calendar.getInstance();

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla.toUpperCase();
	}

	public Integer getVersion() {
		return version;
	}

	public Calendar getDataCadastro() {
		return dataCadastro;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getClass().getSimpleName() + " {");
		builder.append("\n\tid= " + getId());
		builder.append("\n\tsigla= " + getSigla());
		builder.append("\n\tnome= " + getNome());
		builder.append("\n\tdataCadastro=" + getDataCadastro().getTime());
		builder.append("\n\tversion=" + getVersion());
		builder.append("\n }");
		return builder.toString();
	}

	// TODO falta implementar equal e hashcode
}
