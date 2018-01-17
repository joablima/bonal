/*package br.com.empresa.bonal.testes.entidades;

import java.math.BigDecimal;

import br.com.empresa.bonal.entidades.Bem;
import br.com.empresa.bonal.repositorio.BemRepositorio;

public class BemTeste {
	
	
	public static void main(String[] args) {
		
		BemRepositorio br = new BemRepositorio();
		
		
		
		
		Bem b = new Bem();
		b.setCodigo("001");
		b.setNome("Bem de Teste");
		b.setDescricao("Bem usado nos testes unitarios");
		b.setQuantidade(new BigDecimal("0"));
		br.adicionar(b, 1L, 1L);

		System.out.println("Bem adicionado");
		System.out.println(b.toString());

		Bem aux = new Bem();
		aux = br.buscarPorId(b.getId());
		System.out.println("Bem recuperado");
		System.out.println(aux.toString());
		
		System.out.println("Codigo irá ser editada para '002'");
		aux.setCodigo("002");
		br.atualizar(aux, 1L, 1L);
		
		aux = br.buscarPorId(aux.getId());
		System.out.println("Bem editado");
		System.out.println(aux.toString());
		
		
	}

}
*/