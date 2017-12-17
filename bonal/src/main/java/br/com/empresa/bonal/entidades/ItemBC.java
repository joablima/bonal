package br.com.empresa.bonal.entidades;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ItemBC {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "bem")
	private Bem bem;
	
	@Column(name = "data_de_validade")
	private Calendar dataDeValidade;
	
	
	public Bem getBem() {
		return bem;
	}
	public void setBem(Bem bem) {
		this.bem = bem;
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
