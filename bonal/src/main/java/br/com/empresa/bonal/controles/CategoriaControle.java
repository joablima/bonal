package br.com.empresa.bonal.controles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.empresa.bonal.entidades.Categoria;
import br.com.empresa.bonal.repositorio.CategoriaRepositorio;
import br.com.empresa.bonal.util.FacesContextUtil;

@ManagedBean
@ViewScoped
public class CategoriaControle {

	private Categoria categoria = new Categoria();

	private Long categoriaId;

	// Atributos para Consulta
	private String categoriaNome = "";

	// Listas para Consulta
	private List<Categoria> categorias;
	private List<Categoria> lista = new ArrayList<>();

	// Repositorio
	private CategoriaRepositorio categoriaRepositorio;

	// Construtor chamando a classe repositorio
	public CategoriaControle() {
		categoriaRepositorio = new CategoriaRepositorio();
	}

	// Getters and Setters
	public Categoria getCategoria() {
		return categoria;
	}

	// Adicionado para propriedade de contexto das tabelas do Primefaces
	public void setCategoria(Categoria c) {
		this.categoria = c;
	}

	public Long getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Long categoriaId) {
		this.categoriaId = categoriaId;
	}

	public String getCategoriaNome() {
		return categoriaNome;
	}

	public void setCategoriaNome(String categoriaNome) {
		this.categoriaNome = categoriaNome;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public List<Categoria> getLista() {
		return Collections.unmodifiableList(lista);
	}

	// verificar importancia dos métodos abaixo //verificar se estão trocados??
	public Integer getTotalCategorias() {
		return lista.size();
	}

	public Integer getTotalCategoriasConsulta() {
		return categorias.size();
	}

	// ----------------- METODOS ----------------------
	@PostConstruct
	public void listarTabela() {
		if (this.categorias == null) {
			lista = categoriaRepositorio.listarTodos();
			categorias = new ArrayList<>(lista);
		}
		filtrarTabela();
	}

	public void filtrarTabela() {
		Stream<Categoria> stream = lista.stream();

		stream = stream.filter(c -> (c.getNome().toLowerCase().contains(categoriaNome.toLowerCase().trim()))
				| (c.getCodigo().toLowerCase().contains(categoriaNome.toLowerCase().trim()))
				| c.getDescricao().toLowerCase().contains(categoriaNome.toLowerCase().trim()));

		categorias = stream.collect(Collectors.toList());
	}

	// Método chamado ao carregar pagina de consulta para popular tabela
	public String listar() {
		listarTabela();
		return null;
	}

	// Limpar tabela da consulta
	public String limpar() {
		limparFiltros();
		this.categorias = new ArrayList<>(this.lista);
		return null;
	}

	public void limparFiltros() {
		this.categoriaNome = "";
	}

	public void salvar(Categoria c) {
		this.categoria = c;
		this.categoria.setStatus(1);
		salvar();
	}

	// Métodos que utilizam métodos do repositório
	public String salvar() {
		String message = "";
		categoria.setStatus(1);
		
		if (categoria.getId() == null) {
			categoriaRepositorio.adicionar(categoria);
			message += "Categoria Cadastrada com Sucesso.";
		} else {
			categoriaRepositorio.atualizar(categoria);
			message += "Categoria Atualizada com Sucesso.";
		}
		new FacesContextUtil().info(message);
		System.out.println(message);
		categoria = new Categoria();
		return null;
	}

	public void recuperarCategoriaPorId() {
		categoria = categoriaRepositorio.buscarPorId(categoriaId);
	}

	// Remove um Categoria do banco de dados
	public void remover() {
		categoria.setStatus(0);
		categoriaRepositorio.remover(categoria);
		categorias = null;
		listarTabela();
		categoria = null;
	}

	public void remover(Categoria c) {
		this.categoria = c;
		this.categoria.setStatus(0);
		remover();
	}

	// Editar um Categoria
	public String editar() {
		categoriaId = this.categoria.getId();
		return "categoria?categoriaId=" + categoriaId;
	}

	public String editar(Categoria categoria) {
		this.categoria = categoria;
		return editar();
	}

	public boolean CategoriaIdExiste() {
		if (this.categoriaId == null)
			return false;
		return true;
	}

}
