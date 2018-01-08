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

import br.com.empresa.bonal.entidades.UnidadeDeMedida;
import br.com.empresa.bonal.repositorio.UnidadeDeMedidaRepositorio;
import br.com.empresa.bonal.util.FacesContextUtil;

@ManagedBean
@ViewScoped
public class UnidadeDeMedidaControle implements Serializable {
	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(UnidadeDeMedidaControle.class);

	private UnidadeDeMedida unidadeDeMedida = new UnidadeDeMedida();

	private Long unidadeDeMedidaId;
	private Boolean status = true;

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

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
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

	// verificar importancia dos m�todos abaixo //verificar se est�o trocados??
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
			lista = unidadeDeMedidaRepositorio.listarTodos();
			unidadesDeMedida = new ArrayList<>(lista);
		}
		filtrarTabela();
	}

	public void filtrarTabela() {
		Stream<UnidadeDeMedida> stream = lista.stream();

		stream = stream.filter(u -> (u.getNome().toLowerCase().contains(unidadeDeMedidaNome.toLowerCase().trim())));

		stream = stream.filter(u -> (u.getStatus().equals(status)));

		unidadesDeMedida = stream.collect(Collectors.toList());
	}

	// M�todo chamado ao carregar tabela
	public String listar() {
		listarTabela();
		return null;
	}

	// Limpar tabela da consulta
	public String limpar() {
		limparFiltros();
		this.unidadesDeMedida = new ArrayList<>(this.lista);
		return null;
	}

	public void limparFiltros() {
		this.unidadeDeMedidaNome = "";
	}

	public void salvar(UnidadeDeMedida d) {
		this.unidadeDeMedida = d;
		salvar();
	}

	// M�todos que utilizam m�todos do reposit�rio
	public String salvar() {
		String message = "";
		this.unidadeDeMedida.setStatus(true);
		if (unidadeDeMedida.getId() == null) {
			UnidadeDeMedida existe = unidadeDeMedidaRepositorio.unidadeMedidaExiste(unidadeDeMedida);
			if (existe != null) {
				new FacesContextUtil().warn("Já existe essa unidade de medida registrada.");
				return null;
			}
			unidadeDeMedidaRepositorio.adicionar(unidadeDeMedida);
			message += "Unidade de Medida Cadastrada com Sucesso.";
		} else {
			unidadeDeMedidaRepositorio.atualizar(unidadeDeMedida);
			message += "Unidade de Medida Atualizada com Sucesso.";
		}
		logger.info(message);
		new FacesContextUtil().info(message);
		unidadeDeMedida = new UnidadeDeMedida();
		return null;
	}

	public void recuperarUnidadeDeMedidaPorId() {
		unidadeDeMedida = unidadeDeMedidaRepositorio.buscarPorId(unidadeDeMedidaId);
	}

	// Remove um cargo do banco de dados
	public void remover() {
		this.unidadeDeMedida.setStatus(false);
		unidadeDeMedidaRepositorio.remover(unidadeDeMedida);
		unidadesDeMedida = null;
		listar();
	}

	public void remover(UnidadeDeMedida unidade) {
		this.unidadeDeMedida = unidade;
		remover();
	}

	// Editar um cargo
	public String editar() {
		unidadeDeMedidaId = this.unidadeDeMedida.getId();
		return "unidadeDeMedida?unidadeDeMedidaId=" + unidadeDeMedidaId;
	}

	public String editar(UnidadeDeMedida unidadeDeMedida) {
		this.unidadeDeMedida = unidadeDeMedida;
		return editar();
	}

	public String cancelar() {
		return "index";
	}

	public boolean cargoIdExiste() {
		if (this.unidadeDeMedidaId == null)
			return false;
		return true;
	}

}
