package br.com.empresa.bonal.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
public class Producao implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "produto")
	private Produto produto;	
	
	
	private BigDecimal quantidade;
	

	private Date dataDeProducao;
	
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_cadastro")
	private Calendar dataCadastro = Calendar.getInstance();
	

	@Version
	private Integer version;
	
	private Boolean status;

	public Long getId() {
		return id;
	}


	public Calendar getDataCadastro() {
		return dataCadastro;
	}


	public Integer getVersion() {
		return version;
	}


	public Boolean getStatus() {
		return status;
	}


	public void setStatus(Boolean status) {
		this.status = status;
	}


	public Date getDataDeProducao() {
		return dataDeProducao;
	}


	public void setDataDeProducao(Date dataDeProducao) {
		this.dataDeProducao = dataDeProducao;
	}


	public BigDecimal getQuantidade() {
		return quantidade;
	}

	

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}


	public Produto getProduto() {
		return produto;
	}


	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	
	
	

}
