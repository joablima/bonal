package br.com.empresa.bonal.testes.entidades;

import br.com.empresa.bonal.entidades.Servico;
import br.com.empresa.bonal.repositorio.ServicoRepositorio;

public class ServicoTeste {
	
	
	public static void main(String[] args) {
		
		ServicoRepositorio sr = new ServicoRepositorio();
		
		
		//cuidado com essas buscas por id. Buscar sempre por ids existentes no banco
	
		
		Servico s = new Servico();
		s.setCodigo("001");
		s.setNome("Servico de Teste");
		s.setDescricao("Servico usado nos testes unitarios");
		
		sr.adicionar(s, 1L, 1L);

		System.out.println("Servico adicionado");
		System.out.println(s.toString());

		Servico aux = new Servico();
		aux = sr.buscarPorId(s.getId());
		System.out.println("Servico recuperado");
		System.out.println(aux.toString());
		
		System.out.println("Codigo irá ser editado para '002'");
		aux.setCodigo("002");
		sr.atualizar(aux, 1L, 1L);
		
		aux = sr.buscarPorId(aux.getId());
		System.out.println("Servico editado");
		System.out.println(aux.toString());
		
		
	}

}
