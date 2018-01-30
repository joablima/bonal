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
	

	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="unidadeDeMedida")
	private UnidadeDeMedida unidadeDeMedida;
	
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="item")
	private ItemDeProducao itemDeProducao;
	
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
	

	public ItemDeProducao getItemDeProducao() {
		return itemDeProducao;
	}

	public void setItemDeProducao(ItemDeProducao item) {
		this.itemDeProducao = item;
	}

	public UnidadeDeMedida getUnidadeDeMedida() {
		return unidadeDeMedida;
	}

	public void setUnidadeDeMedida(UnidadeDeMedida unidadeDeMedida) {
		this.unidadeDeMedida = unidadeDeMedida;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getClass().getSimpleName() + " {");
		builder.append("\n\tid= " + getId());
		builder.append("\n\tproduto= " + getProduto().getNome());
		builder.append("\n\tbem= " + getItemDeProducao().getNome());
		builder.append("\n\tquantidade= " + getQuantidade());
		builder.append("\n }");
		return builder.toString();
	}
	
	

}
