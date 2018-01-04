package br.com.empresa.bonal.testes.entidades;

import java.math.BigDecimal;

import br.com.empresa.bonal.entidades.CoeficienteTecnico;
import br.com.empresa.bonal.entidades.Categoria;
import br.com.empresa.bonal.entidades.UnidadeDeMedida;
import br.com.empresa.bonal.repositorio.CoeficienteTecnicoRepositorio;
import br.com.empresa.bonal.repositorio.CategoriaRepositorio;
import br.com.empresa.bonal.repositorio.UnidadeDeMedidaRepositorio;

public class CoeficienteTecnicoTeste {
	
	
	public static void main(String[] args) {
		
		CoeficienteTecnicoRepositorio ctr = new CoeficienteTecnicoRepositorio();
		
		
		//cuidado com essas buscas por id. Buscar sempre por ids existentes no banco
	
		
		CoeficienteTecnico c = new CoeficienteTecnico();
		c.setQuantidade(new BigDecimal("12.8"));
		
		ctr.adicionar(c, 1L, 1L);

		System.out.println("Coeficiente Tecnico adicionado");
		System.out.println(c.toString());

		CoeficienteTecnico aux = new CoeficienteTecnico();
		aux = ctr.buscarPorId(c.getId());
		System.out.println("Coeficiente Tecnico recuperado");
		System.out.println(aux.toString());
		
		System.out.println("Quantidade irá ser alterada para 25,5");
		aux.setQuantidade(new BigDecimal("25.5"));
		ctr.atualizar(aux, 1L, 1L);
		
		aux = ctr.buscarPorId(aux.getId());
		System.out.println("Coeficiente Tecnico editado");
		System.out.println(aux.toString());
		
		
	}

}
