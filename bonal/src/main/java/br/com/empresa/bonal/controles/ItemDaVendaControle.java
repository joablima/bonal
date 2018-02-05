package br.com.empresa.bonal.controles;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.empresa.bonal.entidades.ItemDaVenda;
import br.com.empresa.bonal.entidades.Produto;
import br.com.empresa.bonal.entidades.UnidadeDeMedida;
import br.com.empresa.bonal.entidades.Venda;
import br.com.empresa.bonal.repositorio.ItemDaVendaRepositorio;
import br.com.empresa.bonal.repositorio.ProdutoRepositorio;
import br.com.empresa.bonal.util.FacesContextUtil;
import br.com.empresa.bonal.util.logging.Logging;
import br.com.empresa.bonal.util.tx.Transacional;

@Named
@ViewScoped
public class ItemDaVendaControle implements Serializable {
	private static final long serialVersionUID = 1L;

	private ItemDaVenda itemDaVenda = new ItemDaVenda();
	private String message = "";

	private String produtoCodigo = "";
	private Produto produto = new Produto();

	private String unidadeDeMedidaSigla = "";
	private UnidadeDeMedida unidadeDeMedida = new UnidadeDeMedida();

	private Long itemDaVendaId;
	private Venda venda = new Venda();
	private Long vendaId;

	// Atributos para Consulta
	private String itemDaVendaNome = "";

	private Boolean status = true;
	// Listas para Consulta
	private List<ItemDaVenda> itensDaVenda;
	private List<ItemDaVenda> lista = new ArrayList<>();

	@Inject
	private ItemDaVendaRepositorio itemDaVendaRepositorio = new ItemDaVendaRepositorio();

	@Inject
	private RequestContext requestContext;

	@Inject
	private FacesContextUtil facesContext;

	@Inject
	private Logger logger;

	// getter e setters
	public Long getItemDaVendaId() {
		return itemDaVendaId;
	}

	public void setItemDaVendaId(Long itemDaVendaId) {
		this.itemDaVendaId = itemDaVendaId;
	}

	public String getItemDaVendaNome() {
		return itemDaVendaNome;
	}

	public void setItemDaVendaNome(String itemDaVendaNome) {
		this.itemDaVendaNome = itemDaVendaNome;
	}

	public List<ItemDaVenda> getItensDaVenda() {
		return itensDaVenda;
	}

	public List<ItemDaVenda> getLista() {
		return Collections.unmodifiableList(lista);
	}

	// verificar importancia dos m�todos abaixo //verificar se est�o trocados??
	public Integer getTotalItensDaVenda() {
		if(lista==null)
			return 0;
		else
			return lista.size();
	}

