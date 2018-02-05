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

import br.com.empresa.bonal.entidades.Categoria;
import br.com.empresa.bonal.entidades.SubCategoria;
import br.com.empresa.bonal.repositorio.SubCategoriaRepositorio;
import br.com.empresa.bonal.util.FacesContextUtil;
import br.com.empresa.bonal.util.logging.Logging;
import br.com.empresa.bonal.util.tx.Transacional;

@Named
@ViewScoped
public class SubCategoriaControle implements Serializable {
	private static final long serialVersionUID = 1L;

	private SubCategoria subCategoria = new SubCategoria();
	

	private String categoriaCodigo = "";
	private Categoria categoria = new Categoria();
	private String message="";
	private String filtroTipo = "";
	private Long subCategoriaId;

	// Atributos para Consulta
	private String subCategoriaNome = "";

	private Boolean status = true;
	// Listas para Consulta
	private List<SubCategoria> subCategorias;
	private List<SubCategoria> lista = new ArrayList<>();

	@Inject
	private SubCategoriaRepositorio subCategoriaRepositorio;

	@Inject
	private RequestContext requestContext;

	@Inject
	private FacesContextUtil facesContext;

	@Inject
	private Logger logger;

	//getter e setters
	public Long getSubCategoriaId() {
		return subCategoriaId;
	}

	public void setSubCategoriaId(Long subCategoriaId) {
		this.subCategoriaId = subCategoriaId;
	}

	public String getSubCategoriaNome() {
		return subCategoriaNome;
	}

	public void setSubCategoriaNome(String subCategoriaNome) {
		this.subCategoriaNome = subCategoriaNome;
	}

	public List<SubCategoria> getSubCategorias() {
		return subCategorias;
	}

	public List<SubCategoria> getLista() {
		return Collections.unmodifiableList(lista);
	}

	// verificar importancia dos m�todos abaixo //verificar se est�o trocados??
	public Integer getTotalSubCategorias() {
		return lista.size();
	}

	public Integer getTotalSubCategoriasConsulta() {
		return subCategorias.size();
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
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

	public String getFiltroTipo() {
		return filtroTipo;
	}

	public void setFiltroTipo(String filtroTipo) {
		this.filtroTipo = filtroTipo;
	}

	// ----------------- METODOS ----------------------
	
	@Transacional
	public void listarTabela() {
		if (this.subCategorias == null) {
			lista = subCategoriaRepositorio.listarTodos();
			subCategorias = new ArrayList<>(lista);
		}
		filtrarTabela();
	}
	@Logging
	public void preRenderView(ComponentSystemEvent event){
		System.out.println(filtroTipo);
		if (this.subCategorias == null) {
			lista = subCategoriaRepositorio.listarTodos();
			subCategorias = new ArrayList<>(lista);
		}
		filtrarTabela();
	}

	public void filtrarTabela() {
		Stream<SubCategoria> stream = lista.stream();

		stream = stream.filter(c -> (c.getNome().toLowerCase().contains(subCategoriaNome.toLowerCase().trim()))
				| (c.getCodigo().toLowerCase().contains(subCategoriaNome.toLowerCase().trim()))
				| (c.getCategoria().getNome().toLowerCase().contains(subCategoriaNome.toLowerCase().trim()))
				| c.getDescricao().toLowerCase().contains(subCategoriaNome.toLowerCase().trim()));
		
		if(!filtroTipo.equals("")){
			stream = stream.filter(c -> (c.getCategoria().getTipo().toString().equals(filtroTipo)));
		}

		if (status.equals(true))
			stream = stream.filter(c -> (c.getStatus().equals(status)));

		subCategorias = stream.collect(Collectors.toList());
	}

	// M�todo chamado ao carregar pagina de consulta para popular tabela
	public String listar() {
		listarTabela();
		return null;
	}

	// Limpar tabela da consulta
	public String limpar() {
		limparFiltros();
		this.subCategorias = new ArrayList<>(this.lista);
		return null;
	}

	public void limparFiltros() {
		this.subCategoriaNome = "";
	}
	
	@Transacional
	public String salvar(SubCategoria subCategoria){
		subCategoria.setStatus(true);
		subCategoriaRepositorio.atualizar(subCategoria);
		this.subCategorias = null;
		this.subCategoria = new SubCategoria();
		listarTabela();
		return null;
	}
	
	public String subCategoriaConsultar(){
		if(message.equals("")){
			subCategoria = new SubCategoria();
			categoria = new Categoria();
			categoriaCodigo = null;
			return "subCategoriaConsultar";
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
		this.subCategoria.setStatus(true);
		
		getCategoriaPorCodigo();
		
		if(categoria == null){
			message = "Categoria inexistente";
		}
		
		
		SubCategoria existe = subCategoriaRepositorio.getSubCategoriaPorCodigo(subCategoria.getCodigo());
		if (existe != null && (existe.getId()!=subCategoria.getId())) {
			message = "Codigo duplicado";
			
		}

		if (subCategoria.getId() == null) {
			subCategoriaRepositorio.adicionar(subCategoria);
		} else {
			subCategoriaRepositorio.atualizar(subCategoria);
		}
		logger.info(message);
		
		
	}

	@Transacional
	public void recuperarSubCategoriaPorId() {
		subCategoria = subCategoriaRepositorio.buscarPorId(subCategoriaId);
	}

	// Remove um SubCategoria do banco de dados
	@Transacional
	public String remover(SubCategoria subCategoria) {
		subCategoria.setStatus(false);
		subCategoriaRepositorio.atualizar(subCategoria);
		this.subCategorias = null;
		this.subCategoria = new SubCategoria();
		listarTabela();
		return null;
	}

	// Editar um SubCategoria
	public String editar(SubCategoria subCategoria) {
		return "subCategoria?subCategoriaId=" + subCategoria.getId();
	}

	public boolean subCategoriaIdExiste() {
		if (this.subCategoriaId == null)
			return false;
		return true;
	}

	public void categoriaSelecionada(SelectEvent event) {
		categoria = (Categoria) event.getObject();
		categoriaCodigo = categoria.getCodigo();
		subCategoria.setCodigo(categoriaCodigo + "-00-00");
		requestContext.update("formSubCategoria:categoria");
	}

	@Transacional
	public void getCategoriaPorCodigo() {
		categoria = subCategoriaRepositorio.getCategoriaPorCodigo(categoriaCodigo);
		subCategoria.setCategoria(categoria);
	}

	
	// Método usado para carregar objeto para o dialog
	public void selecionarSubCategoria(SubCategoria subCategoria) {
		requestContext.closeDialog(subCategoria);
	}

	public void inicializa() {
		recuperarSubCategoriaPorId();
		categoriaCodigo = subCategoria.getCategoria().getCodigo();
		getCategoriaPorCodigo();
	}

	public void constroiEstrutura() {

		String aux = subCategoria.getCodigo();

		categoriaCodigo = aux.substring(0, 4);
		getCategoriaPorCodigo();

	}

}
