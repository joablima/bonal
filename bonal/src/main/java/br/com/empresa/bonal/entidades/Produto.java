package br.com.empresa.bonal.entidades;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	private String descricao;
	private BigDecimal quantidade;
	

	@Temporal(TemporalType.DATE)
	@Column(name = "data_cadastro")
	private Calendar dataCadastro = Calendar.getInstance();
	

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "categoria")
	private Categoria categoria;
	

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "unidadeDeMedida")
	private UnidadeDeMedida unidadeDeMedida;


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public BigDecimal getQuantidade() {
		return quantidade;
	}


	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}


	public Categoria getCategoria() {
		return categoria;
	}


	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}


	public UnidadeDeMedida getUnidadeDeMedida() {
		return unidadeDeMedida;
	}


	public void setUnidadeDeMedida(UnidadeDeMedida unidadeDeMedida) {
		this.unidadeDeMedida = unidadeDeMedida;
	}


	public Long getId() {
		return id;
	}


	public Calendar getDataCadastro() {
		return dataCadastro;
	}
	
	
}
