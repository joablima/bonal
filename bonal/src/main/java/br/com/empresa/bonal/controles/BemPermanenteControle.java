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

import br.com.empresa.bonal.entidades.BemPermanente;
import br.com.empresa.bonal.entidades.Categoria;
import br.com.empresa.bonal.entidades.ItemDeProducao;
import br.com.empresa.bonal.entidades.SubCategoria;
import br.com.empresa.bonal.entidades.UnidadeDeMedida;
import br.com.empresa.bonal.repositorio.BemPermanenteRepositorio;
import br.com.empresa.bonal.util.FacesContextUtil;
import br.com.empresa.bonal.util.tx.Transacional;

@Named
@ViewScoped
public class BemPermanenteControle implements Serializable {
	private static final long serialVersionUID = 1L;

	private BemPermanente bemPermanente = new BemPermanente();

	private String categoriaCodigo = "";
	private Categoria categoria = new Categoria();
	private String subCategoriaCodigo = "";
	private SubCategoria subCategoria = new SubCategoria();

	private Long bemPermanenteId;

	// Atributos para Consulta
	private String bemPermanenteNome = "";

	private Boolean status = true;
	// Listas para Consulta
	private List<BemPermanente> bensPermanentes;
	private List<BemPermanente> lista = new ArrayList<>();

	@Inject
	private BemPermanenteRepositorio bemPermanenteRepositorio;

	@Inject
	private RequestContext requestContext;

	@Inject
	private FacesContextUtil facesContext;

	@Inject
	private Logger logger;

	// Getters and Setters
	public BemPermanente getBemPermanente() {
		return bemPermanente;
	}

	// Adicionado para propriedade de contexto das tabelas do Primefaces
	public void setBemPermanente(BemPermanente c) {
		this.bemPermanente = c;
	}

	public Long getBemPermanenteId() {
		return bemPermanenteId;
	}

	public void setBemPermanenteId(Long bemPermanenteId) {
		this.bemPermanenteId = bemPermanenteId;
	}

	public String getBemPermanenteNome() {
		return bemPermanenteNome;
	}

	public void setBemPermanenteNome(String bemPermanenteNome) {
		this.bemPermanenteNome = bemPermanenteNome;
	}

	public List<BemPermanente> getBensPermanentes() {
		return bensPermanentes;
	}

	public List<BemPermanente> getLista() {
		return Collections.unmodifiableList(lista);
	}

	// verificar importancia dos m�todos abaixo //verificar se est�o trocados??
	public Integer getTotalBensPermanentes() {
		return lista.size();
	}