	public Integer getTotalItensDaVendaConsulta() {
		if(itensDaVenda == null)
			return 0;
		else
			return itensDaVenda.size();
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getProdutoCodigo() {
		return produtoCodigo;
	}

	public void setProdutoCodigo(String produtoCodigo) {
		this.produtoCodigo = produtoCodigo;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public ItemDaVenda getItemDaVenda() {
		return itemDaVenda;
	}

	public void setItemDaVenda(ItemDaVenda itemDaVenda) {
		this.itemDaVenda = itemDaVenda;
	}

	
	public String getUnidadeDeMedidaSigla() {
		return unidadeDeMedidaSigla;
	}

	public void setUnidadeDeMedidaSigla(String unidadeDeMedidaSigla) {
		this.unidadeDeMedidaSigla = unidadeDeMedidaSigla;
	}

	public UnidadeDeMedida getUnidadeDeMedida() {
		return unidadeDeMedida;
	}

	public void setUnidadeDeMedida(UnidadeDeMedida unidadeDeMedida) {
		this.unidadeDeMedida = unidadeDeMedida;
	}

	public Long getVendaId() {
		return vendaId;
	}

	public void setVendaId(Long vendaId) {
		this.vendaId = vendaId;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}
	
	
	// ----------------- METODOS ----------------------

	@Transacional
	public void listarTabela(ComponentSystemEvent event) {
		if (this.itensDaVenda == null) {
			lista = itemDaVendaRepositorio.listarTodosPorVenda(vendaId);
			itensDaVenda = new ArrayList<>(lista);
		}
		filtrarTabela();
	}
	
	@Transacional
	public void listarTabela() {
		if (this.itensDaVenda == null) {
			lista = itemDaVendaRepositorio.listarTodosPorVenda(vendaId);
			itensDaVenda = new ArrayList<>(lista);
		}
		filtrarTabela();
	}

	public void filtrarTabela() {
		Stream<ItemDaVenda> stream = lista.stream();
		

		stream = stream.filter(c -> (c.getProduto().getNome().toLowerCase().contains(itemDaVendaNome.toLowerCase().trim()))
						| c.getQuantidade().toString().toLowerCase().contains(itemDaVendaNome.toLowerCase().trim())
						| c.getUnidadeDeMedida().getNome().toLowerCase().contains(itemDaVendaNome.toLowerCase().trim()));

		if (status.equals(true))
			stream = stream.filter(c -> (c.getStatus().equals(status)));

		itensDaVenda = stream.collect(Collectors.toList());
	}
	@Logging 
	public String consultarItensDaVenda(){
		System.out.println(vendaId);
		return "itemDaVendaConsultar?faces-redirect=true&vendaId="+vendaId;
	}
	
	public String itemDaVendaConsultar(){
		if(message.equals("")){
			itemDaVenda = new ItemDaVenda();
			
			unidadeDeMedida = new UnidadeDeMedida();
			unidadeDeMedidaSigla = null;
			
			produto = new Produto();
			produtoCodigo = null;
			
			venda = new Venda();
			return consultarItensDaVenda();
		}
		else{
			facesContext.info(message);
			return null;
		}
	}

	// M�todo chamado ao carregar pagina de consulta para popular tabela
	public String listar() {
		listarTabela();
		return null;
	}

	// Limpar tabela da consulta
	public String limpar() {
		limparFiltros();
		this.itensDaVenda = new ArrayList<>(this.lista);
		return null;
	}

	public void limparFiltros() {
		this.itemDaVendaNome = "";
	}

	@Transacional
	public String salvar(ItemDaVenda itemDaVenda) {
		itemDaVenda.setStatus(true);

		BigDecimal aux = venda.getPrecoTotal().add(itemDaVenda.getPrecoTotal());
		venda.setPrecoTotal(aux);
		itemDaVendaRepositorio.atualizar(venda);
		
		itemDaVendaRepositorio.atualizar(itemDaVenda);
		this.itensDaVenda = null;
		this.itemDaVenda = new ItemDaVenda();
		listarTabela();
		return null;
	}
	
	@Logging
	// M�todos que utilizam m�todos do reposit�rio
	@Transacional
	public void salvar() {
		message = "";
		this.itemDaVenda.setStatus(true);

		produto = itemDaVendaRepositorio.getProdutoPorCodigo(produto.getCodigo());

		if (produto == null) {
			message = "produto inexistente, insira um codigo de produto válido";
		}

		itemDaVenda.setProduto(produto);

		

		unidadeDeMedida = itemDaVendaRepositorio.getUnidadeDeMedidaPorSigla(produto.getUnidadeDeMedida().getSigla());

		if (unidadeDeMedida == null) {
			message = "unidadeDeMedida inexistente, insira uma sigla de unidadeDeMedida válido";
		}

		itemDaVenda.setUnidadeDeMedida(unidadeDeMedida);
		
		venda = itemDaVendaRepositorio.getVendaPorId(vendaId);

		if (venda == null) {
			message = "venda inexistente, insira uma sigla de unidadeDeMedida válido";
		}

		itemDaVenda.setVenda(venda);

		if (itemDaVenda.getId() == null) {
			venda.setPrecoTotal(venda.getPrecoTotal().add(itemDaVenda.getPrecoTotal()));
			itemDaVendaRepositorio.atualizar(venda);
			itemDaVendaRepositorio.adicionar(itemDaVenda);
		} else {
			itemDaVendaRepositorio.atualizar(itemDaVenda);
		}
		logger.info(message);
		
				
	}

	@Transacional
	public void recuperarItemDaVendaPorId() {
		itemDaVenda = itemDaVendaRepositorio.buscarPorId(itemDaVendaId);
	}

	@Transacional
	public void recuperarProdutoPorId() {
		produto = itemDaVendaRepositorio.getProdutoPorId(vendaId);
	}
	
	@Transacional
	public void recuperarVendaPorId() {
		venda = itemDaVendaRepositorio.getVendaPorId(vendaId);
	}

	// Remove um ItemDaVenda do banco de dados
	@Transacional
	public String remover(ItemDaVenda itemDaVenda) {
		itemDaVenda.setStatus(false);
		BigDecimal aux = venda.getPrecoTotal().subtract(itemDaVenda.getPrecoTotal());
		venda.setPrecoTotal(aux);
		itemDaVendaRepositorio.atualizar(venda);
		
		itemDaVendaRepositorio.atualizar(itemDaVenda);
		this.itensDaVenda = null;
		this.itemDaVenda = new ItemDaVenda();
		listarTabela();
		return null;
	}

	// Editar um ItemDaVenda
	public String editar(ItemDaVenda itemDaVenda) {
		return "itemDaVenda?itemDaVendaId=" + itemDaVenda.getId();
	}

	// Editar um ItemDaVenda
	public String adicionarItemDaVenda() {
		return "itemDaVenda?vendaId=" + venda.getId();
	}

	public boolean itemDaVendaIdExiste() {
		if (this.itemDaVendaId == null)
			return false;
		return true;
	}

	public void produtoSelecionado(SelectEvent event) {
		produto = (Produto) event.getObject();
		produtoCodigo = produto.getCodigo();
		requestContext.update("formItemDaVenda:produto");
	}

	@Transacional
	public void getProdutoPorCodigo() {
		produto = itemDaVendaRepositorio.getProdutoPorCodigo(produtoCodigo);

	}

	// Método usado para carregar objeto para o dialog
	public void selecionarItemDaVenda(ItemDaVenda itemDaVenda) {
		requestContext.closeDialog(itemDaVenda);
	}
	@Logging
	public void inicializa() {
		if (vendaId != null) {
			recuperarVendaPorId();
		}

		if (itemDaVendaId != null) {
			recuperarItemDaVendaPorId();
			this.produto = itemDaVenda.getProduto();
			produtoCodigo = produto.getCodigo();
			unidadeDeMedida = itemDaVenda.getUnidadeDeMedida();
			unidadeDeMedidaSigla = unidadeDeMedida.getSigla();
			venda = itemDaVenda.getVenda();
			vendaId = venda.getId();
		}

	}
	
	public void calculaPrecoTotal(){
		if(itemDaVenda.getQuantidade()!=null && itemDaVenda.getPrecoUnitario()!=null){
			itemDaVenda.setPrecoTotal(itemDaVenda.getPrecoUnitario().multiply(itemDaVenda.getQuantidade()));
		}
		
	}
	
}
