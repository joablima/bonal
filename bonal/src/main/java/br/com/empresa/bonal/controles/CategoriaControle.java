package br.com.empresa.bonal.controles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import br.com.empresa.bonal.entidades.Categoria;
import br.com.empresa.bonal.repositorio.CategoriaRepositorio;
import br.com.empresa.bonal.util.FacesContextUtil;
import br.com.empresa.bonal.util.enums.EnumCategoria;

@ManagedBean
@ViewScoped
public class CategoriaControle implements Serializable {
	private static final long serialVersionUID = 1L;

	final static Logger logger = Logger.getLogger(CategoriaControle.class);

	private Categoria categoria = new Categoria();

	private Long categoriaId;

	// Atributos para Consulta
	private String categoriaNome = "";

	// Listas para Consulta
	private List<Categoria> categorias;
	private List<Categoria> categoriasDeBem;
	private List<Categoria> categoriasDeServico;
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

	// ----- Carrega os Enums em Arrays -----
	public EnumCategoria[] getEnumCategoria() {
		return EnumCategoria.values();
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public List<Categoria> getCategoriasDeServico() {
		categoriasDeServico = filtraTipo("servico");
		return categoriasDeServico;
	}

	public List<Categoria> getCategoriasDeBem() {
		categoriasDeBem = filtraTipo("bem");
		return categoriasDeBem;
	}

	public List<Categoria> filtraTipo(String tipo) {
		Stream<Categoria> stream = lista.stream();
		stream = stream.filter(c -> (c.getTipo().toString().toLowerCase().contains(tipo)));
		return stream.collect(Collectors.toList());
	}

	public List<Categoria> getLista() {
		return Collections.unmodifiableList(lista);
	}

	// verificar importancia dos m�todos abaixo //verificar se est�o trocados??
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

	// M�todo chamado ao carregar pagina de consulta para popular tabela
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
		salvar();
	}

	// M�todos que utilizam m�todos do reposit�rio
	public String salvar() {
		String message = "";
		this.categoria.setStatus(true);

		if (categoria.getId() == null) {
			Categoria existe = categoriaRepositorio.codigoExiste(categoria);
			if (existe != null) {
				message += "Já existe uma Categoria registrada com esse código." + existe.resumo();
				new FacesContextUtil().warn(message);
				return null;
			}
			categoriaRepositorio.adicionar(categoria);
			message += "Categoria Cadastrada com Sucesso.";
		} else {
			categoriaRepositorio.atualizar(categoria);
			message += "Categoria Atualizada com Sucesso.";
		}
		new FacesContextUtil().info(message);
		logger.info(message);
		categoria = new Categoria();
		return null;
	}

	public void recuperarCategoriaPorId() {
		categoria = categoriaRepositorio.buscarPorId(categoriaId);
	}

	// Remove um Categoria do banco de dados
	public void remover() {
		categoria.setStatus(false);
		categoriaRepositorio.atualizar(categoria);
		categorias = null;
		listarTabela();
		categoria = null;
	}

	public void remover(Categoria c) {
		this.categoria = c;
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
