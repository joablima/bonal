package br.com.empresa.bonal.controles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.empresa.bonal.entidades.CoeficienteTecnico;
import br.com.empresa.bonal.entidades.ItemDeProducao;
import br.com.empresa.bonal.entidades.Produto;
import br.com.empresa.bonal.entidades.UnidadeDeMedida;
import br.com.empresa.bonal.repositorio.CoeficienteTecnicoRepositorio;
import br.com.empresa.bonal.util.FacesContextUtil;
import br.com.empresa.bonal.util.logging.Logging;
import br.com.empresa.bonal.util.tx.Transacional;

@Named
@ViewScoped
public class CoeficienteTecnicoControle implements Serializable {
	private static final long serialVersionUID = 1L;

	private CoeficienteTecnico coeficienteTecnico = new CoeficienteTecnico();

	private String produtoCodigo = "";
	private Produto produto = new Produto();

	private String itemDeProducaoCodigo = "";
	private ItemDeProducao itemDeProducao = new ItemDeProducao();

	private String unidadeDeMedidaSigla = "";
	private UnidadeDeMedida unidadeDeMedida = new UnidadeDeMedida();

	private Long coeficienteTecnicoId;
	private Long produtoId;

	// Atributos para Consulta
	private String coeficienteTecnicoNome = "";

	private Boolean status = true;
	// Listas para Consulta
	private List<CoeficienteTecnico> coeficientesTecnicos;
	private List<CoeficienteTecnico> lista = new ArrayList<>();

	@Inject
	private CoeficienteTecnicoRepositorio coeficienteTecnicoRepositorio = new CoeficienteTecnicoRepositorio();

	@Inject
	private RequestContext requestContext;

	@Inject
	private FacesContextUtil facesContext;

	@Inject
	private Logger logger;

	// getter e setters
	public Long getCoeficienteTecnicoId() {
		return coeficienteTecnicoId;
	}

	public void setCoeficienteTecnicoId(Long coeficienteTecnicoId) {
		this.coeficienteTecnicoId = coeficienteTecnicoId;
	}

	public String getCoeficienteTecnicoNome() {
		return coeficienteTecnicoNome;
	}

	public void setCoeficienteTecnicoNome(String coeficienteTecnicoNome) {
		this.coeficienteTecnicoNome = coeficienteTecnicoNome;
	}

	public List<CoeficienteTecnico> getCoeficientesTecnicos() {
		return coeficientesTecnicos;
	}

	public List<CoeficienteTecnico> getLista() {
		return Collections.unmodifiableList(lista);
	}

	// verificar importancia dos m�todos abaixo //verificar se est�o trocados??
	public Integer getTotalCoeficientesTecnicos() {
		return lista.size();
	}