	public Integer getTotalBensPermanentesConsulta() {
		return bensPermanentes.size();
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

	public SubCategoria getSubCategoria() {
		return subCategoria;
	}

	public void setSubCategoria(SubCategoria subCategoria) {
		this.subCategoria = subCategoria;
	}

	// ----------------- METODOS ----------------------
	@PostConstruct
	@Transacional
	public void listarTabela() {
		if (this.bensPermanentes == null) {
			lista = bemPermanenteRepositorio.listarTodos();
			bensPermanentes = new ArrayList<>(lista);
		}
		filtrarTabela();
	}

	public void filtrarTabela() {
		Stream<BemPermanente> stream = lista.stream();

		stream = stream.filter(c -> (c.getNome().toLowerCase().contains(bemPermanenteNome.toLowerCase().trim()))
				| (c.getCodigo().toLowerCase().contains(bemPermanenteNome.toLowerCase().trim()))
				| (c.getSubCategoria().getNome().toLowerCase().contains(bemPermanenteNome.toLowerCase().trim()))
				| c.getDescricao().toLowerCase().contains(bemPermanenteNome.toLowerCase().trim()));

		if (status.equals(true))
			stream = stream.filter(c -> (c.getStatus().equals(status)));

		bensPermanentes = stream.collect(Collectors.toList());
	}

	// M�todo chamado ao carregar pagina de consulta para popular tabela
	public String listar() {
		listarTabela();
		return null;
	}

	// Limpar tabela da consulta
	public String limpar() {
		limparFiltros();
		this.bensPermanentes = new ArrayList<>(this.lista);
		return null;
	}

	public void limparFiltros() {
		this.bemPermanenteNome = "";
	}
	@Transacional
	public String salvar(BemPermanente bemPermanente){
		bemPermanente.setStatus(true);
		bemPermanenteRepositorio.atualizar(bemPermanente);
		this.bensPermanentes = null;
		this.bemPermanente = new BemPermanente();
		listarTabela();
		return null;
	}

	// M�todos que utilizam m�todos do reposit�rio
	@Transacional
	public String salvar() {
		String message = "";
		this.bemPermanente.setStatus(true);
		
		subCategoria = bemPermanenteRepositorio.getSubCategoriaPorCodigo(bemPermanente.getSubCategoria().getCodigo());

		if (subCategoria == null) {
			facesContext.warn("SubCategoria inexistente, insira um codigo de categoria válido");
			return null;
		}
		if (!subCategoria.getCategoria().getTipo().toString().toLowerCase().equals("bem_permanente")) {
			facesContext.warn("SubCategoria inválida! Está associada com uma categoria de "
					+ subCategoria.getCategoria().getTipo().toString().toLowerCase()
					+ ". Não é possível inserir bens permanente nela.");
			return null;
		}
		
		bemPermanente.setSubCategoria(subCategoria);
		
		ItemDeProducao existe = bemPermanenteRepositorio.getItemDeProducaoPorCodigo(bemPermanente.getCodigo());
		if (existe != null && (existe.getId()!=bemPermanente.getId())) {
			facesContext.warn("Codigo duplicado");
			return null;
		}

		if (bemPermanente.getId() == null) {
			bemPermanenteRepositorio.adicionar(bemPermanente);
			message += "BemPermanente Cadastrada com Sucesso.";
		} else {
			bemPermanenteRepositorio.atualizar(bemPermanente);
			message += "BemPermanente Atualizada com Sucesso.";
		}
		facesContext.info(message);
		logger.info(message);
		bemPermanente = new BemPermanente();
		subCategoria = new SubCategoria();
		subCategoriaCodigo = null;
		categoria = new Categoria();
		categoriaCodigo = null;
		return null;
	}

	@Transacional
	public void recuperarBemPermanentePorId() {
		bemPermanente = bemPermanenteRepositorio.buscarPorId(bemPermanenteId);
	}

	// Remove um SubCategoria do banco de dados
	@Transacional
	public String remover(BemPermanente bemPermanente) {
		bemPermanente.setStatus(false);
		bemPermanenteRepositorio.atualizar(bemPermanente);
		this.bensPermanentes = null;
		this.bemPermanente = new BemPermanente();
		listarTabela();
		return null;
	}

	// Editar um SubCategoria
	public String editar(BemPermanente bemPermanente) {
		return "bemPermanente?bemPermanenteId=" + bemPermanente.getId();
	}

	public boolean bemPermanenteIdExiste() {
		if (this.bemPermanenteId == null)
			return false;
		return true;
	}

	public void categoriaSelecionada(SelectEvent event) {
		categoria = (Categoria) event.getObject();
		categoriaCodigo = categoria.getCodigo();
		bemPermanente.setCodigo(categoriaCodigo + "-00-00");
		requestContext.update("formBemPermanente:categoria");
	}

	@Transacional
	public void getCategoriaPorCodigo() {
		categoria = bemPermanenteRepositorio.getCategoriaPorCodigo(categoriaCodigo);
	}

	@Transacional
	public void getSubCategoriaPorCodigo() {
		subCategoria = bemPermanenteRepositorio.getSubCategoriaPorCodigo(subCategoriaCodigo);
		bemPermanente.setSubCategoria(subCategoria);
	}

	public void subCategoriaSelecionada(SelectEvent event) {
		subCategoria = (SubCategoria) event.getObject();
		categoria = subCategoria.getCategoria();
		categoriaCodigo = categoria.getCodigo();
		subCategoriaCodigo = subCategoria.getCodigo();
		bemPermanente.setCodigo(subCategoriaCodigo+"-00");
		bemPermanente.setSubCategoria(subCategoria);
		requestContext.update("formBemPermanente:subCategoria");
	}

	// Método usado para carregar objeto para o dialog
	public void selecionarBemPermanente(BemPermanente bemPermanente) {
		requestContext.closeDialog(bemPermanente);
	}

	public void inicializa() {
		recuperarBemPermanentePorId();
		subCategoriaCodigo = bemPermanente.getSubCategoria().getCodigo();
		getSubCategoriaPorCodigo();
		categoriaCodigo = subCategoria.getCategoria().getCodigo();
		getCategoriaPorCodigo();
	}

	public void constroiEstrutura() {

		String aux = bemPermanente.getCodigo();

		categoriaCodigo = aux.substring(0, 4);
		getCategoriaPorCodigo();
		subCategoriaCodigo = aux.substring(0, 7);
		getSubCategoriaPorCodigo();

	}

}
