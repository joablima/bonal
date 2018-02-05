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

import br.com.empresa.bonal.entidades.Aquisicao;
import br.com.empresa.bonal.entidades.Fornecedor;
import br.com.empresa.bonal.entidades.Funcionario;
import br.com.empresa.bonal.repositorio.AquisicaoRepositorio;
import br.com.empresa.bonal.util.FacesContextUtil;
import br.com.empresa.bonal.util.logging.Logging;
import br.com.empresa.bonal.util.tx.Transacional;

@Named
@ViewScoped
public class AquisicaoControle implements Serializable {
	private static final long serialVersionUID = 1L;

	private Aquisicao aquisicao = new Aquisicao();
	
	private String message = "";

	private String fornecedorDocumento = "";
	private Fornecedor fornecedor = new Fornecedor();

	private String funcionarioDocumento = "";
	private Funcionario funcionario = new Funcionario();

	private Long aquisicaoId;

	// Atributos para Consulta
	private String aquisicaoNome = "";

	private Boolean status = true;
	// Listas para Consulta
	private List<Aquisicao> aquisicoes;
	private List<Aquisicao> lista = new ArrayList<>();

	@Inject
	private AquisicaoRepositorio aquisicaoRepositorio;

	@Inject
	private RequestContext requestContext;

	@Inject
	private FacesContextUtil facesContext;

	@Inject
	private Logger logger;

	// Getters and Setters
	public Aquisicao getAquisicao() {
		return aquisicao;
	}

	// Adicionado para propriedade de contexto das tabelas do Primefaces
	public void setAquisicao(Aquisicao c) {
		this.aquisicao = c;
	}

	public Long getAquisicaoId() {
		return aquisicaoId;
	}

	public void setAquisicaoId(Long aquisicaoId) {
		this.aquisicaoId = aquisicaoId;
	}

	public String getAquisicaoNome() {
		return aquisicaoNome;
	}

	public void setAquisicaoNome(String aquisicaoNome) {
		this.aquisicaoNome = aquisicaoNome;
	}

	public List<Aquisicao> getAquisicoes() {
		return aquisicoes;
	}

	public List<Aquisicao> getLista() {
		return Collections.unmodifiableList(lista);
	}

	// verificar importancia dos m�todos abaixo //verificar se est�o trocados??
	public Integer getTotalAquisicoes() {
		return lista.size();
	}

