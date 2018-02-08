package br.com.empresa.bonal.entidades;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@SuppressWarnings("serial")
@Entity
@DiscriminatorValue("bem_permanente")
public class BemPermanente extends ItemDeProducao implements Serializable{
	
	private String marca;
	private String modelo;
		

	private BigDecimal quantidade = new BigDecimal("0");

	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	
	public String resumo(){
		return "";
	}
	public BigDecimal getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}
	
	
}
