package br.com.empresa.bonal.entidades;

import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ItemBC {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String codigo;
	private Calendar dataDeValidade;
	
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public Calendar getDataDeValidade() {
		return dataDeValidade;
	}
	public void setDataDeValidade(Calendar dataDeValidade) {
		this.dataDeValidade = dataDeValidade;
	}
	public Long getId() {
		return id;
	}
	
	

}
