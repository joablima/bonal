package br.com.empresa.bonal.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;


@SuppressWarnings("serial")
@DiscriminatorValue("bem_consumo")
public class BemConsumo extends Bem implements Serializable{
	
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "unidade_de_medida")
	private UnidadeDeMedida unidadeDeMedida;	
	
	private BigDecimal quantidade;
	
	
	
	private Boolean status;


	@Version
	private Integer version;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_cadastro")
	private Calendar dataCadastro = Calendar.getInstance();

	public UnidadeDeMedida getUnidadeDeMedida() {
		return unidadeDeMedida;
	}

	public void setUnidadeDeMedida(UnidadeDeMedida unidadeDeMedida) {
		this.unidadeDeMedida = unidadeDeMedida;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Integer getVersion() {
		return version;
	}

	public Calendar getDataCadastro() {
		return dataCadastro;
	}

	
	
	
}
