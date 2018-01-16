package br.com.empresa.bonal.controles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.Logger;
import org.primefaces.context.RequestContext;

import br.com.empresa.bonal.entidades.UnidadeDeMedida;
import br.com.empresa.bonal.repositorio.UnidadeDeMedidaRepositorio;
import br.com.empresa.bonal.util.FacesContextUtil;
import br.com.empresa.bonal.util.tx.transacional;

@Named
@ViewScoped
public class UnidadeDeMedidaControle implements Serializable {
	private static final long serialVersionUID = 1L;

	private UnidadeDeMedida unidadeDeMedida = new UnidadeDeMedida();

	private Long unidadeDeMedidaId;
	private Boolean status = true;

	// Atributos para Consulta
	private String unidadeDeMedidaNome = "";

	// Listas para Consulta
	private List<UnidadeDeMedida> unidadesDeMedida;
	private List<UnidadeDeMedida> lista = new ArrayList<>();

	@Inject
	private UnidadeDeMedidaRepositorio unidadeDeMedidaRepositorio;

	@Inject
	private FacesContextUtil facesContext;

	@Inject
	private RequestContext requestContext;

	@Inject
	private Logger logger;

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
	@transacional
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

		if (status.equals(true))
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
	@transacional
	public String salvar() {
		String message = "";
		this.unidadeDeMedida.setStatus(true);
		if (unidadeDeMedida.getId() == null) {
			UnidadeDeMedida existe = unidadeDeMedidaRepositorio.unidadeMedidaExiste(unidadeDeMedida);
			if (existe != null) {
				facesContext.warn("Já existe essa unidade de medida registrada.");
				return null;
			}
			unidadeDeMedidaRepositorio.adicionar(unidadeDeMedida);
			message += "Unidade de Medida Cadastrada com Sucesso.";
		} else {
			unidadeDeMedidaRepositorio.atualizar(unidadeDeMedida);
			message += "Unidade de Medida Atualizada com Sucesso.";
		}
		logger.info(message);
		facesContext.info(message);
		unidadeDeMedida = new UnidadeDeMedida();
		return null;
	}

	@transacional
	public void recuperarUnidadeDeMedidaPorId() {
		unidadeDeMedida = unidadeDeMedidaRepositorio.buscarPorId(unidadeDeMedidaId);
	}

	// Remove um cargo do banco de dados
	@transacional
	public String remover(UnidadeDeMedida unidade) {
		unidade.setStatus(false);
		unidadeDeMedidaRepositorio.remover(unidade);
		this.unidadesDeMedida = null;
		listar();
		return null;
	}

	// Editar um cargo
	public String editar(UnidadeDeMedida unidade) {
		return "unidadeDeMedida?unidadeDeMedidaId=" + unidade.getId();
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
