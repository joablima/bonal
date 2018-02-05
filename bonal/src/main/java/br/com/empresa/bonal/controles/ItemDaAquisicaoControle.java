/*package br.com.empresa.bonal.controles;

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

import br.com.empresa.bonal.entidades.Aquisicao;
import br.com.empresa.bonal.entidades.Fornecedor;
import br.com.empresa.bonal.entidades.Funcionario;
import br.com.empresa.bonal.entidades.ItemDaAquisicao;
import br.com.empresa.bonal.entidades.ItemDeProducao;
import br.com.empresa.bonal.entidades.UnidadeDeMedida;
import br.com.empresa.bonal.repositorio.ItemDaAquisicaoRepositorio;
import br.com.empresa.bonal.util.FacesContextUtil;
import br.com.empresa.bonal.util.logging.Logging;
import br.com.empresa.bonal.util.tx.Transacional;

@Named
@ViewScoped
public class ItemDaAquisicaoControle implements Serializable {
	private static final long serialVersionUID = 1L;

	private ItemDaAquisicao itemDaAquisicao = new ItemDaAquisicao();
	
	public String message = "";

	private String itemDeProducaoCodigo = "";
	private ItemDeProducao itemDeProducao = new ItemDeProducao();

	private String unidadeDeMedidaSigla = "";
	private UnidadeDeMedida unidadeDeMedida = new UnidadeDeMedida();

	private Long itemDaAquisicaoId;
	private Aquisicao aquisicao = new Aquisicao();
	private Long aquisicaoId;

	// Atributos para Consulta
	private String itemDaAquisicaoNome = "";

	private Boolean status = true;
	// Listas para Consulta
	private List<ItemDaAquisicao> itensDaAquisicao;
	private List<ItemDaAquisicao> lista = new ArrayList<>();

	@Inject
	private ItemDaAquisicaoRepositorio itemDaAquisicaoRepositorio = new ItemDaAquisicaoRepositorio();

	@Inject
	private RequestContext requestContext;

	@Inject
	private FacesContextUtil facesContext;

	@Inject
	private Logger logger;

	// getter e setters
	public Long getItemDaAquisicaoId() {
		return itemDaAquisicaoId;
	}

	public void setItemDaAquisicaoId(Long itemDaAquisicaoId) {
		this.itemDaAquisicaoId = itemDaAquisicaoId;
	}

	public String getItemDaAquisicaoNome() {
		return itemDaAquisicaoNome;
	}

	public void setItemDaAquisicaoNome(String itemDaAquisicaoNome) {
		this.itemDaAquisicaoNome = itemDaAquisicaoNome;
	}

	public List<ItemDaAquisicao> getItensDaAquisicao() {
		return itensDaAquisicao;
	}

	public List<ItemDaAquisicao> getLista() {
		return Collections.unmodifiableList(lista);
	}

	// verificar importancia dos m�todos abaixo //verificar se est�o trocados??
	public Integer getTotalItensDaAquisicao() {
		if(lista==null)
			return 0;
		else
			return lista.size();
	}

	public Integer getTotalItensDaAquisicaoConsulta() {
		if(itensDaAquisicao == null)
			return 0;
		else
			return itensDaAquisicao.size();
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getItemDeProducaoCodigo() {
		return itemDeProducaoCodigo;
	}

	public void setItemDeProducaoCodigo(String itemDeProducaoCodigo) {
		this.itemDeProducaoCodigo = itemDeProducaoCodigo;
	}

	public ItemDeProducao getItemDeProducao() {
		return itemDeProducao;
	}

	public void setItemDeProducao(ItemDeProducao itemDeProducao) {
		this.itemDeProducao = itemDeProducao;
	}

	public ItemDaAquisicao getItemDaAquisicao() {
		return itemDaAquisicao;
	}

	public void setItemDaAquisicao(ItemDaAquisicao itemDaAquisicao) {
		this.itemDaAquisicao = itemDaAquisicao;
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

	public Long getAquisicaoId() {
		return aquisicaoId;
	}

	public void setAquisicaoId(Long aquisicaoId) {
		this.aquisicaoId = aquisicaoId;
	}

	public Aquisicao getAquisicao() {
		return aquisicao;
	}

	public void setAquisicao(Aquisicao aquisicao) {
		this.aquisicao = aquisicao;
	}
	
	
	// ----------------- METODOS ----------------------

	@Transacional
	public void listarTabela(ComponentSystemEvent event) {
		if (this.itensDaAquisicao == null) {
			lista = itemDaAquisicaoRepositorio.listarTodosPorAquisicao(aquisicaoId);
			itensDaAquisicao = new ArrayList<>(lista);
		}
		filtrarTabela();
	}
	
	@Transacional
	public void listarTabela() {
		if (this.itensDaAquisicao == null) {
			lista = itemDaAquisicaoRepositorio.listarTodosPorAquisicao(aquisicaoId);
			itensDaAquisicao = new ArrayList<>(lista);
		}
		filtrarTabela();
	}

	public void filtrarTabela() {
		Stream<ItemDaAquisicao> stream = lista.stream();
		

		stream = stream.filter(c -> (c.getItemDeProducao().getNome().toLowerCase().contains(itemDaAquisicaoNome.toLowerCase().trim()))
						| c.getQuantidade().toString().toLowerCase().contains(itemDaAquisicaoNome.toLowerCase().trim())
						| c.getUnidadeDeMedida().getNome().toLowerCase().contains(itemDaAquisicaoNome.toLowerCase().trim()));

		if (status.equals(true))
			stream = stream.filter(c -> (c.getStatus().equals(status)));

		itensDaAquisicao = stream.collect(Collectors.toList());
	}
	
	public String consultarItensDaAquisicao(){
		return "itemDaAquisicaoConsultar?aquisicaoId="+aquisicaoId;
	}

	// M�todo chamado ao carregar pagina de consulta para popular tabela
	public String listar() {
		listarTabela();
		return null;
	}

	// Limpar tabela da consulta
	public String limpar() {
		limparFiltros();
		this.itensDaAquisicao = new ArrayList<>(this.lista);
		return null;
	}

	public void limparFiltros() {
		this.itemDaAquisicaoNome = "";
	}

	@Transacional
	public String salvar(ItemDaAquisicao itemDaAquisicao) {
		itemDaAquisicao.setStatus(true);
		
		itemDaAquisicaoRepositorio.atualizar(itemDaAquisicao);
		this.itensDaAquisicao = null;
		this.itemDaAquisicao = new ItemDaAquisicao();
		listarTabela();
		return null;
	}
	
	@Logging
	// M�todos que utilizam m�todos do reposit�rio
	@Transacional
	public String salvar() {
		this.itemDaAquisicao.setStatus(true);
		
		getItemDeProducaoPorCodigo();
		
		if(itemDeProducao == null){
			message = "Item de producao inexistente";
		}
		
		getIUnidadeDeMedidaPorSigla();
		
		recuperarAquisicaoPorId();

		if (itemDaAquisicao.getId() == null) {
			
			BigDecimal aux = aquisicao.getPrecoTotal().add(itemDaAquisicao.getPrecoTotal());
			aquisicao.setPrecoTotal(aux);
			Funcionario f = itemDaAquisicaoRepositorio.getFuncionarioPorDocumento(aquisicao.getFuncionario().getDocumento());
			aquisicao.setFuncionario(f);
			Fornecedor fo = itemDaAquisicaoRepositorio.getFornecedorPorDocumento(aquisicao.getFornecedor().getDocumento());
			aquisicao.setFornecedor(fo);
			itemDaAquisicaoRepositorio.atualizar(aquisicao);
			itemDaAquisicaoRepositorio.adicionar(itemDaAquisicao);
		} else {
			
			itemDaAquisicaoRepositorio.atualizar(aquisicao);
			
			itemDaAquisicaoRepositorio.atualizar(itemDaAquisicao);
		}
		itemDaAquisicao = new ItemDaAquisicao();
		
		unidadeDeMedida = new UnidadeDeMedida();
		unidadeDeMedidaSigla = null;
		
		itemDeProducao = new ItemDeProducao();
		itemDeProducaoCodigo = null;
		
		aquisicao = new Aquisicao();
		aquisicaoId =null;
				
		return null;
	}

	@Transacional
	public void recuperarItemDaAquisicaoPorId() {
		itemDaAquisicao = itemDaAquisicaoRepositorio.getItemDaAquisicaoPorId(itemDaAquisicaoId);
	}

	@Transacional
	public void recuperarAquisicaoPorId() {
		aquisicao = itemDaAquisicaoRepositorio.getAquisicaoPorId(aquisicaoId);
		itemDaAquisicao.setAquisicao(aquisicao);
	}

	// Remove um ItemDaAquisicao do banco de dados
	@Transacional
	public String remover(ItemDaAquisicao itemDaAquisicao) {
		itemDaAquisicao.setStatus(false);
		itemDaAquisicaoRepositorio.atualizar(itemDaAquisicao);
		this.itensDaAquisicao = null;
		this.itemDaAquisicao = new ItemDaAquisicao();
		listarTabela();
		return null;
	}

	// Editar um ItemDaAquisicao
	public String editar(ItemDaAquisicao itemDaAquisicao) {
		return "itemDaAquisicao?itemDaAquisicaoId=" + itemDaAquisicao.getId();
	}

	// Editar um ItemDaAquisicao
	public String adicionarItemDaAquisicao() {
		return "itemDaAquisicao?aquisicaoId=" + aquisicao.getId();
	}

	public boolean itemDaAquisicaoIdExiste() {
		if (this.itemDaAquisicaoId == null)
			return false;
		return true;
	}

	public void itemDeProducaoSelecionado(SelectEvent event) {
		itemDeProducao = (ItemDeProducao) event.getObject();
		
		itemDeProducaoCodigo = itemDeProducao.getCodigo();
		
		unidadeDeMedidaSigla = itemDeProducao.getUnidadeDeMedida().getSigla();
		
		requestContext.update("formItemDaAquisicao:itemDeProducao");
	}

	@Transacional
	public void getItemDeProducaoPorCodigo() {
		itemDeProducao = itemDaAquisicaoRepositorio.getItemDeProducaoPorCodigo(itemDeProducaoCodigo);
		itemDaAquisicao.setItemDeProducao(itemDeProducao);

	}
	
	@Transacional
	public void getIUnidadeDeMedidaPorSigla() {
		unidadeDeMedida = itemDaAquisicaoRepositorio.getUnidadeDeMedidaPorSigla(unidadeDeMedidaSigla);
		itemDaAquisicao.setUnidadeDeMedida(unidadeDeMedida);
	}

	// Método usado para carregar objeto para o dialog
	public void selecionarItemDaAquisicao(ItemDaAquisicao itemDaAquisicao) {
		requestContext.closeDialog(itemDaAquisicao);
	}
	@Logging
	public void inicializa() {
		if (aquisicaoId != null) {
			recuperarAquisicaoPorId();
		}

		if (itemDaAquisicaoId != null) {
			recuperarItemDaAquisicaoPorId();
			this.itemDeProducao = itemDaAquisicao.getItemDeProducao();
			itemDeProducaoCodigo = itemDeProducao.getCodigo();
			unidadeDeMedida = itemDaAquisicao.getUnidadeDeMedida();
			unidadeDeMedidaSigla = unidadeDeMedida.getSigla();
			aquisicao = itemDaAquisicao.getAquisicao();
			aquisicaoId = aquisicao.getId();
		}

	}
	
	public void calculaPrecoTotal(){
		itemDaAquisicao.setPrecoTotal(itemDaAquisicao.getPrecoUnitario().multiply(itemDaAquisicao.getQuantidade()));
	}
	
}
*/