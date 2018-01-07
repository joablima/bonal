package br.com.empresa.bonal.testes.entidades;

import br.com.empresa.bonal.entidades.Operacao;
import br.com.empresa.bonal.repositorio.OperacaoRepositorio;

public class NaturezaDaDespesaTeste {
	
	
	public static void main(String[] args) {
		
		OperacaoRepositorio ndr = new OperacaoRepositorio();
		
		Operacao d = new Operacao();
		
		d.setNome("Natureza de Teste");
		d.setCodigo("01");
		d.setDescricao("Esta despesa é considerada de teste");
		
		ndr.adicionar(d);

		System.out.println("Natureza da Despesa adicionada");
		System.out.println(d.toString());

		Operacao aux = new Operacao();
		aux = ndr.buscarPorId(d.getId());
		System.out.println("Natureza da Despesa recuperada");
		System.out.println(aux.toString());
		
		System.out.println("Codigo irá ser editada para '02'");
		aux.setCodigo("02");
		ndr.atualizar(aux);
		
		aux = ndr.buscarPorId(aux.getId());
		System.out.println("Natureza da Despesa editada");
		System.out.println(aux.toString());
		
		
	}

}
