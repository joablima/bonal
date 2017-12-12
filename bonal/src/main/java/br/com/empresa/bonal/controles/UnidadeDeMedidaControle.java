package br.com.empresa.bonal.controles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.empresa.bonal.entidades.UnidadeDeMedida;
import br.com.empresa.bonal.repositorio.UnidadeDeMedidaRepositorio;
import br.com.empresa.bonal.util.FacesContextUtil;

@ManagedBean
@ViewScoped
public class UnidadeDeMedidaControle {

	private UnidadeDeMedida unidadeDeMedida = new UnidadeDeMedida();

	private Long unidadeDeMedidaId;

	// Atributos para Consulta
	private String unidadeDeMedidaNome = "";

	// Listas para Consulta
	private List<UnidadeDeMedida> unidadesDeMedida;
	private List<UnidadeDeMedida> lista = new ArrayList<>();

	// Repositorio
	private UnidadeDeMedidaRepositorio unidadeDeMedidaRepositorio;

	// Construtor chamando a classe repositorio
	public UnidadeDeMedidaControle() {
		unidadeDeMedidaRepositorio = new UnidadeDeMedidaRepositorio();
	}

	// Getters and Setters
	public UnidadeDeMedida getUnidadeDeMedida() {
		return unidadeDeMedida;
	}

	// Adicionado para propriedade de contexto das tabelas do Primefaces
	public void setUnidadeDeMedida(UnidadeDeMedida unidadeDeMedida) {
		this.unidadeDeMedida = unidadeDeMedida;
	}

	public Long getUnidadeDeMedidaId() {
		return unidadeDeMedidaId;
	}

	public void setUnidadeDeMedidaId(Long unidadeDeMedidaId) {
		this.unidadeDeMedidaId = unidadeDeMedidaId;
	}

	public String getUnidadeDeMedidaNome() {
		return unidadeDeMedidaNome;
	}

	public void setUnidadeDeMedidaNome(String unidadeDeMedidaNome) {
		this.unidadeDeMedidaNome = unidadeDeMedidaNome;
	}

	public List<UnidadeDeMedida> getUnidadesDeMedida() {
		return unidadesDeMedida;
	}

	public List<UnidadeDeMedida> getLista() {
		return Collections.unmodifiableList(lista);
	}

	// verificar importancia dos métodos abaixo //verificar se estão trocados??
	public Integer getTotalUnidadesDeMedida() {
		return lista.size();
	}

	public Integer getTotalUnidadesDeMedidaConsulta() {
		return unidadesDeMedida.size();
	}

	// ----------------- METODOS ----------------------
	@PostConstruct
	public void listarTabela() {
		if (this.unidadesDeMedida == null) {
			lista = unidadeDeMedidaRepositorio.listarUnidadesDeMedida(unidadeDeMedidaNome);
			unidadesDeMedida = new ArrayList<>(lista);
		}
		filtrarTabela();
	}

	public void filtrarTabela() {
		Stream<UnidadeDeMedida> filter = lista.stream()
				.filter(c -> (c.getNome().toLowerCase().contains(unidadeDeMedidaNome.toLowerCase().trim())));

		unidadesDeMedida = filter.collect(Collectors.toList());
	}

	// Método chamado ao carregar pagina de consulta para popular tabela
	public String listar() {
		listarTabela();
		return null;
	}

	// Limpar tabela da consulta,
	public String limpar() {
		this.unidadeDeMedidaNome = "";
		// listarCargos(); // Realiza nova consulta ao repositorio
		filtrarTabela(); // Retorna a lista unmodifiablelist offline armazenada
		return null;
	}

	// Métodos que utilizam métodos do repositório
	public String salvar() {
		System.out.println("entrou em salvar");
		String message = "";
		if (unidadeDeMedida.getId() == null) {
			unidadeDeMedidaRepositorio.adicionar(unidadeDeMedida);
			message += "Cargo Cadastrado com Sucesso.";
		} else {
			unidadeDeMedidaRepositorio.atualizar(unidadeDeMedida);
			message += "Cargo Atualizado com Sucesso.";
		}
		new FacesContextUtil().info(message);
		// System.out.println(cargo);
		unidadeDeMedida = new UnidadeDeMedida();
		return null;
	}

	public void recuperarUnidadeDeMedidaPorId() {
		unidadeDeMedida = unidadeDeMedidaRepositorio.getUnidadeDeMedida(unidadeDeMedidaId);
	}

	// Remove um cargo do banco de dados
	public void remover() {
		unidadeDeMedidaRepositorio.remover(unidadeDeMedida);
		unidadesDeMedida = null;
		listarTabela();
		unidadeDeMedida = null;
	}

	public void remover(UnidadeDeMedida c) {
		this.unidadeDeMedida = c;
		remover();
	}

	// Editar um cargo
	public String editar() {
		unidadeDeMedidaId = this.unidadeDeMedida.getId();
		return "cargo?cargoid=" + unidadeDeMedidaId;
	}

	public String editar(UnidadeDeMedida c) {
		this.unidadeDeMedida = c;
		return editar();
	}

	public boolean cargoIdExiste() {
		if (this.unidadeDeMedidaId == null)
			return false;
		return true;
	}

}
