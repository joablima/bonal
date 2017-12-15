package bonal;

import java.math.BigDecimal;

import br.com.empresa.bonal.controles.BemControle;
import br.com.empresa.bonal.controles.CategoriaControle;
import br.com.empresa.bonal.controles.UnidadeDeMedidaControle;
import br.com.empresa.bonal.entidades.Bem;
import br.com.empresa.bonal.entidades.Categoria;
import br.com.empresa.bonal.entidades.UnidadeDeMedida;

public class Teste {

	public static void main(String[] args) {

		Categoria categoria = new Categoria();
		CategoriaControle categoriaControle = new CategoriaControle();

		categoria.setCodigo("200");
		categoria.setNome("Material de limpeza");
		categoria.setDescricao("coloca só besteira");

		categoriaControle.salvar(categoria);

		UnidadeDeMedida unidadeDeMedida = new UnidadeDeMedida();
		unidadeDeMedida.setNome("rolo");
		unidadeDeMedida.setSigla("rl");

		UnidadeDeMedidaControle unidadeDeMedidaControle = new UnidadeDeMedidaControle();
		unidadeDeMedidaControle.salvar(unidadeDeMedida);

		Bem bem = new Bem();
		bem.setCodigo("030");
		bem.setNome("balde");
		bem.setDescricao("limpa o chão");
		bem.setQuantidade(new BigDecimal("2"));

		System.out.println(categoria);
		System.out.println(unidadeDeMedida);
		BemControle bemControle = new BemControle();
		bemControle.salvar(bem, categoria, unidadeDeMedida);

	}
}
