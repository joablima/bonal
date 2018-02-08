package br.com.empresa.bonal.controles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.empresa.bonal.entidades.Cliente;
import br.com.empresa.bonal.entidades.Cargo;
import br.com.empresa.bonal.repositorio.ClienteRepositorio;
import br.com.empresa.bonal.util.FacesContextUtil;
import br.com.empresa.bonal.util.enums.EnumPessoa;
import br.com.empresa.bonal.util.tx.Transacional;

@Named
@ViewScoped
public class ClienteControle implements Serializable {
	private static final long serialVersionUID = 1L;

	private Cliente cliente = new Cliente();
	private String message = "";

	private Long clienteId;

	// Atributos para Consulta
	private String clienteNome = "";
	private String mask = "999-999-999-99";

	private Boolean status = true;
	// Listas para Consulta
	private List<Cliente> clientes;
	private List<Cliente> lista = new ArrayList<>();

	@Inject
	private ClienteRepositorio clienteRepositorio;

	@Inject
	private RequestContext requestContext;

	@Inject
	private FacesContextUtil facesContext;

	@Inject
	private Logger logger;

	// getter e setters
	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}

	public String getClienteNome() {
		return clienteNome;
	}

	public void setClienteNome(String clienteNome) {
		this.clienteNome = clienteNome;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public List<Cliente> getLista() {
		return Collections.unmodifiableList(lista);
	}

	// verificar importancia dos m�todos abaixo //verificar se est�o trocados??
	public Integer getTotalClientes() {
		return lista.size();
	}

	public Integer getTotalClientesConsulta() {
		return clientes.size();
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	// ----- Carrega os Enums em Arrays -----
	public EnumPessoa[] getEnumPessoa() {
		return EnumPessoa.values();
	}
	
	public String getMask() {
		
		return mask;
	}


	public void setMask(String mask) {
		this.mask = mask;
	}

	
	
	// ----------------- METODOS ----------------------
	
	

	@Transacional
	public void listarTabela() {
		if (this.clientes == null) {
			lista = clienteRepositorio.listarTodos();
			clientes = new ArrayList<>(lista);
		}
		filtrarTabela();
	}
	

	@Transacional
	public void preRenderView(ComponentSystemEvent event) {
		if (this.clientes == null) {
			lista = clienteRepositorio.listarTodos();
			clientes = new ArrayList<>(lista);
		}
		filtrarTabela();
	}

	public void filtrarTabela() {
		Stream<Cliente> stream = lista.stream();

		stream = stream.filter(c -> (c.getNome().toLowerCase().contains(clienteNome.toLowerCase().trim()))
				| (c.getDocumento().toLowerCase().contains(clienteNome.toLowerCase().trim()))
				| c.getIdentificacao().toLowerCase().contains(clienteNome.toLowerCase().trim()));

		if (status.equals(true))
			stream = stream.filter(c -> (c.getStatus().equals(status)));

		clientes = stream.collect(Collectors.toList());
	}

	// M�todo chamado ao carregar pagina de consulta para popular tabela
	public String listar() {
		listarTabela();
		return null;
	}

	// Limpar tabela da consulta
	public String limpar() {
		limparFiltros();
		this.clientes = new ArrayList<>(this.lista);
		return null;
	}

	public void limparFiltros() {
		this.clienteNome = "";
	}

	@Transacional
	public String salvar(Cliente cliente) {
		cliente.setStatus(true);
		clienteRepositorio.atualizar(cliente);
		this.clientes = null;
		this.cliente = new Cliente();
		listarTabela();
		return null;
	}
	
	public String clienteConsultar(){
		
		if(message.equals("")){

			cliente = new Cliente();
			return "clienteConsultar?faces-redirect=true";
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
		this.cliente.setStatus(true);

		Cliente existe = clienteRepositorio.getClientePorDocumento(cliente.getDocumento());
		if (existe != null && (existe.getId() != cliente.getId())) {
			message = "Documento duplicado";
		}

		if (cliente.getId() == null) {
			clienteRepositorio.adicionar(cliente);
		} else {
			clienteRepositorio.atualizar(cliente);
		}
		logger.info(message);
	}

	@Transacional
	public void recuperarClientePorId() {
		cliente = clienteRepositorio.buscarPorId(clienteId);
	}

	// Remove um Cliente do banco de dados
	@Transacional
	public String remover(Cliente cliente) {
		cliente.setStatus(false);
		clienteRepositorio.atualizar(cliente);
		this.clientes = null;
		this.cliente = new Cliente();
		listarTabela();
		return null;
	}

	// Editar um Cliente
	public String editar(Cliente cliente) {
		return "cliente?faces-redirect=true&clienteId=" + cliente.getId();
	}

	public boolean clienteIdExiste() {
		if (this.clienteId == null)
			return false;
		return true;
	}

	// Método usado para carregar objeto para o dialog
	public void selecionarCliente(Cliente cliente) {
		requestContext.closeDialog(cliente);
	}
	
	public void atualizarMascara(){
		if(cliente.getTipo().equals("PESSOA_FISICA")){
			mask = "999.999.999-99";
		}
		else{
			mask = "99.999.999/9999-99";
		}
	}
	public void inicializa() {
		recuperarClientePorId();
	}
}
