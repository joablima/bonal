package br.com.empresa.bonal.testes.entidades;

import java.math.BigDecimal;

import br.com.empresa.bonal.entidades.Bem;
import br.com.empresa.bonal.entidades.Categoria;
import br.com.empresa.bonal.entidades.UnidadeDeMedida;
import br.com.empresa.bonal.repositorio.BemRepositorio;
import br.com.empresa.bonal.repositorio.CategoriaRepositorio;
import br.com.empresa.bonal.repositorio.UnidadeDeMedidaRepositorio;

public class BemTeste {
	
	
	public static void main(String[] args) {
		
		UnidadeDeMedidaRepositorio umr = new UnidadeDeMedidaRepositorio();
		BemRepositorio br = new BemRepositorio();
		CategoriaRepositorio cr = new CategoriaRepositorio();
		
		
		//cuidado com essas buscas por id. Buscar sempre por ids existentes no banco
		UnidadeDeMedida u = umr.buscarPorId(1L);
		Categoria c = cr.buscarPorId(3L);
		
		Bem b = new Bem();
		b.setCodigo("001");
		b.setNome("Bem de Teste");
		b.setDescricao("Bem usado nos testes unitarios");
		b.setQuantidade(new BigDecimal("0"));
		b.setTipoBem("consumo");
		b.setCategoria(c);
		b.setUnidadeDeMedida(u);
		
		br.adicionar(b, 3L, 1L);

		System.out.println("Bem adicionado");
		System.out.println(b.toString());

		Bem aux = new Bem();
		aux = br.buscarPorId(b.getId());
		System.out.println("Bem recuperado");
		System.out.println(aux.toString());
		
		System.out.println("Codigo irá ser editada para '002'");
		aux.setCodigo("002");
		br.atualizar(aux, 3L, 1L);
		
		aux = br.buscarPorId(aux.getId());
		System.out.println("Bem editado");
		System.out.println(aux.toString());
		
		
	}

}
