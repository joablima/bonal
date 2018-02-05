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

import br.com.empresa.bonal.entidades.Fornecedor;
import br.com.empresa.bonal.entidades.ItemDeProducao;
import br.com.empresa.bonal.repositorio.FornecedorRepositorio;
import br.com.empresa.bonal.util.FacesContextUtil;
import br.com.empresa.bonal.util.enums.EnumPessoa;
import br.com.empresa.bonal.util.tx.Transacional;

@Named
@ViewScoped
public class FornecedorControle implements Serializable {
	private static final long serialVersionUID = 1L;

	private Fornecedor fornecedor = new Fornecedor();
	private String message = "";

	private Long fornecedorId;

	// Atributos para Consulta
	private String fornecedorNome = "";

	private Boolean status = true;
	// Listas para Consulta
	private List<Fornecedor> fornecedores;
	private List<ItemDeProducao> itensFornecidos = new ArrayList<>(); ;
	private List<Fornecedor> lista = new ArrayList<>();

	@Inject
	private FornecedorRepositorio fornecedorRepositorio;

	@Inject
	private RequestContext requestContext;

	@Inject
	private FacesContextUtil facesContext;

	@Inject
	private Logger logger;

	// getter e setters
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

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	// ----- Carrega os Enums em Arrays -----
	public EnumPessoa[] getEnumPessoa() {
		return EnumPessoa.values();
	}
	
	public List<ItemDeProducao> getItensFornecidos() {
		return itensFornecidos;
	}

	public void setItensFornecidos(List<ItemDeProducao> itensFornecidos) {
		this.itensFornecidos = itensFornecidos;
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

	public void filtrarItensFornecidos() {
		Stream<ItemDeProducao> stream = fornecedor.getItens().stream();

		stream = stream.filter(c -> (c.getNome().toLowerCase().contains(fornecedorNome.toLowerCase().trim()))
				| (c.getSubCategoria().getNome().toLowerCase().contains(fornecedorNome.toLowerCase().trim()))
				| (c.getSubCategoria().getCategoria().getNome().toLowerCase().contains(fornecedorNome.toLowerCase().trim()))
				| c.getCodigo().toString().toLowerCase().contains(fornecedorNome.toLowerCase().trim()));

		if (status.equals(true))
			stream = stream.filter(c -> (c.getStatus().equals(status)));

		itensFornecidos = stream.collect(Collectors.toList());
	}


	public void filtrarTabela() {
		Stream<Fornecedor> stream = lista.stream();

		stream = stream.filter(c -> (c.getNome().toLowerCase().contains(fornecedorNome.toLowerCase().trim()))
				| (c.getDocumento().toLowerCase().contains(fornecedorNome.toLowerCase().trim()))
				| c.getIdentificacao().toLowerCase().contains(fornecedorNome.toLowerCase().trim()));

		if (status.equals(true))
			stream = stream.filter(c -> (c.getStatus().equals(status)));

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

	@Transacional
	public String salvar(Fornecedor fornecedor) {
		fornecedor.setStatus(true);
		fornecedorRepositorio.atualizar(fornecedor);
		this.fornecedores = null;
		this.fornecedor = new Fornecedor();
		listarTabela();
		return null;
	}
	
	public String fornecedorConsultar(){
		
		if(message.equals("")){
			fornecedor = new Fornecedor();
			return "fornecedorConsultar";
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
		this.fornecedor.setStatus(true);

		Fornecedor existe = fornecedorRepositorio.getFornecedorPorDocumento(fornecedor.getDocumento());
		if (existe != null && (existe.getId() != fornecedor.getId())) {
			message = "Documento duplicado";
		}

		if (fornecedor.getId() == null) {
			fornecedorRepositorio.adicionar(fornecedor);
		} else {
			fornecedorRepositorio.atualizar(fornecedor);
		}
		logger.info(message);
	}

	@Transacional
	public void recuperarFornecedorPorId() {
		fornecedor = fornecedorRepositorio.buscarPorId(fornecedorId);
	}

	// Remove um Fornecedor do banco de dados
	@Transacional
	public String remover(Fornecedor fornecedor) {
		fornecedor.setStatus(false);
		fornecedorRepositorio.atualizar(fornecedor);
		this.fornecedores = null;
		this.fornecedor = new Fornecedor();
		listarTabela();
		return null;
	}

	// Editar um Fornecedor
	public String editar(Fornecedor fornecedor) {
		return "fornecedor?fornecedorId=" + fornecedor.getId();
	}

	public boolean fornecedorIdExiste() {
		if (this.fornecedorId == null)
			return false;
		return true;
	}

	// Método usado para carregar objeto para o dialog
	public void selecionarFornecedor(Fornecedor fornecedor) {
		requestContext.closeDialog(fornecedor);
	}

	public void inicializa() {
		recuperarFornecedorPorId();
		itensFornecidos = fornecedor.getItens();
	}
}
