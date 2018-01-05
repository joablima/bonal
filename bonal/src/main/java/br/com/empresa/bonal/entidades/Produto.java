package br.com.empresa.bonal.entidades;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String codigo;
	private String nome;
	private String descricao;
	private BigDecimal quantidade;
	

	@Temporal(TemporalType.DATE)
	@Column(name = "data_cadastro")
	private Calendar dataCadastro = Calendar.getInstance();

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "unidadeDeMedida")
	private UnidadeDeMedida unidadeDeMedida;

	@Version
	private Integer version;
	
	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public BigDecimal getQuantidade() {
		return quantidade;
	}


	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}


	public UnidadeDeMedida getUnidadeDeMedida() {
		return unidadeDeMedida;
	}


	public void setUnidadeDeMedida(UnidadeDeMedida unidadeDeMedida) {
		this.unidadeDeMedida = unidadeDeMedida;
	}


	public Long getId() {
		return id;
	}


	public Calendar getDataCadastro() {
		return dataCadastro;
	}


	public String getCodigo() {
		return codigo;
	}


	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public Integer getVersion() {
		return version;
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
		builder.append("\n\tquantidade=" + getQuantidade());
		builder.append("\n\tunidadeDeMedida=" + getUnidadeDeMedida());
		builder.append("\n\tversion=" + getVersion());
		builder.append("\n }");
		return builder.toString();
	}

	
}
