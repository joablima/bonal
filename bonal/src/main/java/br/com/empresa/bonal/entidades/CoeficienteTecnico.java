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
import javax.persistence.OneToOne;

@SuppressWarnings("serial")
@Entity
public class CoeficienteTecnico implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="produto")
	private Produto produto;
	
	@OneToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="bem")
	private BemDeConsumo bemDeConsumo;
	
	private BigDecimal quantidade;
	
	private Boolean status;

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
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
	
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	

	public BemDeConsumo getBemDeConsumo() {
		return bemDeConsumo;
	}

	public void setBemDeConsumo(BemDeConsumo bemDeConsumo) {
		this.bemDeConsumo = bemDeConsumo;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getClass().getSimpleName() + " {");
		builder.append("\n\tid= " + getId());
		builder.append("\n\tproduto= " + getProduto().getNome());
		builder.append("\n\tbem= " + getBemDeConsumo().getNome());
		builder.append("\n\tquantidade= " + getQuantidade());
		builder.append("\n }");
		return builder.toString();
	}
	
	

}
