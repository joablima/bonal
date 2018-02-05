package br.com.empresa.bonal.entidades;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ItemDaAquisicao implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private BigDecimal quantidade;
	

	private BigDecimal precoUnitario;
	

	private BigDecimal precoTotal;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "itemDeProducao")
	private ItemDeProducao itemDeProducao;	
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "unidade_de_medida")
	private UnidadeDeMedida unidadeDeMedida;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "aquisicao")
	private Aquisicao aquisicao;
	
	
	private Boolean status;
	


	public ItemDeProducao getItemDeProducao() {
		return itemDeProducao;
	}

	public void setItemDeProducao(ItemDeProducao itemDeProducao) {
		this.itemDeProducao = itemDeProducao;
	}

	public UnidadeDeMedida getUnidadeDeMedida() {
		return unidadeDeMedida;
	}

	public void setUnidadeDeMedida(UnidadeDeMedida unidadeDeMedida) {
		this.unidadeDeMedida = unidadeDeMedida;
	}

	

	public Aquisicao getAquisicao() {
		return aquisicao;
	}

	public void setAquisicao(Aquisicao aquisicao) {
		this.aquisicao = aquisicao;
	}

	public Long getId() {
		return id;
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

	public BigDecimal getPrecoUnitario() {
		return precoUnitario;
	}

	public void setPrecoUnitario(BigDecimal precoUnitario) {
		this.precoUnitario = precoUnitario;
	}

	public BigDecimal getPrecoTotal() {
		return precoTotal;
	}

	public void setPrecoTotal(BigDecimal precoTotal) {
		this.precoTotal = precoTotal;
	}	
	
	
	
	

}