	public Integer getTotalCoeficientesTecnicosConsulta() {
		return coeficientesTecnicos.size();
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

	public CoeficienteTecnico getCoeficienteTecnico() {
		return coeficienteTecnico;
	}

	public void setCoeficienteTecnico(CoeficienteTecnico coeficienteTecnico) {
		this.coeficienteTecnico = coeficienteTecnico;
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

	public Long getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
	}

	// ----------------- METODOS ----------------------

	@Transacional
	public void listarTabela() {
		if (this.coeficientesTecnicos == null) {
			lista = coeficienteTecnicoRepositorio.listarTodosPorProduto(produtoId);
			coeficientesTecnicos = new ArrayList<>(lista);
		}
		filtrarTabela();
	}
	
	@Transacional
	public void preRenderView(ComponentSystemEvent event){
        if (!FacesContext.getCurrentInstance().isPostback()){
        	if (this.coeficientesTecnicos == null) {
    			lista = coeficienteTecnicoRepositorio.listarTodos();
    			coeficientesTecnicos = new ArrayList<>(lista);
    		}
    		filtrarTabela();
        }
    }

	public void filtrarTabela() {
		Stream<CoeficienteTecnico> stream = lista.stream();
		

		stream = stream.filter(c -> (c.getItemDeProducao().getNome().toLowerCase().contains(coeficienteTecnicoNome.toLowerCase().trim()))
						| c.getQuantidade().toString().toLowerCase().contains(coeficienteTecnicoNome.toLowerCase().trim()));

		if (status.equals(true))
			stream = stream.filter(c -> (c.getStatus().equals(status)));

		coeficientesTecnicos = stream.collect(Collectors.toList());
	}
	
	public String consultarCoeficientesTecnicos(){
		return "coeficienteTecnicoConsultar?produtoId="+produto.getId();
	}

	// M�todo chamado ao carregar pagina de consulta para popular tabela
	public String listar() {
		listarTabela();
		return null;
	}

	// Limpar tabela da consulta
	public String limpar() {
		limparFiltros();
		this.coeficientesTecnicos = new ArrayList<>(this.lista);
		return null;
	}

	public void limparFiltros() {
		this.coeficienteTecnicoNome = "";
	}

	@Transacional
	public String salvar(CoeficienteTecnico coeficienteTecnico) {
		coeficienteTecnico.setStatus(true);
		coeficienteTecnicoRepositorio.atualizar(coeficienteTecnico);
		this.coeficientesTecnicos = null;
		this.coeficienteTecnico = new CoeficienteTecnico();
		listarTabela();
		return null;
	}
	
	@Logging
	// M�todos que utilizam m�todos do reposit�rio
	@Transacional
	public String salvar() {
		String message = "";
		this.coeficienteTecnico.setStatus(true);

		produto = coeficienteTecnicoRepositorio.getProdutoPorCodigo(produto.getCodigo());

		if (produto == null) {
			facesContext.warn("produto inexistente, insira um codigo de produto válido");
			return null;
		}

		coeficienteTecnico.setProduto(produto);

		itemDeProducao = coeficienteTecnicoRepositorio.getItemDeProducaoPorCodigo(itemDeProducao.getCodigo());

		if (itemDeProducao == null) {
			facesContext.warn("itemDeProducao inexistente, insira um codigo de produto válido");
			return null;
		}

		coeficienteTecnico.setItemDeProducao(itemDeProducao);
		

		unidadeDeMedida = coeficienteTecnicoRepositorio.getUnidadeDeMedidaPorSigla(itemDeProducao.getUnidadeDeMedida().getSigla());

		if (unidadeDeMedida == null) {
			facesContext.warn("unidadeDeMedida inexistente, insira uma sigla de unidadeDeMedida válido");
			return null;
		}

		coeficienteTecnico.setUnidadeDeMedida(unidadeDeMedida);

		if (coeficienteTecnico.getId() == null) {
			coeficienteTecnicoRepositorio.adicionar(coeficienteTecnico);
			message += "CoeficienteTecnico Cadastrada com Sucesso.";
		} else {
			coeficienteTecnicoRepositorio.atualizar(coeficienteTecnico);
			message += "CoeficienteTecnico Atualizada com Sucesso.";
		}
		facesContext.info(message);
		logger.info(message);
		coeficienteTecnico = new CoeficienteTecnico();
		
		unidadeDeMedida = new UnidadeDeMedida();
		unidadeDeMedidaSigla = null;
		
		itemDeProducao = new ItemDeProducao();
		itemDeProducaoCodigo = null;
				
		return null;
	}

	@Transacional
	public void recuperarCoeficienteTecnicoPorId() {
		coeficienteTecnico = coeficienteTecnicoRepositorio.buscarPorId(coeficienteTecnicoId);
	}

	@Transacional
	public void recuperarProdutoPorId() {
		produto = coeficienteTecnicoRepositorio.getProdutoPorId(produtoId);
	}

	// Remove um CoeficienteTecnico do banco de dados
	@Transacional
	public String remover(CoeficienteTecnico coeficienteTecnico) {
		coeficienteTecnico.setStatus(false);
		coeficienteTecnicoRepositorio.atualizar(coeficienteTecnico);
		this.coeficientesTecnicos = null;
		this.coeficienteTecnico = new CoeficienteTecnico();
		listarTabela();
		return null;
	}

	// Editar um CoeficienteTecnico
	public String editar(CoeficienteTecnico coeficienteTecnico) {
		return "coeficienteTecnico?coeficienteTecnicoId=" + coeficienteTecnico.getId();
	}

	// Editar um CoeficienteTecnico
	public String adicionarCoeficienteTecnico() {
		return "coeficienteTecnico?produtoId=" + produto.getId();
	}

	public boolean coeficienteTecnicoIdExiste() {
		if (this.coeficienteTecnicoId == null)
			return false;
		return true;
	}

	public void produtoSelecionado(SelectEvent event) {
		produto = (Produto) event.getObject();
		produtoCodigo = produto.getCodigo();
		requestContext.update("formCoeficienteTecnico:produto");
	}

	@Transacional
	public void getProdutoPorCodigo() {
		produto = coeficienteTecnicoRepositorio.getProdutoPorCodigo(produtoCodigo);

	}

	@Transacional
	public void itemDeProducaoSelecionado(SelectEvent event) {
		itemDeProducao = (ItemDeProducao) event.getObject();
		itemDeProducaoCodigo = itemDeProducao.getCodigo();
		unidadeDeMedida = itemDeProducao.getUnidadeDeMedida();
		unidadeDeMedidaSigla = unidadeDeMedida.getSigla();
		requestContext.update("formCoeficienteTecnico:itemDeProducao");
	}

	@Transacional
	public void getItemDeProducaoPorCodigo() {
		itemDeProducao = coeficienteTecnicoRepositorio.getItemDeProducaoPorCodigo(itemDeProducaoCodigo);
		unidadeDeMedida = itemDeProducao.getUnidadeDeMedida();
		unidadeDeMedidaSigla = unidadeDeMedida.getSigla();
	}

	// Método usado para carregar objeto para o dialog
	public void selecionarCoeficienteTecnico(CoeficienteTecnico coeficienteTecnico) {
		requestContext.closeDialog(coeficienteTecnico);
	}
	@Logging
	public void inicializa() {
		if (produtoId != null) {
			recuperarProdutoPorId();
			produtoCodigo = produto.getCodigo();
		}

		if (coeficienteTecnicoId != null) {
			recuperarCoeficienteTecnicoPorId();
			this.produto = coeficienteTecnico.getProduto();
			produtoCodigo = produto.getCodigo();
			unidadeDeMedida = coeficienteTecnico.getUnidadeDeMedida();
			unidadeDeMedidaSigla = unidadeDeMedida.getSigla();
			itemDeProducao = coeficienteTecnico.getItemDeProducao();
			itemDeProducaoCodigo = itemDeProducao.getCodigo();
		}

	}
	
	 
	
}
