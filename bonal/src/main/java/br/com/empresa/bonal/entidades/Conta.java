package br.com.empresa.bonal.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import br.com.empresa.bonal.util.enums.EnumStatusPagamento;
import br.com.empresa.bonal.util.enums.EnumTipoPagamento;

@Entity
public class Conta implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private EnumTipoPagamento tipoDePagamento;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private EnumStatusPagamento statusPagamento;

	
	private Date vencimento;
	
	private BigDecimal precoTotal;
	
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_cadastro")
	private Calendar dataCadastro = Calendar.getInstance();
	

	@Version
	private Integer version;
	
	private Boolean status;

	public EnumTipoPagamento getTipoDePagamento() {
		return tipoDePagamento;
	}

	public void setTipoDePagamento(EnumTipoPagamento tipoDePagamento) {
		this.tipoDePagamento = tipoDePagamento;
	}



	public Date getVencimento() {
		return vencimento;
	}

	public void setVencimento(Date vencimento) {
		this.vencimento = vencimento;
	}

	public BigDecimal getPrecoTotal() {
		return precoTotal;
	}

	public void setPrecoTotal(BigDecimal precoTotal) {
		this.precoTotal = precoTotal;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
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

	

	public EnumStatusPagamento getStatusPagamento() {
		return statusPagamento;
	}

	public void setStatusPagamento(EnumStatusPagamento statusPagamento) {
		this.statusPagamento = statusPagamento;
	}

	@Override
	public String toString() {
		return "Conta [id="+ ", tipoDePagamento=" + tipoDePagamento.toString() + ", statusPagamento=" + statusPagamento.toString()
				+ ", vencimento=" + vencimento.toString() + ", precoTotal=" + precoTotal.toString() + ", dataCadastro=" + dataCadastro.toString()
				+ ", version=" + version + ", status=" + status + "]";
	}

	
	
	
	

}
