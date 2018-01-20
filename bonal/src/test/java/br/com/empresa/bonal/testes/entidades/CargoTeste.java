package br.com.empresa.bonal.testes.entidades;

import br.com.empresa.bonal.entidades.Cargo;
import br.com.empresa.bonal.repositorio.CargoRepositorio;

public class CargoTeste {

	
	public static void main(String[] args) {

		CargoRepositorio cr = new CargoRepositorio();

		Cargo c = new Cargo();
		c.setNome("Programador de marte");

		cr.adicionar(c);

		System.out.println("Cargo adicionado");
		System.out.println(c.toString());

		Cargo aux = new Cargo();
		aux = cr.buscarPorId(c.getId());
		System.out.println("Cargo recuperado");
		System.out.println(aux.toString());
		
		System.out.println("Salário irá ser editado para 56.000 pelo ótimo trabalho");
		cr.atualizar(aux);
		
		aux = cr.buscarPorId(aux.getId());
		System.out.println("Cargo editado");
		System.out.println(aux.toString());
		
	} 

}
