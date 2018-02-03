package br.com.empresa.bonal.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

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
public class Aquisicao implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fornecedor")
	private Fornecedor fornecedor;	
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "funcionario")
	private Funcionario funcionario;	
	
	private BigDecimal precoTotal;
	
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_cadastro")
	private Calendar dataCadastro = Calendar.getInstance();
	

	@Version
	private Integer version;
	
	public Fornecedor getFornecedor() {
		return fornecedor;
	}


	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}


	private Boolean status;

	public Funcionario getFuncionario() {
		return funcionario;
	}


	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}


	public BigDecimal getPrecoTotal() {
		return precoTotal;
	}


	public void setPrecoTotal(BigDecimal precoTotal) {
		this.precoTotal = precoTotal;
	}


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
	
	
	
	

}
