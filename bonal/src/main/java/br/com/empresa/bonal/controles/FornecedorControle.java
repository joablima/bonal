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

import br.com.empresa.bonal.entidades.Endereco;
import br.com.empresa.bonal.entidades.Fornecedor;
import br.com.empresa.bonal.repositorio.FornecedorRepositorio;
import br.com.empresa.bonal.util.FacesContextUtil;
import br.com.empresa.bonal.util.enums.EnumPessoa;
import br.com.empresa.bonal.util.logging.Logging;
import br.com.empresa.bonal.util.tx.Transacional;

@Named
@ViewScoped
public class FornecedorControle implements Serializable {
	private static final long serialVersionUID = 1L;

	private Fornecedor fornecedor = new Fornecedor();

	// private EnumPessoa tipo;
	private Long fornecedorId;

	// Atributos para Consulta
	private String fornecedorNome = "";

	// Listas para Consulta
	private List<Fornecedor> fornecedores;
	private List<Fornecedor> lista = new ArrayList<>();

	private Boolean status = true;

	@Inject
	private FornecedorRepositorio fornecedorRepositorio;

	@Inject
	private RequestContext requestContext;

	@Inject
	private FacesContextUtil facesContext;

	@Inject
	private Logger logger;

	// Getters and Setters
	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	// Adicionado para propriedade de contexto das tabelas do Primefaces
	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Endereco getEndereco() {
		return fornecedor.getEndereco();
	}

	// Adicionado para propriedade de contexto das tabelas do Primefaces
	public void setEndereco(Endereco endereco) {
		this.fornecedor.setEndereco(endereco);
	}

	public Long getFornecedorId() {
		return fornecedorId;
	}

	public void setFornecedorId(Long fornecedorId) {
		this.fornecedorId = fornecedorId;
	}

	public String getFornecedorNome() {
		return fornecedorNome;
	}

	public void setFornecedorNome(String fornecedorNome) {
		this.fornecedorNome = fornecedorNome;
	}

	public List<Fornecedor> getFornecedores() {
		return fornecedores;
	}

	public List<Fornecedor> getLista() {
		return Collections.unmodifiableList(lista);
	}

	// verificar importancia dos m�todos abaixo //verificar se est�o trocados??
	public Integer getTotalFornecedores() {
		return lista.size();
	}

	public Integer getTotalFornecedoresConsulta() {
		return fornecedores.size();
	}

	// ----- Carrega os Enums em Arrays -----
	public EnumPessoa[] getEnumPessoa() {
		return EnumPessoa.values();
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	// ----------------- METODOS ----------------------
	@PostConstruct
	@Transacional
	public void listarTabela() {
		if (this.fornecedores == null) {
			lista = fornecedorRepositorio.listarTodos();
			fornecedores = new ArrayList<>(lista);
		}
		filtrarTabela();
	}

	public void filtrarTabela() {
		Stream<Fornecedor> stream = lista.stream();

		if (!fornecedorNome.equals(null))
			stream = stream.filter(f -> (f.getNome().toLowerCase().contains(fornecedorNome.toLowerCase().trim()))
					| (f.getDocumento().toLowerCase().contains(fornecedorNome.toLowerCase().trim()))
					| (f.getEmail().toLowerCase().contains(fornecedorNome.toLowerCase().trim()))
					| f.getIdentificacao().toLowerCase().contains(fornecedorNome.toLowerCase().trim()));

		if (status.equals(true))
			stream = stream.filter(f -> (f.getStatus().equals(status)));

		fornecedores = stream.collect(Collectors.toList());
	}

	// M�todo chamado ao carregar pagina de consulta para popular tabela
	public String listar() {
		listarTabela();
		return null;
	}

	// Limpar tabela da consulta
	public String limpar() {
		limparFiltros();
		this.fornecedores = new ArrayList<>(this.lista);
		return null;
	}

	public void limparFiltros() {
		this.fornecedorNome = "";

	}
	
	public String salvar(Fornecedor f){
		this.fornecedor = f;
		return salvar();
	}

	// M�todos que utilizam m�todos do reposit�rio
	@Transacional
	public String salvar() {
		String message = "";

		this.fornecedor.setStatus(true);
		if (fornecedor.getId() == null) {
			fornecedorRepositorio.adicionar(fornecedor);
			message += "Fornecedor Cadastrado com Sucesso.";
		} else {
			fornecedorRepositorio.atualizar(fornecedor);
			message += "Fornecedor Atualizado com Sucesso.";
		}
		facesContext.info(message);
		logger.info(message);
		this.fornecedor = new Fornecedor();
		return null;
	}

	@Transacional
	public void recuperarFornecedorPorId() {
		this.fornecedor = fornecedorRepositorio.buscarPorId(fornecedorId);
	}

	// Remove um Fornecedor do banco de dados
	@Transacional
	public String remover(Fornecedor fornecedor) {
		fornecedor.setStatus(false);
		fornecedorRepositorio.remover(fornecedor);
		this.fornecedores = null;
		listarTabela();
		return null;
	}

	@Logging
	public String editar(Fornecedor fornecedor) {
		return "fornecedor?fornecedorId=" + fornecedor.getId();
	}

	public String addCoeficientes() {
		return "coeficientetecnico?fornecedorId=" + this.fornecedorId;
	}

	public boolean fornecedorIdExiste() {
		if (this.fornecedorId == null)
			return false;
		return true;
	}

	// vou tirar isso daqui não se preocupe
	public void carregandoDados() {
		try {
			// simulate a long running request
			Thread.sleep(1500);
		} catch (Exception e) {
			// ignore
		}
	}

	// Método usado para carregar objeto para o dialog
	public void selecionarFornecedor(Fornecedor fornecedor) {
		requestContext.closeDialog(fornecedor);
	}
}
