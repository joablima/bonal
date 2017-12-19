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

import br.com.empresa.bonal.entidades.ItemBP;
import br.com.empresa.bonal.repositorio.ItemBPRepositorio;
import br.com.empresa.bonal.util.FacesContextUtil;

@ManagedBean
@ViewScoped
public class ItemBPControle implements Serializable{
	private static final long serialVersionUID = 1L;

	private ItemBP itemBP = new ItemBP();

	private Long itemBPId;

	// Atributos para Consulta
	private String itemBPNome = "";

	// Listas para Consulta
	private List<ItemBP> itens;
	private List<ItemBP> lista = new ArrayList<>();

	// Repositorio
	private ItemBPRepositorio itemBPRepositorio;

	// Construtor chamando a classe repositorio
	public ItemBPControle() {
		itemBPRepositorio = new ItemBPRepositorio();
	}

	// Getters and Setters
	public ItemBP getItemBP() {
		return itemBP;
	}

	// Adicionado para propriedade de contexto das tabelas do Primefaces
	public void setItemBP(ItemBP itemBP) {
		this.itemBP = itemBP;
	}

	public Long getItemBPId() {
		return itemBPId;
	}

	public void setItemBPId(Long itemBPId) {
		this.itemBPId = itemBPId;
	}

	public String getItemBPNome() {
		return itemBPNome;
	}

	public void setItemBPNome(String itemBPNome) {
		this.itemBPNome = itemBPNome;
	}

	public List<ItemBP> getItens() {
		return itens;
	}

	public List<ItemBP> getLista() {
		return Collections.unmodifiableList(lista);
	}

	// retorna total de lista do banco de dados
	public Integer getTotalItens() {
		return lista.size();
	}

	// retorna total da lista filtrada
	public Integer getTotalItensConsulta() {
		return itens.size();
	}

	// ----------------- METODOS ----------------------
	@PostConstruct
	public void listarTabela() {
		if (this.itens == null) {
			lista = itemBPRepositorio.listarTodos();
			itens = new ArrayList<>(lista);
		}
		filtrarTabela();
	}

	public void filtrarTabela() {
		Stream<ItemBP> filter = lista.stream();

		if (!itemBPNome.equals(null))
			filter = filter.filter(i -> (i.getId().toString().toLowerCase().contains(itemBPNome.toLowerCase().trim()))
					| (i.getBem().getNome().toLowerCase().contains(itemBPNome.toLowerCase().trim()))
					| (i.getDataDeGarantia().toString().toLowerCase().contains(itemBPNome.toLowerCase().trim())));

		itens = filter.collect(Collectors.toList());
	}

	// Método chamado ao carregar pagina de consulta para popular tabela
	public String listar() {
		listarTabela();
		return null;
	}
	
	// Limpar tabela da consulta
	public String limpar() {
		limparFiltros();
		this.itens = new ArrayList<>(this.lista);
		return null;
	}

	public void limparFiltros() {
		this.itemBPNome = "";
	}

	// Métodos que utilizam métodos do repositório
	public String salvar() {
		String message = "";
		if (itemBP.getId() == null) {
			itemBPRepositorio.adicionar(itemBP);
			message += "ItemBP cadastrado com Sucesso.";
		} else {
			itemBPRepositorio.atualizar(itemBP);
			message += "ItemBP atualizado com Sucesso.";
		}
		new FacesContextUtil().info(message);
		System.out.println(message);
		itemBP = new ItemBP();
		return null;
	}
	
	public void salvar(ItemBP i) {
		this.itemBP = i;
		salvar();
	}

	public void recuperarItemBPPorId() {
		itemBP = itemBPRepositorio.buscarPorId(itemBPId);
	}

	// Remove um objeto do banco de dados
	public void remover() {
		itemBPRepositorio.remover(itemBP);
		itens = null;
		listarTabela();
		itemBP = null;
	}

	public void remover(ItemBP itemBP) {
		this.itemBP = itemBP;
		remover();
	}

	// Editar objeto
	public String editar() {
		itemBPId = this.itemBP.getId();
		return "itemBP?itemBPId=" + itemBPId;
	}

	public String editar(ItemBP itemBP) {
		this.itemBP = itemBP;
		return editar();
	}

	public boolean itemBPIdExiste() {
		if (this.itemBPId == null)
			return false;
		return true;
	}
}
