//package br.com.empresa.bonal.entidades;
//
//import javax.persistence.Column;
//import javax.persistence.DiscriminatorValue;
//import javax.persistence.Entity;
//
//import org.hibernate.validator.constraints.br.CNPJ;
//
//@Entity
//@DiscriminatorValue("juridica")
//public class PessoaJuridica extends Pessoa {
//
//	@CNPJ
//	@Column(name = "cpf", length = 18)
//	private String cnpj;
//
//	@Column(name = "ins_estadual", length = 15)
//	private String insEstadual;
//
//	private String razaoSocial;
//
//	public String getCnpj() {
//		return cnpj;
//	}
//
//	public void setCnpj(String cnpj) {
//		this.cnpj = cnpj;
//	}
//
//	public String getInsEstadual() {
//		return insEstadual;
//	}
//
//	public void setInsEstadual(String insEstadual) {
//		this.insEstadual = insEstadual;
//	}
//
//	public String getRazaoSocial() {
//		return razaoSocial;
//	}
//
//	public void setRazaoSocial(String razaoSocial) {
//		this.razaoSocial = razaoSocial;
//	}
//}
