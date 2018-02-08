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

import br.com.empresa.bonal.entidades.Servico;
import br.com.empresa.bonal.entidades.Categoria;
import br.com.empresa.bonal.entidades.ItemDeProducao;
import br.com.empresa.bonal.entidades.SubCategoria;
import br.com.empresa.bonal.entidades.UnidadeDeMedida;
import br.com.empresa.bonal.repositorio.ServicoRepositorio;
import br.com.empresa.bonal.util.FacesContextUtil;
import br.com.empresa.bonal.util.tx.Transacional;

@Named
@ViewScoped
public class ServicoControle implements Serializable {
	private static final long serialVersionUID = 1L;

	private Servico servico = new Servico();
	private String message = "";

	private String categoriaCodigo = "";
	private Categoria categoria = new Categoria();
	private String subCategoriaCodigo = "";
	private SubCategoria subCategoria = new SubCategoria();
	private String unidadeDeMedidaSigla = "";
	private UnidadeDeMedida unidadeDeMedida = new UnidadeDeMedida();

	private Long servicoId;

	// Atributos para Consulta
	private String servicoNome = "";

	private Boolean status = true;
	// Listas para Consulta
	private List<Servico> servicos;
	private List<Servico> lista = new ArrayList<>();

	@Inject
	private ServicoRepositorio servicoRepositorio;

	@Inject
	private RequestContext requestContext;

	@Inject
	private FacesContextUtil facesContext;

	@Inject
	private Logger logger;

	// Getters and Setters
	public Servico getServico() {
		return servico;
	}

	// Adicionado para propriedade de contexto das tabelas do Primefaces
	public void setServico(Servico c) {
		this.servico = c;
	}

	public Long getServicoId() {
		return servicoId;
	}

	public void setServicoId(Long servicoId) {
		this.servicoId = servicoId;
	}

	public String getServicoNome() {
		return servicoNome;
	}

	public void setServicoNome(String servicoNome) {
		this.servicoNome = servicoNome;
	}

	public List<Servico> getServicos() {
		return servicos;
	}

	public List<Servico> getLista() {
		return Collections.unmodifiableList(lista);
	}

	// verificar importancia dos m�todos abaixo //verificar se est�o trocados??
	public Integer getTotalServicos() {
		return lista.size();
	}

