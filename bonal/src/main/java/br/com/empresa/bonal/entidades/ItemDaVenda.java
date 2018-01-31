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
public class ItemDaVenda implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private BigDecimal quantidade;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "produto")
	private Produto produto;	
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "unidade_de_medida")
	private UnidadeDeMedida unidadeDeMedida;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "venda")
	private Venda venda;
	
	
	private Boolean status;
	
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public UnidadeDeMedida getUnidadeDeMedida() {
		return unidadeDeMedida;
	}

	public void setUnidadeDeMedida(UnidadeDeMedida unidadeDeMedida) {
		this.unidadeDeMedida = unidadeDeMedida;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
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
	
	
	
	

}
