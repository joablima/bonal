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
import br.com.empresa.bonal.util.tx.Transacional;

@Named
@ViewScoped
public class UnidadeDeMedidaControle implements Serializable {
	private static final long serialVersionUID = 1L;

	private UnidadeDeMedida unidadeDeMedida = new UnidadeDeMedida();

	private Long unidadeDeMedidaId;

	// Atributos para Consulta
	private String unidadeDeMedidaNome = "";

	private Boolean status = true;
	// Listas para Consulta
	private List<UnidadeDeMedida> unidadesDeMedida;
	private List<UnidadeDeMedida> lista = new ArrayList<>();

	@Inject
	private RequestContext requestContext;

	@Inject
	private UnidadeDeMedidaRepositorio unidadeDeMedidaRepositorio;

	@Inject
	private FacesContextUtil facesContext;

	@Inject
	private Logger logger;

	// Getters and Setters
	public UnidadeDeMedida getUnidadeDeMedida() {
		return unidadeDeMedida;
	}

	// Adicionado para propriedade de contexto das tabelas do Primefaces
	public void setUnidadeDeMedida(UnidadeDeMedida c) {
		this.unidadeDeMedida = c;
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

	// verificar importancia dos m�todos abaixo //verificar se est�o trocados??
	public Integer getTotalUnidadesDeMedida() {
		return lista.size();
	}

	public Integer getTotalUnidadesDeMedidaConsulta() {
		return unidadesDeMedida.size();
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	// ----------------- METODOS ----------------------
	@PostConstruct
	@Transacional
	public void listarTabela() {
		if (this.unidadesDeMedida == null) {
			lista = unidadeDeMedidaRepositorio.listarTodos();
			unidadesDeMedida = new ArrayList<>(lista);
		}
		filtrarTabela();
	}

	public void filtrarTabela() {
		Stream<UnidadeDeMedida> stream = lista.stream();

		stream = stream.filter(c -> (c.getNome().toLowerCase().contains(unidadeDeMedidaNome.toLowerCase().trim()))
				| (c.getSigla().toLowerCase().contains(unidadeDeMedidaNome.toLowerCase().trim())));

		if (status.equals(true))
			stream = stream.filter(c -> (c.getStatus().equals(status)));

		unidadesDeMedida = stream.collect(Collectors.toList());
	}

	// M�todo chamado ao carregar pagina de consulta para popular tabela
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

	// Remove um Categoria do banco de dados
	@Transacional
	public String salvar(UnidadeDeMedida unidadeDeMedida) {
		unidadeDeMedida.setStatus(true);
		unidadeDeMedidaRepositorio.atualizar(unidadeDeMedida);
		this.unidadesDeMedida = null;
		this.unidadeDeMedida = new UnidadeDeMedida();
		listarTabela();
		return null;
	}

	// M�todos que utilizam m�todos do reposit�rio
	@Transacional
	public String salvar() {
		String message = "";
		this.unidadeDeMedida.setStatus(true);

		if (unidadeDeMedida.getId() == null) {
			UnidadeDeMedida existe = unidadeDeMedidaRepositorio.unidadeMedidaExiste(unidadeDeMedida);
			if (existe != null) {
				facesContext.warn("Já existe uma unidade de medida registrada com essa sigla.");
				return null;
			}

			unidadeDeMedidaRepositorio.adicionar(unidadeDeMedida);
			message += "Unidade de Medida Cadastrada com Sucesso.";
		} else {
			unidadeDeMedidaRepositorio.atualizar(unidadeDeMedida);
			message += "Unidade de medida Atualizada com Sucesso.";
		}
		facesContext.info(message);
		logger.info(message);
		unidadeDeMedida = new UnidadeDeMedida();
		return null;
	}

	@Transacional
	public void recuperarUnidadeDeMedidaPorId() {
		unidadeDeMedida = unidadeDeMedidaRepositorio.buscarPorId(unidadeDeMedidaId);
	}

	// Remove um Categoria do banco de dados
	@Transacional
	public String remover(UnidadeDeMedida unidadeDeMedida) {
		unidadeDeMedida.setStatus(false);
		unidadeDeMedidaRepositorio.atualizar(unidadeDeMedida);
		this.unidadesDeMedida = null;
		this.unidadeDeMedida = new UnidadeDeMedida();
		listarTabela();
		return null;
	}

	// Editar um Categoria
	public String editar(UnidadeDeMedida unidadeDeMedida) {
		return "unidadeDeMedida?unidadeDeMedidaId=" + unidadeDeMedida.getId();
	}

	public boolean unidadeDeMedidaIdExiste() {
		if (this.unidadeDeMedidaId == null)
			return false;
		return true;
	}

	// Método usado para carregar objeto para o dialog
	public void selecionarUnidadeDeMedida(UnidadeDeMedida unidadeDeMedida) {
		requestContext.closeDialog(unidadeDeMedida);
	}

}
