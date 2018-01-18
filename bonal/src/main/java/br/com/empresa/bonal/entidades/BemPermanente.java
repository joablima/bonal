package br.com.empresa.bonal.entidades;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@SuppressWarnings("serial")
@Entity
@DiscriminatorValue("bem_permanente")
public class BemPermanente extends ItemDeProducao implements Serializable{
	
	private String marca;
	private String modelo;
		


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
	
}
