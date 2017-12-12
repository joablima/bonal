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
import br.com.empresa.bonal.repositorio.BemRepositorio;
import br.com.empresa.bonal.util.FacesContextUtil;

@ManagedBean
@ViewScoped
public class BemControle {

	private Bem bem = new Bem();

	private Long bemId;

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
	public void setBem(Bem b) {
		this.bem = b;
	}

	public Long getBemId() {
		return bemId;
	}

	public void setBemId(Long bemId) {
		this.bemId = bemId;
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

	// verificar importancia dos m�todos abaixo //verificar se est�o trocados??
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
			lista = bemRepositorio.listarBens(bemNome);
			bens = new ArrayList<>(lista);
		}
		filtrarTabela();
	}

	public void filtrarTabela() {
		Stream<Bem> filter = lista.stream()
				.filter(c -> (c.getNome().toLowerCase().contains(bemNome.toLowerCase().trim()))
						| (c.getCodigo().toLowerCase().contains(bemNome.toLowerCase().trim()))
						| c.getDescricao().toLowerCase().contains(bemNome.toLowerCase().trim())
						| (c.getCategoria().getCodigo().toLowerCase().contains(bemNome.toLowerCase().trim()))
						| (c.getQuantidade().toString().toLowerCase().contains(bemNome.toLowerCase().trim()))
						);
	
		
		bens = filter.collect(Collectors.toList());
	}

	// M�todo chamado ao carregar pagina de consulta para popular tabela
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

	// M�todos que utilizam m�todos do reposit�rio
	public String salvar() {
		String message = "";
		if (bem.getId() == null) {
			bemRepositorio.adicionar(bem);
			message += "Categoria Cadastrado com Sucesso.";
		} else {
			bemRepositorio.atualizar(bem);
			message += "Categoria Atualizado com Sucesso.";
		}
		new FacesContextUtil().info(message);
		bem = new Bem();
		return null;
	}

	public void recuperarBemPorId() {
		bem = bemRepositorio.getBem(bemId);
	}

	// Remove um Categoria do banco de dados
	public void remover() {
		bemRepositorio.remover(bem);
		bens = null;
		listarTabela();
		bem = null;
	}

	public void remover(Bem c) {
		this.bem = c;
		remover();
	}

	// Editar um Categoria
	public String editar() {
		bemId = this.bem.getId();
		return "bem?bemid=" + bemId;
	}

	public String editar(Bem categoria) {
		this.bem = categoria;
		return editar();
	}

	public boolean BemIdExiste() {
		if (this.bemId == null)
			return false;
		return true;
	}

}