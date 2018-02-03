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

import br.com.empresa.bonal.entidades.Cliente;
import br.com.empresa.bonal.entidades.Funcionario;
import br.com.empresa.bonal.entidades.Venda;
import br.com.empresa.bonal.repositorio.VendaRepositorio;
import br.com.empresa.bonal.util.FacesContextUtil;
import br.com.empresa.bonal.util.logging.Logging;
import br.com.empresa.bonal.util.tx.Transacional;

@Named
@ViewScoped
public class VendaControle implements Serializable {
	private static final long serialVersionUID = 1L;

	private Venda venda = new Venda();

	private String clienteDocumento = "";
	private Cliente cliente = new Cliente();

	private String funcionarioDocumento = "";
	private Funcionario funcionario = new Funcionario();

	private Long vendaId;

	// Atributos para Consulta
	private String vendaNome = "";

	private Boolean status = true;
	// Listas para Consulta
	private List<Venda> vendas;
	private List<Venda> lista = new ArrayList<>();

	@Inject
	private VendaRepositorio vendaRepositorio;

	@Inject
	private RequestContext requestContext;

	@Inject
	private FacesContextUtil facesContext;

	@Inject
	private Logger logger;

	// Getters and Setters
	public Venda getVenda() {
		return venda;
	}

	// Adicionado para propriedade de contexto das tabelas do Primefaces
	public void setVenda(Venda c) {
		this.venda = c;
	}

	public Long getVendaId() {
		return vendaId;
	}

	public void setVendaId(Long vendaId) {
		this.vendaId = vendaId;
	}

	public String getVendaNome() {
		return vendaNome;
	}

	public void setVendaNome(String vendaNome) {
		this.vendaNome = vendaNome;
	}

	public List<Venda> getVendas() {
		return vendas;
	}

	public List<Venda> getLista() {
		return Collections.unmodifiableList(lista);
	}

	// verificar importancia dos m�todos abaixo //verificar se est�o trocados??
	public Integer getTotalVendas() {
		return lista.size();
	}

	public Integer getTotalVendasConsulta() {
		return vendas.size();
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getClienteDocumento() {
		return clienteDocumento;
	}

	public void setClienteDocumento(String clienteDocumento) {
		this.clienteDocumento = clienteDocumento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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
		if (this.vendas == null) {
			lista = vendaRepositorio.listarTodos();
			vendas = new ArrayList<>(lista);
		}
		filtrarTabela();
	}

	public void filtrarTabela() {
		Stream<Venda> stream = lista.stream();

		stream = stream.filter(c -> (c.getCliente().getNome().toLowerCase().contains(vendaNome.toLowerCase().trim()))
				| (c.getFuncionario().getNome().toLowerCase().contains(vendaNome.toLowerCase().trim())));

		if (status.equals(true))
			stream = stream.filter(c -> (c.getStatus().equals(status)));

		vendas = stream.collect(Collectors.toList());
	}

	// M�todo chamado ao carregar pagina de consulta para popular tabela
	public String listar() {
		listarTabela();
		return null;
	}

	// Limpar tabela da consulta
	public String limpar() {
		limparFiltros();
		this.vendas = new ArrayList<>(this.lista);
		return null;
	}

	public void limparFiltros() {
		this.vendaNome = "";
	}

	@Transacional
	public String salvar(Venda venda) {
		venda.setStatus(true);
		vendaRepositorio.atualizar(venda);
		this.vendas = null;
		this.venda = new Venda();
		listarTabela();
		return null;
	}
	
	@Transacional
	public void adicionar() {
		venda.setStatus(true);
		cliente = vendaRepositorio.getClientePorDocumento(clienteDocumento);
		funcionario = vendaRepositorio.getFuncionarioPorDocumento(funcionarioDocumento);

		if (funcionario == null) {
			facesContext.warn("funcionario inexistente");
		}
		if (cliente == null) {
			facesContext.warn("Cliente inexistente");
		}

		
		venda.setCliente(cliente);
		venda.setFuncionario(funcionario);
		
		vendaId = vendaRepositorio.adicionarComRetorno(venda);
	}
	
	@Logging
	// M�todos que utilizam m�todos do reposit�rio
	@Transacional
	public String salvar() {
		String message = "";
		this.venda.setStatus(true);

		cliente = vendaRepositorio.getClientePorDocumento(venda.getCliente().getDocumento());

		if (cliente == null) {
			facesContext.warn("Unidade de medida inexistente, insira um codigo válido");
			return null;
		}

		venda.setCliente(cliente);
		
		funcionario = vendaRepositorio.getFuncionarioPorDocumento(venda.getFuncionario().getDocumento());

		if (funcionario == null) {
			facesContext.warn("funcionario inexistente");
			return null;
		}

		venda.setFuncionario(funcionario);

		if (venda.getId() == null) {
			vendaId = vendaRepositorio.adicionarComRetorno(venda);
			message += "Venda Cadastrada com Sucesso.";
		} else {
			vendaRepositorio.atualizar(venda);
			message += "Venda Atualizada com Sucesso.";
		}
		facesContext.info(message);
		logger.info(message);
		venda = new Venda();
		cliente = new Cliente();
		clienteDocumento = null;
		return null;
	}

	@Transacional
	public void recuperarVendaPorId() {
		venda = vendaRepositorio.buscarPorId(vendaId);
	}

	// Remove um SubCategoria do banco de dados
	@Transacional
	public String remover(Venda venda) {
		venda.setStatus(false);
		vendaRepositorio.atualizar(venda);
		this.vendas = null;
		this.venda = new Venda();
		listarTabela();
		return null;
	}

	// Editar um SubCategoria
	public String editar(Venda venda) {
		return "venda?vendaId=" + venda.getId();
	}
	
	public String consultarItensDaVenda(Venda venda){		
		return "itemDaVendaConsultar?vendaId="+venda.getId();
	}
	
	public String consultarItensDaVenda(){
		if(funcionario == null ){
			facesContext.warn("Funcionario inexistente");
			return null;
		}
		if(cliente == null ){
			facesContext.warn("Cliente inexistente");
			return null;
		}
		
		return "itemDaVendaConsultar?faces-redirect=true&vendaId="+vendaId;
	}
	

	public boolean vendaIdExiste() {
		if (this.vendaId == null)
			return false;
		return true;
	}

	@Transacional
	public void getClientePorDocumento() {
		cliente = vendaRepositorio.getClientePorDocumento(clienteDocumento);
		venda.setCliente(cliente);
	}

	public void clienteSelecionado(SelectEvent event) {
		cliente = (Cliente) event.getObject();
		clienteDocumento = cliente.getDocumento();
		venda.setCliente(cliente);
		requestContext.update("formVenda:cliente");
	}
	
	@Transacional
	public void getFuncionarioPorDocumento() {
		funcionario = vendaRepositorio.getFuncionarioPorDocumento(funcionarioDocumento);
		venda.setFuncionario(funcionario);
	}

	public void funcionarioSelecionado(SelectEvent event) {
		funcionario = (Funcionario) event.getObject();
		funcionarioDocumento = funcionario.getDocumento();
		venda.setFuncionario(funcionario);
		requestContext.update("formVenda:funcionario");
	}

	// Método usado para carregar objeto para o dialog
	public void selecionarVenda(Venda venda) {
		requestContext.closeDialog(venda);
	}

	public void inicializa() {
		recuperarVendaPorId();

		clienteDocumento = venda.getCliente().getDocumento();
		getClientePorDocumento();
		funcionarioDocumento = venda.getFuncionario().getDocumento();
		getFuncionarioPorDocumento();
		
		

	}
}
