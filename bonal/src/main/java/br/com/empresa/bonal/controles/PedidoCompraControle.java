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

import br.com.empresa.bonal.entidades.BemDeConsumo;
import br.com.empresa.bonal.entidades.BemPermanente;
import br.com.empresa.bonal.entidades.Conta;
import br.com.empresa.bonal.entidades.Fornecedor;
import br.com.empresa.bonal.entidades.Funcionario;
import br.com.empresa.bonal.entidades.ItemDaCompra;
import br.com.empresa.bonal.entidades.ItemDeProducao;
import br.com.empresa.bonal.entidades.PedidoCompra;
import br.com.empresa.bonal.entidades.UnidadeDeMedida;
import br.com.empresa.bonal.repositorio.PedidoCompraRepositorio;
import br.com.empresa.bonal.util.FacesContextUtil;
import br.com.empresa.bonal.util.enums.EnumStatusPagamento;
import br.com.empresa.bonal.util.enums.EnumTipoPagamento;
import br.com.empresa.bonal.util.logging.Logging;
import br.com.empresa.bonal.util.tx.Transacional;

@Named
@ViewScoped
public class PedidoCompraControle implements Serializable {
	private static final long serialVersionUID = 1L;

	private PedidoCompra pedidoCompra = new PedidoCompra();
	private Fornecedor fornecedor = new Fornecedor();
	private String fornecedorDocumento;

	private Funcionario funcionario = new Funcionario();
	private String funcionarioDocumento;

	private Conta conta = new Conta();

	private ItemDeProducao itemDeProducao = new ItemDeProducao();
	private String itemDeProducaoCodigo;

	private String message = "";

	private ItemDaCompra itemDaCompra = new ItemDaCompra();

	private Long pedidoCompraId;

	// Atributos para Consulta
	private String pedidoCompraNome = "";

	// Listas para Consulta
	private List<PedidoCompra> pedidos;
	private List<PedidoCompra> lista = new ArrayList<>();

	private List<ItemDaCompra> itensDaVenda = new ArrayList<>();

	private Boolean status = true;

	@Inject
	private PedidoCompraRepositorio pedidoCompraRepositorio;

	@Inject
	private RequestContext requestContext;

	@Inject
	private FacesContextUtil facesContext;

	@Inject
	private Logger logger;

	private int contador = 0;

	// Getters and Setters
	public PedidoCompra getPedidoCompra() {
		return pedidoCompra;
	}

	// Adicionado para propriedade de contexto das tabelas do Primefaces
	public void setPedidoCompra(PedidoCompra pedidoCompra) {
		this.pedidoCompra = pedidoCompra;
	}

	public Long getPedidoCompraId() {
		return pedidoCompraId;
	}

	public void setPedidoCompraId(Long pedidoCompraId) {
		this.pedidoCompraId = pedidoCompraId;
	}

	public String getPedidoCompraNome() {
		return pedidoCompraNome;
	}

	public void setPedidoCompraNome(String pedidoCompraNome) {
		this.pedidoCompraNome = pedidoCompraNome;
	}

	public List<PedidoCompra> getPedidos() {
		return pedidos;
	}

	public List<PedidoCompra> getLista() {
		return Collections.unmodifiableList(lista);
	}

	// verificar importancia dos m�todos abaixo //verificar se est�o trocados??
	public Integer getTotalPedidos() {
		return lista.size();
	}

