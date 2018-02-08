package br.com.empresa.bonal.controles;

import java.io.Serializable;
import java.math.BigDecimal;
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
import org.primefaces.event.SelectEvent;

import br.com.empresa.bonal.entidades.Produto;
import br.com.empresa.bonal.entidades.UnidadeDeMedida;
import br.com.empresa.bonal.repositorio.ProdutoRepositorio;
import br.com.empresa.bonal.util.FacesContextUtil;
import br.com.empresa.bonal.util.tx.Transacional;

@Named
@ViewScoped
public class ProdutoControle implements Serializable {
	private static final long serialVersionUID = 1L;

	private Produto produto = new Produto();
	
	private String message = "";
	
	private String unidadeDeMedidaSigla = "";
	private UnidadeDeMedida unidadeDeMedida = new UnidadeDeMedida();

	private Long produtoId;

	// Atributos para Consulta
	private String produtoNome = "";

	private Boolean status = true;
	// Listas para Consulta
	private List<Produto> produtos;
	private List<Produto> lista = new ArrayList<>();

	@Inject
	private ProdutoRepositorio produtoRepositorio;

	@Inject
	private RequestContext requestContext;

	@Inject
	private FacesContextUtil facesContext;

	@Inject
	private Logger logger;

	// Getters and Setters
	public Produto getProduto() {
		return produto;
	}

	// Adicionado para propriedade de contexto das tabelas do Primefaces
	public void setProduto(Produto c) {
		this.produto = c;
	}

	public Long getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
	}

	public String getProdutoNome() {
		return produtoNome;
	}

	public void setProdutoNome(String produtoNome) {
		this.produtoNome = produtoNome;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public List<Produto> getLista() {
		return Collections.unmodifiableList(lista);
	}

	// verificar importancia dos m�todos abaixo //verificar se est�o trocados??
	public Integer getTotalProdutos() {
		return lista.size();
	}

	public Integer getTotalProdutosConsulta() {
		return produtos.size();
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
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

	// ----------------- METODOS ----------------------
	@PostConstruct
	@Transacional
	public void listarTabela() {
		if (this.produtos == null) {
			lista = produtoRepositorio.listarTodos();
			produtos = new ArrayList<>(lista);
		}
		filtrarTabela();
	}

	public void filtrarTabela() {
		Stream<Produto> stream = lista.stream();

		stream = stream.filter(c -> (c.getNome().toLowerCase().contains(produtoNome.toLowerCase().trim()))
				| (c.getCodigo().toLowerCase().contains(produtoNome.toLowerCase().trim()))
				| c.getDescricao().toLowerCase().contains(produtoNome.toLowerCase().trim()));

		if (status.equals(true))
			stream = stream.filter(c -> (c.getStatus().equals(status)));

		produtos = stream.collect(Collectors.toList());
	}

	// M�todo chamado ao carregar pagina de consulta para popular tabela
	public String listar() {
		listarTabela();
		return null;
	}

	// Limpar tabela da consulta
	public String limpar() {
		limparFiltros();
		this.produtos = new ArrayList<>(this.lista);
		return null;
	}

	public void limparFiltros() {
		this.produtoNome = "";
	}
	@Transacional
	public String salvar(Produto produto){
		produto.setStatus(true);
		produtoRepositorio.atualizar(produto);
		this.produtos = null;
		this.produto = new Produto();
		listarTabela();
		return null;
	}
	
	public String produtoConsultar(){
		if(message.equals("")){
			produto = new Produto();
			unidadeDeMedida = new UnidadeDeMedida();
			unidadeDeMedidaSigla = null;
			return "produtoConsultar?faces-redirect=true";
		}
		else{
			
			facesContext.info(message);
			return null;
		}
	}

	// M�todos que utilizam m�todos do reposit�rio
	@Transacional
	public void salvar() {
		message = "";
		this.produto.setStatus(true);
		
		unidadeDeMedida = produtoRepositorio.getUnidadeDeMedidaPorSigla(produto.getUnidadeDeMedida().getSigla());

		

		if (unidadeDeMedida == null) {
			message = "Unidade de medida inexistente, insira um codigo válido";
		}
		
		produto.setUnidadeDeMedida(unidadeDeMedida);
		
		Produto existe = produtoRepositorio.getProdutoPorCodigo(produto.getCodigo());
		
		if (existe != null && (existe.getId()!=produto.getId())) {
			message = "Codigo duplicado";
		}

		if (produto.getId() == null) {
			produto.setQuantidade(new BigDecimal("0"));
			produtoRepositorio.adicionar(produto);
		} else {
			produtoRepositorio.atualizar(produto);
		}
		
		logger.info(message);
		
	}

	@Transacional
	public void recuperarProdutoPorId() {
		produto = produtoRepositorio.buscarPorId(produtoId);
	}

	// Remove um SubCategoria do banco de dados
	@Transacional
	public String remover(Produto produto) {
		produto.setStatus(false);
		produtoRepositorio.atualizar(produto);
		this.produtos = null;
		this.produto = new Produto();
		listarTabela();
		return null;
	}

	// Editar um SubCategoria
	public String editar(Produto produto) {
		return "produto?faces-redirect=true&produtoId=" + produto.getId();
	}

	public boolean produtoIdExiste() {
		if (this.produtoId == null)
			return false;
		return true;
	}

	
	@Transacional
	public void getUnidadeDeMedidaPorSigla() {
		unidadeDeMedida = produtoRepositorio.getUnidadeDeMedidaPorSigla(unidadeDeMedidaSigla);
		produto.setUnidadeDeMedida(unidadeDeMedida);
	}

	public String consultarCoeficientesTecnicos(){
		return "coeficienteTecnicoConsultar?faces-redirect=true&produtoId="+produto.getId();
	}
	
	public String consultarCoeficientesTecnicos(Produto p){
		return "coeficienteTecnicoConsultar?faces-redirect=true&produtoId="+p.getId();
	}
	

	public void unidadeDeMedidaSelecionada(SelectEvent event) {
		unidadeDeMedida = (UnidadeDeMedida) event.getObject();
		unidadeDeMedidaSigla = unidadeDeMedida.getSigla();
		produto.setUnidadeDeMedida(unidadeDeMedida);
		requestContext.update("formProduto:unidadeDeMedida");
	}

	// Método usado para carregar objeto para o dialog
	public void selecionarProduto(Produto produto) {
		requestContext.closeDialog(produto);
	}
	
	public void calculaMargemDeLucro(){
		if(produto.getCusto()!=null && produto.getVenda()!=null){
			produto.setMargemDeLucro();
		}
	}
	
	public void inicializa() {
		recuperarProdutoPorId();
		
		unidadeDeMedidaSigla = produto.getUnidadeDeMedida().getSigla();
		getUnidadeDeMedidaPorSigla();

	}
}
