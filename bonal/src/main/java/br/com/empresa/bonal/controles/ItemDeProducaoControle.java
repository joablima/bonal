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

import br.com.empresa.bonal.entidades.ItemDeProducao;
import br.com.empresa.bonal.repositorio.ItemDeProducaoRepositorio;
import br.com.empresa.bonal.util.FacesContextUtil;
import br.com.empresa.bonal.util.tx.Transacional;

@Named
@ViewScoped
public class ItemDeProducaoControle implements Serializable {
	private static final long serialVersionUID = 1L;

	private ItemDeProducao itemDeProducao = new ItemDeProducao();

	private Long itemDeProducaoId;

	// Atributos para Consulta
	private String itemDeProducaoNome = "";

	private Boolean status = true;
	// Listas para Consulta
	private List<ItemDeProducao> itensDeProducao;
	private List<ItemDeProducao> lista = new ArrayList<>();

	@Inject
	private RequestContext requestContext;

	@Inject
	private ItemDeProducaoRepositorio itemDeProducaoRepositorio;

	@Inject
	private FacesContextUtil facesContext;

	@Inject
	private Logger logger;

	// Getters and Setters
	public ItemDeProducao getItemDeProducao() {
		return itemDeProducao;
	}

	// Adicionado para propriedade de contexto das tabelas do Primefaces
	public void setItemDeProducao(ItemDeProducao c) {
		this.itemDeProducao = c;
	}

	public Long getItemDeProducaoId() {
		return itemDeProducaoId;
	}

	public void setItemDeProducaoId(Long itemDeProducaoId) {
		this.itemDeProducaoId = itemDeProducaoId;
	}

	public String getItemDeProducaoNome() {
		return itemDeProducaoNome;
	}

	public void setItemDeProducaoNome(String itemDeProducaoNome) {
		this.itemDeProducaoNome = itemDeProducaoNome;
	}

	public List<ItemDeProducao> getItensDeProducao() {
		return itensDeProducao;
	}

	public List<ItemDeProducao> getLista() {
		return Collections.unmodifiableList(lista);
	}

	// verificar importancia dos m�todos abaixo //verificar se est�o trocados??
	public Integer getTotalItensDeProducao() {
		return lista.size();
	}

	public Integer getTotalItensDeProducaoConsulta() {
		return itensDeProducao.size();
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
		if (this.itensDeProducao == null) {
			lista = itemDeProducaoRepositorio.listarTodos();
			itensDeProducao = new ArrayList<>(lista);
		}
		filtrarTabela();
	}

	public void filtrarTabela() {
		Stream<ItemDeProducao> stream = lista.stream();

		stream = stream.filter(c -> (c.getNome().toLowerCase().contains(itemDeProducaoNome.toLowerCase().trim()))
				| (c.getCodigo().toLowerCase().contains(itemDeProducaoNome.toLowerCase().trim()))
				| (c.getSubCategoria().getCategoria().getNome().toLowerCase().contains(itemDeProducaoNome.toLowerCase().trim()))
				| (c.getSubCategoria().getNome().toLowerCase().contains(itemDeProducaoNome.toLowerCase().trim())));

		if (status.equals(true))
			stream = stream.filter(c -> (c.getStatus().equals(status)));

		itensDeProducao = stream.collect(Collectors.toList());
	}

	// M�todo chamado ao carregar pagina de consulta para popular tabela
	public String listar() {
		listarTabela();
		return null;
	}

	// Limpar tabela da consulta
	public String limpar() {
		limparFiltros();
		this.itensDeProducao = new ArrayList<>(this.lista);
		return null;
	}

	public void limparFiltros() {
		this.itemDeProducaoNome = "";
	}

	// Remove um Categoria do banco de dados
	@Transacional
	public String salvar(ItemDeProducao itemDeProducao) {
		itemDeProducao.setStatus(true);
		itemDeProducaoRepositorio.atualizar(itemDeProducao);
		this.itensDeProducao = null;
		this.itemDeProducao = new ItemDeProducao();
		listarTabela();
		return null;
	}

	@Transacional
	public void recuperarItemDeProducaoPorId() {
		itemDeProducao = itemDeProducaoRepositorio.buscarPorId(itemDeProducaoId);
	}

	// Editar um Categoria
	public String editar(ItemDeProducao itemDeProducao) {
		return "itemDeProducao?faces-redirect=true&itemDeProducaoId=" + itemDeProducao.getId();
	}

	public boolean itemDeProducaoIdExiste() {
		if (this.itemDeProducaoId == null)
			return false;
		return true;
	}

	// Método usado para carregar objeto para o dialog
	public void selecionarItemDeProducao(ItemDeProducao itemDeProducao) {
		requestContext.closeDialog(itemDeProducao);
	}

}