	public Integer getTotalPedidosConsulta() {
		return pedidos.size();
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public List<ItemDaCompra> getItensDaVenda() {
		return itensDaVenda;
	}

	public void setItensDaVenda(List<ItemDaCompra> itensDaVenda) {
		this.itensDaVenda = itensDaVenda;
	}

	public void addItem(ItemDaCompra item) {
		itensDaVenda.add(item);
	}

	public void delItem(ItemDaCompra item) {
		itensDaVenda.remove(item);
	}

	public Integer getTotalDeItens() {
		return itensDaVenda.size();
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public String getFornecedorDocumento() {
		return fornecedorDocumento;
	}

	public void setFornecedorDocumento(String fornecedorDocumento) {
		this.fornecedorDocumento = fornecedorDocumento;
	}

	public ItemDaCompra getItemDaCompra() {
		return itemDaCompra;
	}

	public void setItemDaCompra(ItemDaCompra itemDaCompra) {
		this.itemDaCompra = itemDaCompra;
	}

	public ItemDeProducao getItemDeProducao() {
		return itemDeProducao;
	}

	public void setItemDeProducao(ItemDeProducao itemDeProducao) {
		this.itemDeProducao = itemDeProducao;
	}

	public String getItemDeProducaoCodigo() {
		return itemDeProducaoCodigo;
	}

	public void setItemDeProducaoCodigo(String itemDeProducaoCodigo) {
		this.itemDeProducaoCodigo = itemDeProducaoCodigo;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public String getFuncionarioDocumento() {
		return funcionarioDocumento;
	}

	public void setFuncionarioDocumento(String funcionarioDocumento) {
		this.funcionarioDocumento = funcionarioDocumento;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	// ----- Carrega os Enums em Arrays -----
	public EnumTipoPagamento[] getEnumTipoPagamento() {
		return EnumTipoPagamento.values();
	}

	// ----- Carrega os Enums em Arrays -----
	public EnumStatusPagamento[] getEnumStatusPagamento() {
		return EnumStatusPagamento.values();
	}

	// ----------------- METODOS ----------------------

	@Transacional
	public void listarTabela() {
		if (this.pedidos == null) {
			lista = pedidoCompraRepositorio.listarTodos();
			pedidos = new ArrayList<>(lista);
		}
		filtrarTabela();
	}

	@Transacional
	public void preRenderView(ComponentSystemEvent event) {
		if (this.pedidos == null) {
			lista = pedidoCompraRepositorio.listarTodos();
			pedidos = new ArrayList<>(lista);
		}
		filtrarTabela();
	}

	public void filtrarTabela() {
		Stream<PedidoCompra> stream = lista.stream();

		stream = stream
				.filter(o -> (o.getFornecedor().getNome().toLowerCase().contains(pedidoCompraNome.toLowerCase().trim()))
						| o.getFuncionario().getNome().toLowerCase().contains(pedidoCompraNome.toLowerCase().trim())
						| o.getPrecoTotal().toString().toLowerCase().contains(pedidoCompraNome.toLowerCase().trim()));

		if (status.equals(true))
			stream = stream.filter(o -> (o.getStatus().equals(status)));

		pedidos = stream.collect(Collectors.toList());
	}

	// M�todo chamado ao carregar tabela
	public String listar() {
		listarTabela();
		return null;
	}

	// Limpar tabela da consulta
	public String limpar() {
		limparFiltros();
		this.pedidos = new ArrayList<>(this.lista);
		return null;
	}

	public void limparFiltros() {
		this.pedidoCompraNome = "";
	}

	// Remove um cargo do banco de dados
	@Transacional
	public String salvar(PedidoCompra pedidoCompra) {
		pedidoCompra.setStatus(true);
		pedidoCompraRepositorio.atualizar(pedidoCompra);
		this.pedidos = null;
		listar();
		return null;
	}

	public String itemDeProducaoConsultar() {
		if (message.equals("")) {

			pedidoCompra = new PedidoCompra();
			return "itemDeProducaoConsultar";
		} else {

			facesContext.info(message);
			return null;

		}
	}

	@Transacional
	public void recuperarPedidoCompraPorId() {
		pedidoCompra = pedidoCompraRepositorio.buscarPorId(pedidoCompraId);
	}

	// Remove um cargo do banco de dados
	@Transacional
	public String remover(PedidoCompra pedidoCompra) {
		pedidoCompra.setStatus(false);
		pedidoCompraRepositorio.remover(pedidoCompra);
		this.pedidos = null;
		listar();
		return null;
	}

	@Transacional
	public void getFornecedorPorDocumento() {
		fornecedor = pedidoCompraRepositorio.getFornecedorPorDocumento(fornecedorDocumento);
		pedidoCompra.setFornecedor(fornecedor);
	}

	public void fornecedorSelecionado(SelectEvent event) {
		fornecedor = (Fornecedor) event.getObject();
		fornecedorDocumento = fornecedor.getDocumento();
		pedidoCompra.setFornecedor(fornecedor);
		requestContext.update("formDadosFornecedor:fornecedor");
	}

	public void funcionarioSelecionado(SelectEvent event) {
		funcionario = (Funcionario) event.getObject();
		funcionarioDocumento = funcionario.getDocumento();
		pedidoCompra.setFuncionario(funcionario);
		requestContext.update("formDadosFornecedor:funcionario");
	}

	@Transacional
	public void getFuncionarioPorDocumento() {
		funcionario = pedidoCompraRepositorio.getFuncionarioPorDocumento(funcionarioDocumento);
		pedidoCompra.setFuncionario(funcionario);
	}

	public void itemDeProducaoSelecionado(SelectEvent event) {
		itemDeProducao = (ItemDeProducao) event.getObject();
		itemDeProducaoCodigo = itemDeProducao.getCodigo();
		itemDaCompra = new ItemDaCompra();
		itemDaCompra.setItemDeProducao(itemDeProducao);
		itemDaCompra.setUnidadeDeMedida(itemDeProducao.getUnidadeDeMedida());
		requestContext.update("formDadosItem:itemDeProducao");
	}

	@Transacional
	public void getItemDeProducaoPorCodigo() {
		itemDeProducao = pedidoCompraRepositorio.getItemDeProducaoPorCodigo(itemDeProducaoCodigo);
		itemDaCompra.setItemDeProducao(itemDeProducao);
		itemDaCompra.setUnidadeDeMedida(itemDeProducao.getUnidadeDeMedida());
	}

	@Logging
	public String editar(PedidoCompra pedidoCompra) {
		return "pedidoCompra?pedidoCompraId=" + pedidoCompra.getId();
	}

	public String cancelar() {
		return "index";
	}

	public boolean pedidoCompraIdExiste() {
		if (this.pedidoCompraId == null)
			return false;
		return true;
	}

	// Método usado para carregar objeto para o dialog
	public void selecionarPedidoCompra(PedidoCompra pedidoCompra) {
		requestContext.closeDialog(pedidoCompra);
	}

	public void calculaPrecoTotal() {
		if (itemDaCompra.getQuantidade() != null && itemDaCompra.getPrecoUnitario() != null) {
			itemDaCompra.setPrecoTotal(itemDaCompra.getPrecoUnitario().multiply(itemDaCompra.getQuantidade()));
		}

	}

	public void adicionarItem() {
		if (pedidoCompra.getPrecoTotal() == null) {
			pedidoCompra.setPrecoTotal(new BigDecimal("0"));
		}

		BigDecimal aux = itemDaCompra.getPrecoTotal().add(pedidoCompra.getPrecoTotal());
		pedidoCompra.setPrecoTotal(aux);
		itensDaVenda.add(itemDaCompra);
		itemDaCompra = new ItemDaCompra();
		itemDeProducao = new ItemDeProducao();
		itemDeProducaoCodigo = null;
		requestContext.update("formTabelaItens:itens");
		requestContext.update("formDadosPagamento");

	}

	public void removerItem(ItemDaCompra itemDaCompra) {
		if (pedidoCompra.getPrecoTotal() == null) {
			pedidoCompra.setPrecoTotal(new BigDecimal("0"));
		}

		BigDecimal aux = pedidoCompra.getPrecoTotal().subtract(itemDaCompra.getPrecoTotal());
		pedidoCompra.setPrecoTotal(aux);
		itensDaVenda.remove(itemDaCompra);
		requestContext.update("formTabelaItens:itens");
		requestContext.update("formDadosPagamento");

	}
	@Transacional
	@Logging
	public void salvarTudo() {

		conta.setVencimento(pedidoCompra.getVencimento());
		conta.setPrecoTotal(pedidoCompra.getPrecoTotal());
		conta.setStatus(true);
		conta.setTipo("despesa");
		
		
		Long contaId = pedidoCompraRepositorio.adicionarContaComRetorno(conta);
		
		conta = pedidoCompraRepositorio.buscarContaPorId(contaId);
		
		pedidoCompra.setStatus(true);
		pedidoCompra.setConta(conta);
		
		for(int i = 0; i < itensDaVenda.size(); i++){
			
			ItemDeProducao p = pedidoCompraRepositorio.getItemDeProducaoPorCodigo(itensDaVenda.get(i).getItemDeProducao().getCodigo());
			itensDaVenda.get(i).setItemDeProducao(p);
			
			UnidadeDeMedida u = pedidoCompraRepositorio.getUnidadeDeMedidaPorSigla(p.getUnidadeDeMedida().getSigla());
			itensDaVenda.get(i).setUnidadeDeMedida(u);
			if(p.getSubCategoria().getCategoria().getTipo().toString().equals("BEM_CONSUMO")){
				BemDeConsumo bem = pedidoCompraRepositorio.getBemDeConsumoPorCodigo(p.getCodigo());
				bem.setQuantidade(bem.getQuantidade().add(itensDaVenda.get(i).getQuantidade()));
				pedidoCompraRepositorio.atualizarBemDeConsumo(bem);
			}
			
			if(p.getSubCategoria().getCategoria().getTipo().toString().equals("BEM_PERMANENTE")){
				BemPermanente bem = pedidoCompraRepositorio.getBemPermanentePorCodigo(p.getCodigo());
				bem.setQuantidade(bem.getQuantidade().add(itensDaVenda.get(i).getQuantidade()));
				pedidoCompraRepositorio.atualizarBemPermanente(bem);
			}
			
			
			pedidoCompra.addItem(itensDaVenda.get(i));
		}
		
		
		Funcionario f = pedidoCompraRepositorio.getFuncionarioPorDocumento(funcionarioDocumento);
		pedidoCompra.setFuncionario(f);
		
		Fornecedor c = pedidoCompraRepositorio.getFornecedorPorDocumento(fornecedorDocumento);
		pedidoCompra.setFornecedor(c);

		
		pedidoCompraRepositorio.adicionar(pedidoCompra);
		conta = new Conta();
		funcionario = new Funcionario();
		fornecedor = new Fornecedor();
		pedidoCompra = new PedidoCompra();
		itensDaVenda = new ArrayList<>();
		itemDeProducao = new ItemDeProducao();

	}
	
	
	public String index(){
		return "index?faces-redirect=true";
	}

}
