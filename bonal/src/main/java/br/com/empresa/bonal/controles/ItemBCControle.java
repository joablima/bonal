//package br.com.empresa.bonal.controles;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//import javax.annotation.PostConstruct;
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.ViewScoped;
//
//import br.com.empresa.bonal.entidades.ItemBC;
//import br.com.empresa.bonal.repositorio.ItemBCRepositorio;
//import br.com.empresa.bonal.util.FacesContextUtil;
//
//@ManagedBean
//@ViewScoped
//public class ItemBCControle {
//
//	private ItemBC itemBc = new ItemBC();
//
//	private Long itemBCId;
//
//	// Atributos para Consulta
//	private String ItemBCNome = "";
//
//	// Listas para Consulta
//	private List<ItemBC> itensBC;
//	private List<ItemBC> lista = new ArrayList<>();
//
//	// Repositorio
//	private ItemBCRepositorio ItemBCRepositorio;
//
//	// Construtor chamando a classe repositorio
//	public ItemBCControle() {
//		ItemBCRepositorio = new ItemBCRepositorio();
//	}
//
//	// Getters and Setters
//	public ItemBC getUnidadeDeMedida() {
//		return itemBc;
//	}
//
//	// Adicionado para propriedade de contexto das tabelas do Primefaces
//	public void setUnidadeDeMedida(ItemBC unidadeDeMedida) {
//		this.itemBc = unidadeDeMedida;
//	}
//
//	public Long getUnidadeDeMedidaId() {
//		return itemBCId;
//	}
//
//	public void setUnidadeDeMedidaId(Long unidadeDeMedidaId) {
//		this.itemBCId = unidadeDeMedidaId;
//	}
//
//	public String getUnidadeDeMedidaNome() {
//		return ItemBCNome;
//	}
//
//	public void setUnidadeDeMedidaNome(String unidadeDeMedidaNome) {
//		this.ItemBCNome = unidadeDeMedidaNome;
//	}
//
//	public List<ItemBC> getUnidadesDeMedida() {
//		return itensBC;
//	}
//
//	public List<ItemBC> getLista() {
//		return Collections.unmodifiableList(lista);
//	}
//
//	// verificar importancia dos métodos abaixo //verificar se estão trocados??
//	public Integer getTotalUnidadesDeMedida() {
//		return lista.size();
//	}
//
//	public Integer getTotalUnidadesDeMedidaConsulta() {
//		return itensBC.size();
//	}
//
//	// ----------------- METODOS ----------------------
//	@PostConstruct
//	public void listarTabela() {
//		if (this.itensBC == null) {
//			lista = ItemBCRepositorio.listarUnidadesDeMedida(ItemBCNome);
//			itensBC = new ArrayList<>(lista);
//		}
//		filtrarTabela();
//	}
//
//	public void filtrarTabela() {
//		Stream<ItemBC> filter = lista.stream()
//				.filter(c -> (c.getNome().toLowerCase().contains(ItemBCNome.toLowerCase().trim())));
//
//		itensBC = filter.collect(Collectors.toList());
//	}
//
//	// Método chamado ao carregar pagina de consulta para popular tabela
//	public String listar() {
//		listarTabela();
//		return null;
//	}
//
//	// Limpar tabela da consulta,
//	public String limpar() {
//		this.ItemBCNome = "";
//		// listarCargos(); // Realiza nova consulta ao repositorio
//		filtrarTabela(); // Retorna a lista unmodifiablelist offline armazenada
//		return null;
//	}
//
//	// Métodos que utilizam métodos do repositório
//	public String salvar() {
//		System.out.println("entrou em salvar");
//		String message = "";
//		if (itemBc.getId() == null) {
//			ItemBCRepositorio.adicionar(itemBc);
//			message += "Cargo Cadastrado com Sucesso.";
//		} else {
//			ItemBCRepositorio.atualizar(itemBc);
//			message += "Cargo Atualizado com Sucesso.";
//		}
//		new FacesContextUtil().info(message);
//		// System.out.println(cargo);
//		itemBc = new ItemBC();
//		return null;
//	}
//
//	public void recuperarUnidadeDeMedidaPorId() {
//		itemBc = ItemBCRepositorio.getUnidadeDeMedida(itemBCId);
//	}
//
//	// Remove um cargo do banco de dados
//	public void remover() {
//		ItemBCRepositorio.remover(itemBc);
//		itensBC = null;
//		listarTabela();
//		itemBc = null;
//	}
//
//	public void remover(ItemBC c) {
//		this.itemBc = c;
//		remover();
//	}
//
//	// Editar um cargo
//	public String editar() {
//		itemBCId = this.itemBc.getId();
//		return "cargo?cargoid=" + itemBCId;
//	}
//
//	public String editar(ItemBC c) {
//		this.itemBc = c;
//		return editar();
//	}
//
//	public boolean cargoIdExiste() {
//		if (this.itemBCId == null)
//			return false;
//		return true;
//	}
//
//}
