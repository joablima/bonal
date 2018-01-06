package br.com.empresa.bonal.testes.entidades;

import br.com.empresa.bonal.entidades.UnidadeDeMedida;
import br.com.empresa.bonal.repositorio.UnidadeDeMedidaRepositorio;

public class UnidadeDeMedidaTeste {
	
	
	public static void main(String[] args) {
		
		UnidadeDeMedidaRepositorio umr = new UnidadeDeMedidaRepositorio();
		
		UnidadeDeMedida u = new UnidadeDeMedida();
		
		u.setNome("Unidade de Teste");
		u.setSigla("UT");
		
		umr.adicionar(u);

		System.out.println("Unidade De Medida adicionada");
		System.out.println(u.toString());

		UnidadeDeMedida aux = new UnidadeDeMedida();
		aux = umr.buscarPorId(u.getId());
		System.out.println("Unidade De Medida recuperada");
		System.out.println(aux.toString());
		
		System.out.println("Sigla irá ser editada para 'SE'");
		aux.setSigla("SE");
		umr.atualizar(aux);
		
		aux = umr.buscarPorId(aux.getId());
		System.out.println("Unidade De Medida editada");
		System.out.println(aux.toString());
		
		
	}

}
