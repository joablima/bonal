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

import org.apache.log4j.Logger;

import br.com.empresa.bonal.entidades.Cliente;
import br.com.empresa.bonal.entidades.Endereco;
import br.com.empresa.bonal.repositorio.ClienteRepositorio;
import br.com.empresa.bonal.util.FacesContextUtil;
import br.com.empresa.bonal.util.enums.EnumPessoa;
import br.com.empresa.bonal.util.tx.transacional;

@Named
@ViewScoped
public class ClienteControle implements Serializable {
	private static final long serialVersionUID = 1L;

	final static Logger logger = Logger.getLogger(ClienteControle.class);

	private Cliente cliente = new Cliente();

	// private EnumPessoa tipo;
	private Long clienteId;

	// Atributos para Consulta
	private String clienteNome = "";

	// Listas para Consulta
	private List<Cliente> clientes;
	private List<Cliente> lista = new ArrayList<>();

	private Boolean status = true;

	@Inject
	private ClienteRepositorio clienteRepositorio;

	@Inject
	private FacesContextUtil facesContext;

	// Getters and Setters
	public Cliente getCliente() {
		return cliente;
	}

	// Adicionado para propriedade de contexto das tabelas do Primefaces
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Endereco getEndereco() {
		return cliente.getEndereco();
	}

	// Adicionado para propriedade de contexto das tabelas do Primefaces
	public void setEndereco(Endereco endereco) {
		this.cliente.setEndereco(endereco);
	}

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
	@transacional
	public void listarTabela() {
		if (this.clientes == null) {
			lista = clienteRepositorio.listarTodos();
			clientes = new ArrayList<>(lista);
		}
		filtrarTabela();
	}

	public void filtrarTabela() {
		Stream<Cliente> stream = lista.stream();

		if (!clienteNome.equals(null))
			stream = stream.filter(c -> (c.getNome().toLowerCase().contains(clienteNome.toLowerCase().trim()))
					| (c.getDocumento().toLowerCase().contains(clienteNome.toLowerCase().trim()))
					| (c.getEmail().toLowerCase().contains(clienteNome.toLowerCase().trim()))
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

	public void salvar(Cliente cliente) {
		this.cliente = cliente;
		this.clienteId = cliente.getId();
		salvar();
	}

	// M�todos que utilizam m�todos do reposit�rio
	@transacional
	public String salvar() {
		String message = "";

		this.cliente.setStatus(true);
		if (cliente.getId() == null) {
			clienteRepositorio.adicionar(cliente);
			message += "Cliente Cadastrado com Sucesso.";
		} else {
			clienteRepositorio.atualizar(cliente);
			message += "Cliente Atualizado com Sucesso.";
		}
		facesContext.info(message);
		logger.info(message);
		this.cliente = new Cliente();
		return null;
	}

	@transacional
	public void recuperarClientePorId() {
		cliente = clienteRepositorio.buscarPorId(clienteId);
	}

	// Remove um Cliente do banco de dados
	@transacional
	public String remover(Cliente cliente) {
		cliente.setStatus(false);
		clienteRepositorio.remover(cliente);
		this.clientes = null;
		this.cliente = null;
		listarTabela();
		return null;
	}


	// Editar um Cliente
	public String editar(Cliente cliente) {
		return "cliente?clienteId=" + cliente.getId();
	}

	public String addCoeficientes() {
		return "coeficientetecnico?clienteId=" + this.clienteId;
	}

	public boolean ClienteIdExiste() {
		if (this.clienteId == null)
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
}
