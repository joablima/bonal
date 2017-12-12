//package br.com.empresa.bonal.entidades;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//
//@Entity
//public class Cargo {
//	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	private Long id;
//	
//	//define um nome para o cargo cadastrado
//	private String nome;
//	
//	//define um salario base para os funcionarios que possuem esse cargo
//	private double salarioBase;
//	
//	//define se os funcionarios desse cargo terão acesso de administrador no sistema
//	private int administrador;
//	
//	
//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
//	public String getNome() {
//		return nome;
//	}
//	public void setNome(String nome) {
//		this.nome = nome;
//	}
//	public double getSalarioBase() {
//		return salarioBase;
//	}
//	public void setSalarioBase(double salarioBase) {
//		this.salarioBase = salarioBase;
//	}
//	public int getAdministrador() {
//		return administrador;
//	}
//	public void setAdministrador(int administrador) {
//		this.administrador = administrador;
//	}
//	
//	
//}
