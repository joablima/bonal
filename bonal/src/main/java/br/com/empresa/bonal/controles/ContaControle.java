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

import br.com.empresa.bonal.entidades.Conta;
import br.com.empresa.bonal.repositorio.ContaRepositorio;
import br.com.empresa.bonal.util.FacesContextUtil;
import br.com.empresa.bonal.util.enums.EnumStatusPagamento;
import br.com.empresa.bonal.util.tx.Transacional;

@Named
@ViewScoped
public class ContaControle implements Serializable {
	private static final long serialVersionUID = 1L;

	private Conta conta = new Conta();
	
	private String message = "";


	private Long contaId;

	// Atributos para Consulta
	private String contaNome = "";

	private Boolean status = true;
	// Listas para Consulta
	private List<Conta> contas;
	private List<Conta> lista = new ArrayList<>();

	@Inject
	private ContaRepositorio contaRepositorio;

	@Inject
	private RequestContext requestContext;

	@Inject
	private FacesContextUtil facesContext;

	@Inject
	private Logger logger;

	// Getters and Setters
	public Conta getConta() {
		return conta;
	}

	// Adicionado para propriedade de contexto das tabelas do Primefaces
	public void setConta(Conta c) {
		this.conta = c;
	}

	public Long getContaId() {
		return contaId;
	}

	public void setContaId(Long contaId) {
		this.contaId = contaId;
	}

	public String getContaNome() {
		return contaNome;
	}

	public void setContaNome(String contaNome) {
		this.contaNome = contaNome;
	}

	public List<Conta> getContas() {
		return contas;
	}

	public List<Conta> getLista() {
		return Collections.unmodifiableList(lista);
	}

	// verificar importancia dos m�todos abaixo //verificar se est�o trocados??
	public Integer getTotalContas() {
		return lista.size();
	}

	public Integer getTotalContasConsulta() {
		return contas.size();
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}


	// ----------------- METODOS ----------------------
	
	@Transacional
	public void listarTabela() {
		if (this.contas == null) {
			lista = contaRepositorio.listarTodos();
			contas = new ArrayList<>(lista);
		}
		filtrarTabela();
	}
	

	@Transacional
	public void preRenderView(ComponentSystemEvent event) {
		if (this.contas == null) {
			lista = contaRepositorio.listarTodos();
			contas = new ArrayList<>(lista);
		}
		filtrarTabela();
	}

	public void filtrarTabela() {
		Stream<Conta> stream = lista.stream();

		stream = stream
				.filter(c -> (c.getPrecoTotal().toString().toLowerCase().contains(contaNome.toLowerCase().trim()))
						| (c.getTipoDePagamento().toString().toLowerCase().contains(contaNome.toLowerCase().trim()))
						| (c.getStatusPagamento().toString().toLowerCase().contains(contaNome.toLowerCase().trim()))
						| (c.getTipo().toLowerCase().contains(contaNome.toLowerCase().trim())));

		

		contas = stream.collect(Collectors.toList());
	}

	// M�todo chamado ao carregar pagina de consulta para popular tabela
	public String listar() {
		listarTabela();
		return null;
	}

	// Limpar tabela da consulta
	public String limpar() {
		limparFiltros();
		this.contas = new ArrayList<>(this.lista);
		return null;
	}

	public void limparFiltros() {
		this.contaNome = "";
	}


	
	public String consultarContas(){
		return "contaConsultar?faces-redirect=true";
	}
	

	@Transacional
	public void recuperarContaPorId() {
		conta = contaRepositorio.buscarPorId(contaId);
	}


	public boolean contaIdExiste() {
		if (this.contaId == null)
			return false;
		return true;
	}

	// Método usado para carregar objeto para o dialog
	public void selecionarConta(Conta conta) {
		requestContext.closeDialog(conta);
	}

	public void inicializa() {
		recuperarContaPorId();

	}
	
	@Transacional
	public void mudarStatusPagamento(Conta conta){

		if(conta.getStatusPagamento().toString().toLowerCase().equals("pendente")){
			String pago = "PAGO";
			conta.setStatusPagamento(EnumStatusPagamento.valueOf(pago));
		}
		else{
			String pendente = "PENDENTE";
			conta.setStatusPagamento(EnumStatusPagamento.valueOf(pendente));
		}
		
		contaRepositorio.atualizar(conta);
	}
}
