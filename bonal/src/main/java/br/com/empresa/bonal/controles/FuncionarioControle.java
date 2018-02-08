package br.com.empresa.bonal.controles;

import java.io.Serializable;
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

import br.com.empresa.bonal.entidades.Cargo;
import br.com.empresa.bonal.entidades.Funcionario;
import br.com.empresa.bonal.repositorio.FuncionarioRepositorio;
import br.com.empresa.bonal.util.FacesContextUtil;
import br.com.empresa.bonal.util.logging.Logging;
import br.com.empresa.bonal.util.tx.Transacional;

@Named
@ViewScoped
public class FuncionarioControle implements Serializable {
	private static final long serialVersionUID = 1L;

	private Funcionario funcionario = new Funcionario();
	
	private String message = "";

	private String cargoCodigo = "";
	private Cargo cargo = new Cargo();

	private Long funcionarioId;

	// Atributos para Consulta
	private String funcionarioNome = "";

	private Boolean status = true;
	// Listas para Consulta
	private List<Funcionario> funcionarios;
	private List<Funcionario> lista = new ArrayList<>();

	@Inject
	private FuncionarioRepositorio funcionarioRepositorio;

	@Inject
	private RequestContext requestContext;

	@Inject
	private FacesContextUtil facesContext;

	@Inject
	private Logger logger;

	//getter e setters
	public Long getFuncionarioId() {
		return funcionarioId;
	}

	public void setFuncionarioId(Long funcionarioId) {
		this.funcionarioId = funcionarioId;
	}

	public String getFuncionarioNome() {
		return funcionarioNome;
	}

	public void setFuncionarioNome(String funcionarioNome) {
		this.funcionarioNome = funcionarioNome;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public List<Funcionario> getLista() {
		return Collections.unmodifiableList(lista);
	}

	// verificar importancia dos m�todos abaixo //verificar se est�o trocados??
	public Integer getTotalFuncionarios() {
		return lista.size();
	}

	public Integer getTotalFuncionariosConsulta() {
		return funcionarios.size();
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getCargoCodigo() {
		return cargoCodigo;
	}

	public void setCargoCodigo(String cargoCodigo) {
		this.cargoCodigo = cargoCodigo;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	// ----------------- METODOS ----------------------

	@Transacional
	public void listarTabela() {
		if (this.funcionarios == null) {
			lista = funcionarioRepositorio.listarTodos();
			funcionarios = new ArrayList<>(lista);
		}
		filtrarTabela();
	}
	
	@Logging
	@Transacional
	public void preRenderView(ComponentSystemEvent event) {
		if (this.funcionarios == null) {
			lista = funcionarioRepositorio.listarTodos();
			funcionarios = new ArrayList<>(lista);
		}

		filtrarTabela();
	}

	public void filtrarTabela() {
		Stream<Funcionario> stream = lista.stream();

		stream = stream.filter(c -> (c.getNome().toLowerCase().contains(funcionarioNome.toLowerCase().trim()))
				| (c.getDocumento().toLowerCase().contains(funcionarioNome.toLowerCase().trim()))
				| (c.getCargo().getNome().toLowerCase().contains(funcionarioNome.toLowerCase().trim()))
				| c.getIdentificacao().toLowerCase().contains(funcionarioNome.toLowerCase().trim()));

		if (status.equals(true))
			stream = stream.filter(c -> (c.getStatus().equals(status)));

		funcionarios = stream.collect(Collectors.toList());
	}

	// M�todo chamado ao carregar pagina de consulta para popular tabela
	public String listar() {
		listarTabela();
		return null;
	}

	// Limpar tabela da consulta
	public String limpar() {
		limparFiltros();
		this.funcionarios = new ArrayList<>(this.lista);
		return null;
	}

	public void limparFiltros() {
		this.funcionarioNome = "";
	}
	
	@Transacional
	public String salvar(Funcionario funcionario){
		funcionario.setStatus(true);
		funcionarioRepositorio.atualizar(funcionario);
		this.funcionarios = null;
		this.funcionario = new Funcionario();
		listarTabela();
		return null;
	}
	
	public String funcionarioConsultar(){
		
		if(message.equals("")){
			funcionario = new Funcionario();
			cargo = new Cargo();
			cargoCodigo = null;
			return "funcionarioConsultar?faces-redirect=true";
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
		this.funcionario.setStatus(true);
		this.funcionario.setTipo("PESSOA_FISICA");
		
		cargo = funcionarioRepositorio.getCargoPorCodigo(cargoCodigo);

		if (cargo == null) {
			message = "cargo inexistente, insira um codigo de cargo válido";
		}
	
		
		funcionario.setCargo(cargo);
		
		
		Funcionario existe = funcionarioRepositorio.getFuncionarioPorCpf(funcionario.getDocumento());
		if (existe != null && (existe.getId()!=funcionario.getId())) {
			message = "Codigo duplicado";
		}

		if (funcionario.getId() == null) {
			funcionarioRepositorio.adicionar(funcionario);
		} else {
			funcionarioRepositorio.atualizar(funcionario);
		}
		
		logger.info(message);
		
	}

	@Transacional
	public void recuperarFuncionarioPorId() {
		funcionario = funcionarioRepositorio.buscarPorId(funcionarioId);
	}

	// Remove um Funcionario do banco de dados
	@Transacional
	public String remover(Funcionario funcionario) {
		funcionario.setStatus(false);
		funcionarioRepositorio.atualizar(funcionario);
		this.funcionarios = null;
		this.funcionario = new Funcionario();
		listarTabela();
		return null;
	}

	// Editar um Funcionario
	public String editar(Funcionario funcionario) {
		return "funcionario?funcionarioId=" + funcionario.getId();
	}

	public boolean funcionarioIdExiste() {
		if (this.funcionarioId == null)
			return false;
		return true;
	}

	public void cargoSelecionada(SelectEvent event) {
		cargo = (Cargo) event.getObject();
		cargoCodigo = cargo.getCodigo();
		requestContext.update("formFuncionario:cargo");
	}

	@Transacional
	public void getCargoPorCodigo() {
		cargo = funcionarioRepositorio.getCargoPorCodigo(cargoCodigo);
	}

	
	// Método usado para carregar objeto para o dialog
	public void selecionarFuncionario(Funcionario funcionario) {
		requestContext.closeDialog(funcionario);
	}

	public void inicializa() {
		recuperarFuncionarioPorId();
		cargoCodigo = funcionario.getCargo().getCodigo();
		getCargoPorCodigo();
	}
}
