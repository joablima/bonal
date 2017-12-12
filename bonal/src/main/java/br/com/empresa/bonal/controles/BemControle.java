package br.com.empresa.bonal.controles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.empresa.bonal.entidades.Bem;
import br.com.empresa.bonal.entidades.Categoria;
import br.com.empresa.bonal.entidades.UnidadeDeMedida;
import br.com.empresa.bonal.repositorio.BemRepositorio;

@ManagedBean
@ViewScoped
public class BemControle {

	private Bem bem = new Bem();

	private Long bemId;

	private Long categoriaId;
	private Long unidadeDeMedidaId;

	// Atributos para Consulta
	private String bemNome = "";

	// Listas para Consulta
	private List<Bem> bens;
	private List<Bem> lista = new ArrayList<>();

	// Repositorio
	private BemRepositorio bemRepositorio;

	// Construtor chamando a classe repositorio
	public BemControle() {
		bemRepositorio = new BemRepositorio();
	}

	// Getters and Setters
	public Bem getBem() {
		return bem;
	}

	// Adicionado para propriedade de contexto das tabelas do Primefaces
	public void setBem(Bem bem) {
		this.bem = bem;
	}

	public Long getBemId() {
		return bemId;
	}

	public void setBemId(Long bemId) {
		this.bemId = bemId;
	}

	public Long getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Long categoriaId) {
		this.categoriaId = categoriaId;
	}

	public Long getUnidadeDeMedidaId() {
		return unidadeDeMedidaId;
	}

	public void setUnidadeDeMedidaId(Long unidadeDeMedidaId) {
		this.unidadeDeMedidaId = unidadeDeMedidaId;
	}

	public String getBemNome() {
		return bemNome;
	}

	public void setBemNome(String bemNome) {
		this.bemNome = bemNome;
	}

	public List<Bem> getBens() {
		return bens;
	}

	public List<Bem> getLista() {
		return Collections.unmodifiableList(lista);
	}

	// verificar importancia dos métodos abaixo //verificar se estão trocados??
	public Integer getTotalBens() {
		return lista.size();
	}

	public Integer getTotalBensConsulta() {
		return bens.size();
	}

	// ----------------- METODOS ----------------------
	@PostConstruct
	public void listarTabela() {
		if (this.bens == null) {
			lista = bemRepositorio.listarBens(bemNome, categoriaId, unidadeDeMedidaId);
			bens = new ArrayList<>(lista);
		}
		filtrarTabela();
	}

	public void filtrarTabela() {
		Stream<Bem> filter = lista.stream();

		if (!bemNome.equals(null)) {
			filter = filter.filter(c -> (c.getNome().toLowerCase().contains(bemNome.toLowerCase().trim()))
					| (c.getCodigo().toLowerCase().contains(bemNome.toLowerCase().trim()))
					| c.getDescricao().toLowerCase().contains(bemNome.toLowerCase().trim()));
		}

		if (categoriaId != null)
			filter = filter.filter(c -> (c.getCategoria().getId().equals(categoriaId)));

		if (unidadeDeMedidaId != null)
			filter = filter.filter(c -> (c.getUnidadeDeMedida().getId().equals(unidadeDeMedidaId)));

		bens = filter.collect(Collectors.toList());
	}

	// Método chamado ao carregar pagina de consulta para popular tabela
	public String listar() {
		listarTabela();
		return null;
	}

	// Limpar tabela da consulta,
	public String limpar() {
		this.bemNome = "";
		// listarCategorias(); // Realiza nova consulta ao repositorio
		filtrarTabela(); // Retorna a lista unmodifiablelist offline armazenada
		return null;
	}

	public void salvar(Bem b, Categoria c, UnidadeDeMedida u) {
		this.bem = b;
		this.categoriaId = c.getId();
		this.unidadeDeMedidaId = u.getId();
		salvar();
	}

	// Métodos que utilizam métodos do repositório
	public String salvar() {
		String message = "";
		if (bem.getId() == null) {
			bemRepositorio.adicionar(bem, categoriaId, unidadeDeMedidaId);
			message += "Bem Cadastrado com Sucesso.";
		} else {
			bemRepositorio.atualizar(bem, categoriaId, unidadeDeMedidaId);
			message += "Bem Atualizado com Sucesso.";
		}
		// new FacesContextUtil().info(message);
		System.out.println(message);
		bem = new Bem();
		return null;
	}

	public void recuperarBemPorId() {
		bem = bemRepositorio.getBem(bemId);
	}

	// Remove um Bem do banco de dados
	public void remover() {
		bemRepositorio.remover(bem);
		bens = null;
		listarTabela();
		bem = null;
	}

	public void remover(Bem bem) {
		this.bem = bem;
		remover();
	}

	// Editar um Bem
	public String editar() {
		bemId = this.bem.getId();
		return "bem?bemid=" + bemId;
	}

	public String editar(Bem bem) {
		this.bem = bem;
		return editar();
	}

	public boolean BemIdExiste() {
		if (this.bemId == null)
			return false;
		return true;
	}

}
