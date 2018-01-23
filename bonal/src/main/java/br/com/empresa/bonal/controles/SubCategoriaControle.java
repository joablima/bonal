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

import br.com.empresa.bonal.entidades.Categoria;
import br.com.empresa.bonal.entidades.SubCategoria;
import br.com.empresa.bonal.repositorio.SubCategoriaRepositorio;
import br.com.empresa.bonal.util.FacesContextUtil;
import br.com.empresa.bonal.util.tx.Transacional;

@Named
@ViewScoped
public class SubCategoriaControle implements Serializable {
	private static final long serialVersionUID = 1L;

	private SubCategoria subCategoria = new SubCategoria();
	
	private String categoriaCodigo;
	private Categoria categoria;

	private Long subCategoriaId;

	// Atributos para Consulta
	private String subCategoriaNome = "";

	private Boolean status = true;
	// Listas para Consulta
	private List<SubCategoria> subCategorias;
	private List<SubCategoria> lista = new ArrayList<>();
	

	@Inject
	private RequestContext requestContext;

	@Inject
	private SubCategoriaRepositorio subCategoriaRepositorio;

	@Inject
	private FacesContextUtil facesContext;

	@Inject
	private Logger logger;

	// Getters and Setters
	public SubCategoria getSubCategoria() {
		return subCategoria;
	}

	// Adicionado para propriedade de contexto das tabelas do Primefaces
	public void setSubCategoria(SubCategoria c) {
		this.subCategoria = c;
	}

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

	// ----------------- METODOS ----------------------
	@PostConstruct
	@Transacional
	public void listarTabela() {
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

	
	// M�todos que utilizam m�todos do reposit�rio
	@Transacional
	public String salvar() {
		String message = "";
		this.subCategoria.setStatus(true);
		
		Categoria c = subCategoriaRepositorio.getCategoriaPorCodigo(categoriaCodigo);
		if(c == null){
			facesContext.warn("Categoria inexistente, insira um codigo de categoria válido");
			return null;
		}
		subCategoria.setCategoria(c);

		if (subCategoria.getId() == null) {
			SubCategoria existe = subCategoriaRepositorio.codigoExiste(subCategoria);
			if (existe != null) {
				facesContext.warn("Já existe uma sub categoria registrada com esse código." + existe.resumo());
				return null;
			}
			
			subCategoriaRepositorio.adicionar(subCategoria);
			message += "Sub categoria Cadastrada com Sucesso.";
		} else {
			subCategoriaRepositorio.atualizar(subCategoria);
			message += "Sub categoria Atualizada com Sucesso.";
		}
		facesContext.info(message);
		logger.info(message);
		subCategoria = new SubCategoria();
		return null;
	}

	@Transacional
	public void recuperarSubCategoriaPorId() {
		subCategoria = subCategoriaRepositorio.buscarPorId(subCategoriaId);
	}

	// Remove um Categoria do banco de dados
	@Transacional
	public String remover(SubCategoria subCategoria) {
		subCategoria.setStatus(false);
		subCategoriaRepositorio.atualizar(subCategoria);
		this.subCategorias = null;
		this.subCategoria = new SubCategoria();
		listarTabela();
		return null;
	}

	// Editar um Categoria
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
		this.subCategoria.setCategoria(categoria);
		requestContext.update("formSubCategoria:categoria");
	}
	
	public void getCategoriaPorCodigo(){
		this.categoria =  subCategoriaRepositorio.getCategoriaPorCodigo(categoriaCodigo);
	}
	

}
