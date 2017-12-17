package br.com.empresa.bonal.entidades;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

public class CoeficienteTecnico {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="produto")
	private Produto produto;
	
	@OneToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="bem")
	private Bem bem;
	
	private BigDecimal quantidade;

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Bem getBem() {
		return bem;
	}

	public void setBem(Bem bem) {
		this.bem = bem;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public Long getId() {
		return id;
	}
	
	
	
	

}
