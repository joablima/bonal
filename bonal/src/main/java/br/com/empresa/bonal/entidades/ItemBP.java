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

@Entity
public class ItemBP {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "bem")
	private Bem bem;
	
	@Column(name = "data_de_garantia")
	private Calendar dataDeGarantia;
	
	private BigDecimal taxaDepreciacao;
	private BigDecimal vidaUtil;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Bem getBem() {
		return bem;
	}
	public void setBem(Bem bem) {
		this.bem = bem;
	}
	public Calendar getDataDeGarantia() {
		return dataDeGarantia;
	}
	public void setDataDeGarantia(Calendar dataDeGarantia) {
		this.dataDeGarantia = dataDeGarantia;
	}
	public BigDecimal getTaxaDepreciacao() {
		return taxaDepreciacao;
	}
	public void setTaxaDepreciacao(BigDecimal taxaDepreciacao) {
		this.taxaDepreciacao = taxaDepreciacao;
	}
	public BigDecimal getVidaUtil() {
		return vidaUtil;
	}
	public void setVidaUtil(BigDecimal vidaUtil) {
		this.vidaUtil = vidaUtil;
	}
	
		

	
	
}
