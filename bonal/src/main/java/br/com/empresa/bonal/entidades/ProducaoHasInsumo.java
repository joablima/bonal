package br.com.empresa.bonal.entidades;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ProducaoHasInsumo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	//recebe chave estrangeira da producao que pertence
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="producaoId")
	private Producao producaoId;
	
	//codigo do insumo que está sendo registrado
	private String codigo;
	
	//registra a quantidade daquele Insumo que está sendo utilizado
	private double quantidade;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Producao getProducaoId() {
		return producaoId;
	}
	public void setProducaoId(Producao producaoId) {
		this.producaoId = producaoId;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public double getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}
	
	
}