	public Integer getTotalServicosConsulta() {
		return servicos.size();
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getSubCategoriaCodigo() {
		return subCategoriaCodigo;
	}

	public void setSubCategoriaCodigo(String subCategoriaCodigo) {
		this.subCategoriaCodigo = subCategoriaCodigo;
	}

	public String getCategoriaCodigo() {
		return categoriaCodigo;
	}

	public void setCategoriaCodigo(String categoriaCodigo) {
		this.categoriaCodigo = categoriaCodigo;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String getUnidadeDeMedidaSigla() {
		return unidadeDeMedidaSigla;
	}

	public void setUnidadeDeMedidaSigla(String unidadeDeMedidaSigla) {
		this.unidadeDeMedidaSigla = unidadeDeMedidaSigla;
	}

	public SubCategoria getSubCategoria() {
		return subCategoria;
	}

	public void setSubCategoria(SubCategoria subCategoria) {
		this.subCategoria = subCategoria;
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
		if (this.servicos == null) {
			lista = servicoRepositorio.listarTodos();
			servicos = new ArrayList<>(lista);
		}
		filtrarTabela();
	}

	public void filtrarTabela() {
		Stream<Servico> stream = lista.stream();

		stream = stream.filter(c -> (c.getNome().toLowerCase().contains(servicoNome.toLowerCase().trim()))
				| (c.getCodigo().toLowerCase().contains(servicoNome.toLowerCase().trim()))
				| (c.getSubCategoria().getNome().toLowerCase().contains(servicoNome.toLowerCase().trim()))
				| c.getDescricao().toLowerCase().contains(servicoNome.toLowerCase().trim()));

		if (status.equals(true))
			stream = stream.filter(c -> (c.getStatus().equals(status)));

		servicos = stream.collect(Collectors.toList());
	}

	// M�todo chamado ao carregar pagina de consulta para popular tabela
	public String listar() {
		listarTabela();
		return null;
	}

	// Limpar tabela da consulta
	public String limpar() {
		limparFiltros();
		this.servicos = new ArrayList<>(this.lista);
		return null;
	}

	public void limparFiltros() {
		this.servicoNome = "";
	}
	@Transacional
	public String salvar(Servico servico){
		servico.setStatus(true);
		servicoRepositorio.atualizar(servico);
		this.servicos = null;
		this.servico = new Servico();
		listarTabela();
		return null;
	}
	
	public String servicoConsultar(){
		
		if(message.equals("")){
			servico = new Servico();
			unidadeDeMedida = new UnidadeDeMedida();
			unidadeDeMedidaSigla = null;
			subCategoria = new SubCategoria();
			subCategoriaCodigo = null;
			categoria = new Categoria();
			categoriaCodigo = null;
			return "servicoConsultar?faces-redirect=true";
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
		this.servico.setStatus(true);
		
		subCategoria = servicoRepositorio.getSubCategoriaPorCodigo(servico.getSubCategoria().getCodigo());
		unidadeDeMedida = servicoRepositorio.getUnidadeDeMedidaPorSigla(servico.getUnidadeDeMedida().getSigla());

		if (subCategoria == null) {
			message = "SubCategoria inexistente, insira um codigo de categoria válido";
		}
		if (!subCategoria.getCategoria().getTipo().toString().toLowerCase().equals("servico")) {
			message = "SubCategoria inválida! Está associada com uma categoria de "
					+ subCategoria.getCategoria().getTipo().toString().toLowerCase()
					+ ". Não é possível inserir servico nela.";
		}

		if (unidadeDeMedida == null) {
			message = "Unidade de medida inexistente, insira um codigo válido";
		}
		
		servico.setSubCategoria(subCategoria);
		servico.setUnidadeDeMedida(unidadeDeMedida);
		
		ItemDeProducao existe = servicoRepositorio.getItemDeProducaoPorCodigo(servico.getCodigo());
		if (existe != null && (existe.getId()!=servico.getId())) {
			message = "Codigo duplicado";
		}

		if (servico.getId() == null) {
			servicoRepositorio.adicionar(servico);
		} else {
			servicoRepositorio.atualizar(servico);
		}
		logger.info(message);

	}

	@Transacional
	public void recuperarServicoPorId() {
		servico = servicoRepositorio.buscarPorId(servicoId);
	}

	// Remove um SubCategoria do banco de dados
	@Transacional
	public String remover(Servico servico) {
		servico.setStatus(false);
		servicoRepositorio.atualizar(servico);
		this.servicos = null;
		this.servico = new Servico();
		listarTabela();
		return null;
	}

	// Editar um SubCategoria
	public String editar(Servico servico) {
		return "servico?faces-redirect=true&servicoId=" + servico.getId();
	}

	public boolean servicoIdExiste() {
		if (this.servicoId == null)
			return false;
		return true;
	}

	public void categoriaSelecionada(SelectEvent event) {
		categoria = (Categoria) event.getObject();
		categoriaCodigo = categoria.getCodigo();
		servico.setCodigo(categoriaCodigo + "-00-00");
		requestContext.update("formServico:categoria");
	}

	@Transacional
	public void getCategoriaPorCodigo() {
		categoria = servicoRepositorio.getCategoriaPorCodigo(categoriaCodigo);
	}

	@Transacional
	public void getUnidadeDeMedidaPorSigla() {
		unidadeDeMedida = servicoRepositorio.getUnidadeDeMedidaPorSigla(unidadeDeMedidaSigla);
		servico.setUnidadeDeMedida(unidadeDeMedida);
	}

	@Transacional
	public void getSubCategoriaPorCodigo() {
		subCategoria = servicoRepositorio.getSubCategoriaPorCodigo(subCategoriaCodigo);
		servico.setSubCategoria(subCategoria);
	}

	public void subCategoriaSelecionada(SelectEvent event) {
		subCategoria = (SubCategoria) event.getObject();
		categoria = subCategoria.getCategoria();
		categoriaCodigo = categoria.getCodigo();
		subCategoriaCodigo = subCategoria.getCodigo();
		servico.setCodigo(subCategoriaCodigo+"-00");
		servico.setSubCategoria(subCategoria);
		requestContext.update("formServico:subCategoria");
	}

	public void unidadeDeMedidaSelecionada(SelectEvent event) {
		unidadeDeMedida = (UnidadeDeMedida) event.getObject();
		unidadeDeMedidaSigla = unidadeDeMedida.getSigla();
		servico.setUnidadeDeMedida(unidadeDeMedida);
		requestContext.update("formServico:unidadeDeMedida");
	}

	// Método usado para carregar objeto para o dialog
	public void selecionarServico(Servico servico) {
		requestContext.closeDialog(servico);
	}

	public void inicializa() {
		recuperarServicoPorId();
		subCategoriaCodigo = servico.getSubCategoria().getCodigo();
		getSubCategoriaPorCodigo();
		categoriaCodigo = subCategoria.getCategoria().getCodigo();
		getCategoriaPorCodigo();
		unidadeDeMedidaSigla = servico.getUnidadeDeMedida().getSigla();
		getUnidadeDeMedidaPorSigla();

	}

	public void constroiEstrutura() {

		String aux = servico.getCodigo();

		categoriaCodigo = aux.substring(0, 4);
		getCategoriaPorCodigo();
		subCategoriaCodigo = aux.substring(0, 7);
		getSubCategoriaPorCodigo();

	}

}
