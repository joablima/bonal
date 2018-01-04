package br.com.empresa.bonal.testes.entidades;

import br.com.empresa.bonal.entidades.Categoria;
import br.com.empresa.bonal.entidades.NaturezaDaDespesa;
import br.com.empresa.bonal.repositorio.NaturezaDaDespesaRepositorio;

public class NaturezaDaDespesaTeste {
	
	
	public static void main(String[] args) {
		
		NaturezaDaDespesaRepositorio ndr = new NaturezaDaDespesaRepositorio();
		
		NaturezaDaDespesa d = new NaturezaDaDespesa();
		
		d.setNome("Natureza de Teste");
		d.setCodigo("01");
		d.setDescricao("Esta despesa é considerada de teste");
		
		ndr.adicionar(d);

		System.out.println("Natureza da Despesa adicionada");
		System.out.println(d.toString());

		NaturezaDaDespesa aux = new NaturezaDaDespesa();
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
