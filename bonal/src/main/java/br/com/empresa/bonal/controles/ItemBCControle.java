package br.com.empresa.bonal.controles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.empresa.bonal.entidades.ItemBC;
import br.com.empresa.bonal.repositorio.ItemBCRepositorio;
import br.com.empresa.bonal.util.FacesContextUtil;

@ManagedBean
@ViewScoped
public class ItemBCControle {

	private ItemBC itemBC = new ItemBC();

	private Long itemBCId;

	// Atributos para Consulta
	private String itemBCNome = "";

	// Listas para Consulta
	private List<ItemBC> itens;
	private List<ItemBC> lista = new ArrayList<>();

	// Repositorio
	private ItemBCRepositorio itemBCRepositorio;

	// Construtor chamando a classe repositorio
	public ItemBCControle() {
		itemBCRepositorio = new ItemBCRepositorio();
	}

	// Getters and Setters
	public ItemBC getItemBC() {
		return itemBC;
	}

	// Adicionado para propriedade de contexto das tabelas do Primefaces
	public void setItemBC(ItemBC itemBC) {
		this.itemBC = itemBC;
	}

	public Long getItemBCId() {
		return itemBCId;
	}

	public void setItemBCId(Long itemBCId) {
		this.itemBCId = itemBCId;
	}

	public String getItemBCNome() {
		return itemBCNome;
	}

	public void setItemBCNome(String itemBCNome) {
		this.itemBCNome = itemBCNome;
	}

	public List<ItemBC> getItens() {
		return itens;
	}

	public List<ItemBC> getLista() {
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
			lista = itemBCRepositorio.listarTodos();
			itens = new ArrayList<>(lista);
		}
		filtrarTabela();
	}

	public void filtrarTabela() {
		Stream<ItemBC> filter = lista.stream();

		if (!itemBCNome.equals(null))
			filter = filter.filter(i -> (i.getId().toString().toLowerCase().contains(itemBCNome.toLowerCase().trim()))
					| (i.getBem().getNome().toLowerCase().contains(itemBCNome.toLowerCase().trim()))
					| (i.getDataDeValidade().toString().toLowerCase().contains(itemBCNome.toLowerCase().trim())));

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
		this.itemBCNome = "";
	}

	// Métodos que utilizam métodos do repositório
	public String salvar() {
		String message = "";
		if (itemBC.getId() == null) {
			itemBCRepositorio.adicionar(itemBC);
			message += "ItemBC cadastrado com Sucesso.";
		} else {
			itemBCRepositorio.atualizar(itemBC);
			message += "ItemBC atualizado com Sucesso.";
		}
		new FacesContextUtil().info(message);
		System.out.println(message);
		itemBC = new ItemBC();
		return null;
	}
	
	public void salvar(ItemBC i) {
		this.itemBC = i;
		salvar();
	}

	public void recuperarItemBCPorId() {
		itemBC = itemBCRepositorio.buscarPorId(itemBCId);
	}

	// Remove um objeto do banco de dados
	public void remover() {
		itemBCRepositorio.remover(itemBC);
		itens = null;
		listarTabela();
		itemBC = null;
	}

	public void remover(ItemBC itemBC) {
		this.itemBC = itemBC;
		remover();
	}

	// Editar objeto
	public String editar() {
		itemBCId = this.itemBC.getId();
		return "itemBC?itemBCId=" + itemBCId;
	}

	public String editar(ItemBC itemBC) {
		this.itemBC = itemBC;
		return editar();
	}

	public boolean itemBCIdExiste() {
		if (this.itemBCId == null)
			return false;
		return true;
	}
}
