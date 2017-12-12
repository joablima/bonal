//package br.com.empresa.bonal.controles;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//import javax.annotation.PostConstruct;
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.ViewScoped;
//
//import br.com.empresa.bonal.entidades.Categoria;
//import br.com.empresa.bonal.repositorio.CategoriaRepositorio;
//import br.com.empresa.bonal.util.FacesContextUtil;
//
//
//@ManagedBean
//@ViewScoped
//public class CategoriaControle {
//	
//	private Categoria categoria = new Categoria();
//
//	private Long categoriaId;
//
//	// Atributos para Consulta
//	private String categoriaNome = "";
//
//	// Listas para Consulta
//	private List<Categoria> categorias;
//	private List<Categoria> lista = new ArrayList<>();
//
//	// Repositorio
//	private CategoriaRepositorio categoriaRepositorio;
//
//	// Construtor chamando a classe repositorio
//	public CategoriaControle() {
//		categoriaRepositorio = new CategoriaRepositorio();
//	}
//
//	// Getters and Setters
//	public Categoria getCargo() {
//		return categoria;
//	}
//
//	// Adicionado para propriedade de contexto das tabelas do Primefaces
//	public void setCargo(Categoria cargo) {
//		this.categoria = cargo;
//	}
//
//	public Long getCargoId() {
//		return categoriaId;
//	}
//
//	public void setCargoId(Long cargoId) {
//		this.categoriaId = cargoId;
//	}
//
//	public String getCargoNome() {
//		return categoriaNome;
//	}
//
//	public void setCargoNome(String cargoNome) {
//		this.categoriaNome = cargoNome;
//	}
//
//	public List<Categoria> getCargos() {
//		return categorias;
//	}
//
//	public List<Categoria> getLista() {
//		return Collections.unmodifiableList(lista);
//	}
//
//	// verificar importancia dos métodos abaixo //verificar se estão trocados??
//	public Integer getTotalCargos() {
//		return lista.size();
//	}
//
//	public Integer getTotalCargosConsultados() {
//		return categorias.size();
//	}
//
//	// ----------------- METODOS ----------------------
//	@PostConstruct
//	public void listarTabela() {
//		if (this.categorias == null) {
//			lista = categoriaRepositorio.listarCategorias(categoriaNome);
//			categorias = new ArrayList<>(lista);
//		}
//		filtrarTabela();
//	}
//
//	public void filtrarTabela() {
//		Stream<Categoria> filter = lista.stream()
//				.filter(c -> (c.getNome().toLowerCase().contains(categoriaNome.toLowerCase().trim())));
//
//		categorias = filter.collect(Collectors.toList());
//	}
//
//	// Método chamado ao carregar pagina de consulta para popular tabela
//	public String listar() {
//		listarTabela();
//		return null;
//	}
//
//	// Limpar tabela da consulta,
//	public String limpar() {
//		this.categoriaNome = "";
//		// listarCargos(); // Realiza nova consulta ao repositorio
//		filtrarTabela(); // Retorna a lista unmodifiablelist offline armazenada
//		return null;
//	}
//
//	// Métodos que utilizam métodos do repositório
//	public String salvar() {
//		String message = "";
//		if (categoria.getId() == null) {
//			categoriaRepositorio.adicionar(categoria);
//			message += "Categoria Cadastrado com Sucesso.";
//		} else {
//			categoriaRepositorio.atualizar(categoria);
//			message += "Categoria Atualizado com Sucesso.";
//		}
//		new FacesContextUtil().info(message);
//		categoria = new Categoria();
//		return null;
//	}
//
//	public void recuperarCargoPorId() {
//		categoria = categoriaRepositorio.getCategoria(categoriaId);
//	}
//
//	// Remove um cargo do banco de dados
//	public void remover() {
//		categoriaRepositorio.remover(categoria);
//		categorias = null;
//		listarTabela();
//		categoria = null;
//	}
//
//	public void remover(Categoria c) {
//		this.categoria = c;
//		remover();
//	}
//
//	// Editar um cargo
//	public String editar() {
//		categoriaId = this.categoria.getId();
//		return "categoria?categoriaid=" + categoriaId;
//	}
//
//	public String editar(Categoria c) {
//		this.categoria = c;
//		return editar();
//	}
//
//	public boolean cargoIdExiste() {
//		if (this.categoriaId == null)
//			return false;
//		return true;
//	}
//	
//}
