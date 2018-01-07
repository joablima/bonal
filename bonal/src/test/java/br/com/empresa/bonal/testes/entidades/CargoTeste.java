package br.com.empresa.bonal.testes.entidades;

import java.math.BigDecimal;

import br.com.empresa.bonal.entidades.Cargo;
import br.com.empresa.bonal.repositorio.CargoRepositorio;

public class CargoTeste {

	
	public static void main(String[] args) {

		CargoRepositorio cr = new CargoRepositorio();

		Cargo c = new Cargo();
		c.setNome("Programador de marte");
		c.setSalario(new BigDecimal("28.000"));

		cr.adicionar(c);

		System.out.println("Cargo adicionado");
		System.out.println(c.toString());

		Cargo aux = new Cargo();
		aux = cr.buscarPorId(c.getId());
		System.out.println("Cargo recuperado");
		System.out.println(aux.toString());
		
		System.out.println("Salário irá ser editado para 56.000 pelo ótimo trabalho");
		aux.setSalario(new BigDecimal("56.000"));
		cr.atualizar(aux);
		
		aux = cr.buscarPorId(aux.getId());
		System.out.println("Cargo editado");
		System.out.println(aux.toString());
		
	} 

}
