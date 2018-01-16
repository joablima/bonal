package br.com.empresa.bonal.entidades;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
/*
	Esta classe ir� categorizar os bens de consumo e permanentes, como por exemplo
	bens de consumos podem ser Produtos, Maquinas, equipamentos
*/
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import br.com.empresa.bonal.util.enums.EnumCategoria;

@SuppressWarnings("serial")
@Entity
public class Categoria implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String nome;

	@NotNull
	@Column(name = "codigo", unique = true)
	private String codigo;

	private String descricao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private EnumCategoria tipo;

	@Version
	private Integer version;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_cadastro")
	private Calendar dataCadastro = Calendar.getInstance();

	@NotNull
	private Boolean status;

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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

	public EnumCategoria getTipo() {
		return tipo;
	}

	public void setTipo(EnumCategoria tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getClass().getSimpleName() + " {");
		builder.append("\n\tid= " + getId());
		builder.append("\n\tnome= " + getNome());
		builder.append("\n\tcodigo= " + getCodigo());
		builder.append("\n\tdescricao= " + getDescricao());
		builder.append("\n\tdataCadastro=" + getDataCadastro().getTime());
		builder.append("\n\tversion=" + getVersion());
		builder.append("\n }");
		return builder.toString();
	}

	public String resumo() {
		StringBuilder builder = new StringBuilder();
		String status = getStatus() ? "ativo" : "inativo";
		builder.append(" " + getCodigo()).append(" - " + getNome()).append(" - " + getDescricao())
				.append(" - " + status);
		return builder.toString();
	}

	// TODO falta implementar equal e hashcode
}
