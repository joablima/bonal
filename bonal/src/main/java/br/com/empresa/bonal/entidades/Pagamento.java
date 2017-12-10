package br.com.empresa.bonal.entidades;
/*
	Registra todos os pagamentos realizados pela organização
*/
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Pagamento {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	//registra o funcionario que foi beneficiado
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="funcionario")
	private Funcionario funcionario;
	
	//registra o custo total do pagamento
	private double custoTotal;
	
	//registra a data que foi realizado o pagamento
	private Date data;
	
	//define se o pagamento foi custo ou despesa com base no cargo do funcionario
	private String naturezaOperacao;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	public double getCustoTotal() {
		return custoTotal;
	}
	public void setCustoTotal(double custoTotal) {
		this.custoTotal = custoTotal;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getNaturezaOperacao() {
		return naturezaOperacao;
	}
	public void setNaturezaOperacao(String naturezaOperacao) {
		this.naturezaOperacao = naturezaOperacao;
	}
	
	
	
}
