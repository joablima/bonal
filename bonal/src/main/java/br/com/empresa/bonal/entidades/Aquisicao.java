package br.com.empresa.bonal.entidades;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
/*
	A tabela de Aquisição registra todas as aquisições de bens e serviços 
	realizados pela empresa. Para uma aquisição ser válida o bem ou serviço
	deve estar previamente cadastrado no sistema .

*/
@Entity
public class Aquisicao {
	//gera id automaticamente e define um campo para armazenar o cpf ou cnpj do fornecedor
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id, fornecedor;
	//a variavel codigo refere-se a entidade sendo adquirida, se é bem ou serviço e a natureza da operação define se é custo ou despesa
	private String codigo, naturezaOperacao;
	
	//preco armazena o preco da aquisicao
	private double preco;
	
	//registra a data que foi realizada a aquisicao
	private Date data;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(Long fornecedor) {
		this.fornecedor = fornecedor;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNaturezaOperacao() {
		return naturezaOperacao;
	}
	public void setNaturezaOperacao(String naturezaOperacao) {
		this.naturezaOperacao = naturezaOperacao;
	}
	public double getPreco() {
		return preco;
	}
	public void setPreco(double preco) {
		this.preco = preco;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	
	
	
}
