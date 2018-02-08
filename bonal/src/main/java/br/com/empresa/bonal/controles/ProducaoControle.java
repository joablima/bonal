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
import org.primefaces.event.SelectEvent;

import br.com.empresa.bonal.entidades.Producao;
import br.com.empresa.bonal.entidades.Produto;
import br.com.empresa.bonal.entidades.ItemDeProducao;
import br.com.empresa.bonal.entidades.UnidadeDeMedida;
import br.com.empresa.bonal.repositorio.ProducaoRepositorio;
import br.com.empresa.bonal.util.FacesContextUtil;
import br.com.empresa.bonal.util.tx.Transacional;

@Named
@ViewScoped
public class ProducaoControle implements Serializable {
	private static final long serialVersionUID = 1L;

	private Producao producao = new Producao();
	private String message = "";

	private String produtoCodigo = "";
	private Produto produto = new Produto();

	private Long producaoId;

	// Atributos para Consulta
	private String producaoNome = "";

	private Boolean status = true;
	// Listas para Consulta
	private List<Producao> producoes;
	private List<Producao> lista = new ArrayList<>();

	@Inject
	private ProducaoRepositorio producaoRepositorio;

	@Inject
	private RequestContext requestContext;

	@Inject
	private FacesContextUtil facesContext;

	@Inject
	private Logger logger;

	// Getters and Setters
	public Producao getProducao() {
		return producao;
	}

	// Adicionado para propriedade de contexto das tabelas do Primefaces
	public void setProducao(Producao c) {
		this.producao = c;
	}

	public Long getProducaoId() {
		return producaoId;
	}

	public void setProducaoId(Long producaoId) {
		this.producaoId = producaoId;
	}

	public String getProducaoNome() {
		return producaoNome;
	}

	public void setProducaoNome(String producaoNome) {
		this.producaoNome = producaoNome;
	}

	public List<Producao> getProducoes() {
		return producoes;
	}

	public List<Producao> getLista() {
		return Collections.unmodifiableList(lista);
	}

	// verificar importancia dos m�todos abaixo //verificar se est�o trocados??
	public Integer getTotalProducoes() {
		return lista.size();
	}

	public Integer getTotalProducoesConsulta() {
		return producoes.size();
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

	// ----------------- METODOS ----------------------
	@PostConstruct
	@Transacional
	public void listarTabela() {
		if (this.producoes == null) {
			lista = producaoRepositorio.listarTodos();
			producoes = new ArrayList<>(lista);
		}
		filtrarTabela();
	}

	public void filtrarTabela() {
		Stream<Producao> stream = lista.stream();

		stream = stream.filter(c -> (c.getProduto().getNome().toLowerCase().contains(producaoNome.toLowerCase().trim()))
				| c.getQuantidade().toString().toLowerCase().contains(producaoNome.toLowerCase().trim()));

		if (status.equals(true))
			stream = stream.filter(c -> (c.getStatus().equals(status)));

		producoes = stream.collect(Collectors.toList());
	}

	// M�todo chamado ao carregar pagina de consulta para popular tabela
	public String listar() {
		listarTabela();
		return null;
	}

	// Limpar tabela da consulta
	public String limpar() {
		limparFiltros();
		this.producoes = new ArrayList<>(this.lista);
		return null;
	}

	public void limparFiltros() {
		this.producaoNome = "";
	}
	@Transacional
	public String salvar(Producao producao){
		producao.setStatus(true);
		producaoRepositorio.atualizar(producao);
		this.producoes = null;
		this.producao = new Producao();
		listarTabela();
		return null;
	}
	
	public String producaoConsultar(){
		
		if(message.equals("")){
			producao = new Producao();
			produto = new Produto();
			produtoCodigo = null;
			return "producaoConsultar?faces-redirect=true";
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
		this.producao.setStatus(true);
		
		produto = producaoRepositorio.getProdutoPorCodigo(produto.getCodigo());

		if (produto == null) {
			message = "Produto inexistente, insira um codigo de produto válido";
		}
				
		producao.setProduto(produto);
		
	
		if (producao.getId() == null) {
			
			produto.setQuantidade(produto.getQuantidade().add(producao.getQuantidade()));
			
			producaoRepositorio.atualizarProduto(produto);
			producaoRepositorio.adicionar(producao);
		} else {
			producaoRepositorio.atualizar(producao);
		}
		logger.info(message);

	}

	@Transacional
	public void recuperarProducaoPorId() {
		producao = producaoRepositorio.buscarPorId(producaoId);
	}

	// Remove um SubProduto do banco de dados
	@Transacional
	public String remover(Producao producao) {
		producao.setStatus(false);
		producaoRepositorio.atualizar(producao);
		this.producoes = null;
		this.producao = new Producao();
		listarTabela();
		return null;
	}

	// Editar um SubProduto
	public String editar(Producao producao) {
		return "producao?faces-redirect=true&producaoId=" + producao.getId();
	}

	public boolean producaoIdExiste() {
		if (this.producaoId == null)
			return false;
		return true;
	}

	public void produtoSelecionado(SelectEvent event) {
		produto = (Produto) event.getObject();
		produtoCodigo = produto.getCodigo();
		requestContext.update("formProducao:produto");
	}

	@Transacional
	public void getProdutoPorCodigo() {
		produto = producaoRepositorio.getProdutoPorCodigo(produtoCodigo);
	}

	

	
	// Método usado para carregar objeto para o dialog
	public void selecionarProducao(Producao producao) {
		requestContext.closeDialog(producao);
	}

	public void inicializa() {
		recuperarProducaoPorId();
		produtoCodigo = producao.getProduto().getCodigo();
		getProdutoPorCodigo();

	}

	

}
