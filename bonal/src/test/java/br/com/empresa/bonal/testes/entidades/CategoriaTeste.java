package br.com.empresa.bonal.testes.entidades;

import br.com.empresa.bonal.entidades.Categoria;
import br.com.empresa.bonal.repositorio.CategoriaRepositorio;
import br.com.empresa.bonal.util.tx.Transacional;

public class CategoriaTeste {
	
	public static void main(String[] args) {
		teste();
	}
	
	
	@Transacional
	public static String teste(){

		CategoriaRepositorio cr = new CategoriaRepositorio();

		Categoria c = new Categoria();
		c.setNome("Categoria de Teste Unitario");
		c.setCodigo("001");
		c.setDescricao("Categoria criada para validacao de teste");
		c.setStatus(true);

		cr.adicionar(c);

		System.out.println("Categoria adicionada");
		System.out.println(c.toString());

		Categoria aux = new Categoria();
		aux = cr.buscarPorId(c.getId());
		System.out.println("Categoria recuperada");
		System.out.println(aux.toString());

		System.out.println("Descrição irá ser editada para 'Descricao Editada'");
		aux.setDescricao("Descricao Editada");
		cr.atualizar(aux);

		aux = cr.buscarPorId(aux.getId());
		System.out.println("Categoria editada");
		System.out.println(aux.toString());
		
		return "";
	}

}
