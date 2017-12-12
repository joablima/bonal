//package br.com.empresa.bonal.entidades;
//
//import java.util.Date;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//
//@Entity
//public class Producao {
//	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	private Long id;
//	
//	//registra o codigo do produto que está sendo produzido
//	private String codigo;
//	
//	//registra a quantidade que está sendo produzido
//	private double quantidade;
//	
//	//registra a data que foi realizado a producao
//	private Date data;
//	
//	//registra o custo aproximado que aquela producao teve
//	private double custoAproximado;
//	
//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
//	public String getCodigo() {
//		return codigo;
//	}
//	public void setCodigo(String codigo) {
//		this.codigo = codigo;
//	}
//	public double getQuantidade() {
//		return quantidade;
//	}
//	public void setQuantidade(double quantidade) {
//		this.quantidade = quantidade;
//	}
//	public Date getData() {
//		return data;
//	}
//	public void setData(Date data) {
//		this.data = data;
//	}
//	public double getCustoAproximado() {
//		return custoAproximado;
//	}
//	public void setCustoAproximado(double custoAproximado) {
//		this.custoAproximado = custoAproximado;
//	}
//	
//	
//}
