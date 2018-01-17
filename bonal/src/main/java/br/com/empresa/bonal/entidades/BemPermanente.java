package br.com.empresa.bonal.entidades;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;


@SuppressWarnings("serial")
@DiscriminatorValue("bem_permanente")
public class BemPermanente extends Bem implements Serializable{
	
	private String marca;
	private String modelo;
		
	private Boolean status;

	@Version
	private Integer version;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_cadastro")
	private Calendar dataCadastro = Calendar.getInstance();

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