	public Integer getTotalAquisicoesConsulta() {
		return aquisicoes.size();
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getFornecedorDocumento() {
		return fornecedorDocumento;
	}

	public void setFornecedorDocumento(String fornecedorDocumento) {
		this.fornecedorDocumento = fornecedorDocumento;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public String getFuncionarioDocumento() {
		return funcionarioDocumento;
	}

	public void setFuncionarioDocumento(String funcionarioDocumento) {
		this.funcionarioDocumento = funcionarioDocumento;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	// ----------------- METODOS ----------------------
	@PostConstruct
	@Transacional
	public void listarTabela() {
		if (this.aquisicoes == null) {
			lista = aquisicaoRepositorio.listarTodos();
			aquisicoes = new ArrayList<>(lista);
		}
		filtrarTabela();
	}

	public void filtrarTabela() {
		Stream<Aquisicao> stream = lista.stream();

		stream = stream
				.filter(c -> (c.getFornecedor().getNome().toLowerCase().contains(aquisicaoNome.toLowerCase().trim()))
						| (c.getFuncionario().getNome().toLowerCase().contains(aquisicaoNome.toLowerCase().trim())));

		if (status.equals(true))
			stream = stream.filter(c -> (c.getStatus().equals(status)));

		aquisicoes = stream.collect(Collectors.toList());
	}

	// M�todo chamado ao carregar pagina de consulta para popular tabela
	public String listar() {
		listarTabela();
		return null;
	}

	// Limpar tabela da consulta
	public String limpar() {
		limparFiltros();
		this.aquisicoes = new ArrayList<>(this.lista);
		return null;
	}

	public void limparFiltros() {
		this.aquisicaoNome = "";
	}

	@Transacional
	public String salvar(Aquisicao aquisicao) {
		aquisicao.setStatus(true);
		aquisicaoRepositorio.atualizar(aquisicao);
		this.aquisicoes = null;
		this.aquisicao = new Aquisicao();
		listarTabela();
		return null;
	}
	
	public String consultarAquisicoes(){
		return "aquisicaoConsultar";
	}
	
	public String aquisicaoConsultar(){
		
		if(message.equals("")){
			aquisicao = new Aquisicao();
			fornecedor = new Fornecedor();
			fornecedorDocumento = null;
			return "aquisicaoConsultar";
		}
		else{
			facesContext.info(message);
			return null;
		}
	}

	@Logging
	// M�todos que utilizam m�todos do reposit�rio
	@Transacional
	public void salvar() {
		

		getFuncionarioPorDocumento();
		
		if(funcionario == null){
			message = "Funcionário inexistente";
		}
		

		getFornecedorPorDocumento();
		if(fornecedor == null){
			message = "Fornecedor inexistente";
		}
		
		this.aquisicao.setStatus(true);

		if (aquisicao.getId() == null) {
			aquisicao.setPrecoTotal(new BigDecimal("0"));
			aquisicaoId = aquisicaoRepositorio.adicionarComRetorno(aquisicao);
		} else {
			aquisicaoRepositorio.atualizar(aquisicao);
		}
		
	}

	@Transacional
	public void recuperarAquisicaoPorId() {
		aquisicao = aquisicaoRepositorio.buscarPorId(aquisicaoId);
	}

	// Remove um SubCategoria do banco de dados
	@Transacional
	public String remover(Aquisicao aquisicao) {
		aquisicao.setStatus(false);
		aquisicaoRepositorio.atualizar(aquisicao);
		this.aquisicoes = null;
		this.aquisicao = new Aquisicao();
		listarTabela();
		return null;
	}

	// Editar um SubCategoria
	public String editar(Aquisicao aquisicao) {
		return "aquisicao?aquisicaoId=" + aquisicao.getId();
	}

	public String consultarItensDaAquisicao(Aquisicao aquisicao) {
		return "itemDaAquisicaoConsultar?aquisicaoId=" + aquisicao.getId();
	}

	public String consultarItensDaAquisicao() {
		if (funcionario == null) {
			facesContext.warn("Funcionario inexistente");
			return null;
		}
		if (fornecedor == null) {
			facesContext.warn("Fornecedor inexistente");
			return null;
		}

		return "itemDaAquisicaoConsultar?faces-redirect=true&aquisicaoId=" + aquisicaoId;
	}

	public boolean aquisicaoIdExiste() {
		if (this.aquisicaoId == null)
			return false;
		return true;
	}

	@Transacional
	public void getFornecedorPorDocumento() {
		fornecedor = aquisicaoRepositorio.getFornecedorPorDocumento(fornecedorDocumento);
		aquisicao.setFornecedor(fornecedor);
	}

	public void fornecedorSelecionado(SelectEvent event) {
		fornecedor = (Fornecedor) event.getObject();
		fornecedorDocumento = fornecedor.getDocumento();
		requestContext.update("formAquisicao:fornecedor");
	}

	@Transacional
	public void getFuncionarioPorDocumento() {
		funcionario = aquisicaoRepositorio.getFuncionarioPorDocumento(funcionarioDocumento);
		aquisicao.setFuncionario(funcionario);
	}

	public void funcionarioSelecionado(SelectEvent event) {
		funcionario = (Funcionario) event.getObject();
		funcionarioDocumento = funcionario.getDocumento();
		requestContext.update("formAquisicao:funcionario");
	}

	// Método usado para carregar objeto para o dialog
	public void selecionarAquisicao(Aquisicao aquisicao) {
		requestContext.closeDialog(aquisicao);
	}

	public void inicializa() {
		recuperarAquisicaoPorId();
		fornecedorDocumento = aquisicao.getFornecedor().getDocumento();
		getFornecedorPorDocumento();
		funcionarioDocumento = aquisicao.getFuncionario().getDocumento();
		getFuncionarioPorDocumento();

	}
}
