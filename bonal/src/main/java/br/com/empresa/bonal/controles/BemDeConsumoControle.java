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

import br.com.empresa.bonal.entidades.BemDeConsumo;
import br.com.empresa.bonal.entidades.Categoria;
import br.com.empresa.bonal.entidades.ItemDeProducao;
import br.com.empresa.bonal.entidades.SubCategoria;
import br.com.empresa.bonal.entidades.UnidadeDeMedida;
import br.com.empresa.bonal.repositorio.BemDeConsumoRepositorio;
import br.com.empresa.bonal.util.FacesContextUtil;
import br.com.empresa.bonal.util.tx.Transacional;

@Named
@ViewScoped
public class BemDeConsumoControle implements Serializable {
	private static final long serialVersionUID = 1L;

	private BemDeConsumo bemDeConsumo = new BemDeConsumo();
	private String message ="";

	private String categoriaCodigo = "";
	private Categoria categoria = new Categoria();
	private String subCategoriaCodigo = "";
	private SubCategoria subCategoria = new SubCategoria();
	private String unidadeDeMedidaSigla = "";
	private UnidadeDeMedida unidadeDeMedida = new UnidadeDeMedida();

	private Long bemDeConsumoId;

	// Atributos para Consulta
	private String bemDeConsumoNome = "";
	private Boolean status = true;
	// Listas para Consulta
	private List<BemDeConsumo> bensDeConsumo;
	private List<BemDeConsumo> lista = new ArrayList<>();

	@Inject
	private BemDeConsumoRepositorio bemDeConsumoRepositorio;

	@Inject
	private RequestContext requestContext;

	@Inject
	private FacesContextUtil facesContext;

	@Inject
	private Logger logger;

	// Getters and Setters
	public BemDeConsumo getBemDeConsumo() {
		return bemDeConsumo;
	}

	// Adicionado para propriedade de contexto das tabelas do Primefaces
	public void setBemDeConsumo(BemDeConsumo c) {
		this.bemDeConsumo = c;
	}

	public Long getBemDeConsumoId() {
		return bemDeConsumoId;
	}

	public void setBemDeConsumoId(Long bemDeConsumoId) {
		this.bemDeConsumoId = bemDeConsumoId;
	}

	public String getBemDeConsumoNome() {
		return bemDeConsumoNome;
	}

	public void setBemDeConsumoNome(String bemDeConsumoNome) {
		this.bemDeConsumoNome = bemDeConsumoNome;
	}

	public List<BemDeConsumo> getBensDeConsumo() {
		return bensDeConsumo;
	}

	public List<BemDeConsumo> getLista() {
		return Collections.unmodifiableList(lista);
	}

	// verificar importancia dos m�todos abaixo //verificar se est�o trocados??
	public Integer getTotalBensDeConsumo() {
		return lista.size();
	}

	public Integer getTotalBensDeConsumoConsulta() {
		return bensDeConsumo.size();
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
	
	@Transacional
	public void listarTabela() {
		if (this.bensDeConsumo == null) {
			lista = bemDeConsumoRepositorio.listarTodos();
			bensDeConsumo = new ArrayList<>(lista);
		}
		filtrarTabela();
	}
	
	
	@Transacional
	public void preRenderView(ComponentSystemEvent event) {
		if (this.bensDeConsumo == null) {
			lista = bemDeConsumoRepositorio.listarTodos();
			bensDeConsumo = new ArrayList<>(lista);
		}
		filtrarTabela();
	}

	public void filtrarTabela() {
		Stream<BemDeConsumo> stream = lista.stream();

		stream = stream.filter(c -> (c.getNome().toLowerCase().contains(bemDeConsumoNome.toLowerCase().trim()))
				| (c.getCodigo().toLowerCase().contains(bemDeConsumoNome.toLowerCase().trim()))
				| (c.getSubCategoria().getNome().toLowerCase().contains(bemDeConsumoNome.toLowerCase().trim()))
				| c.getDescricao().toLowerCase().contains(bemDeConsumoNome.toLowerCase().trim()));

		if (status.equals(true))
			stream = stream.filter(c -> (c.getStatus().equals(status)));

		bensDeConsumo = stream.collect(Collectors.toList());
	}

	// M�todo chamado ao carregar pagina de consulta para popular tabela
	public String listar() {
		listarTabela();
		return null;
	}

	// Limpar tabela da consulta
	public String limpar() {
		limparFiltros();
		this.bensDeConsumo = new ArrayList<>(this.lista);
		return null;
	}

	public void limparFiltros() {
		this.bemDeConsumoNome = "";
	}
	@Transacional
	public String salvar(BemDeConsumo bemDeConsumo){
		bemDeConsumo.setStatus(true);
		bemDeConsumoRepositorio.atualizar(bemDeConsumo);
		this.bensDeConsumo = null;
		this.bemDeConsumo = new BemDeConsumo();
		listarTabela();
		return null;
	}
	
	public String bemDeConsumoConsultar(){
		if(message.equals("")){

			bemDeConsumo = new BemDeConsumo();
			unidadeDeMedida = new UnidadeDeMedida();
			unidadeDeMedidaSigla = null;
			subCategoria = new SubCategoria();
			subCategoriaCodigo = null;
			categoria = new Categoria();
			categoriaCodigo = null;
			return "bemDeConsumoConsultar";
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
		this.bemDeConsumo.setStatus(true);
		
		subCategoria = bemDeConsumoRepositorio.getSubCategoriaPorCodigo(bemDeConsumo.getSubCategoria().getCodigo());
		unidadeDeMedida = bemDeConsumoRepositorio.getUnidadeDeMedidaPorSigla(bemDeConsumo.getUnidadeDeMedida().getSigla());

		if (subCategoria == null) {
			message = "SubCategoria inexistente, insira um codigo de categoria válido";
		}
		if (!subCategoria.getCategoria().getTipo().toString().toLowerCase().equals("bem_consumo")) {
			message = "SubCategoria inválida! Está associada com uma categoria de "
					+ subCategoria.getCategoria().getTipo().toString().toLowerCase()
					+ ". Não é possível inserir bens de consumo nela.";
		}

		if (unidadeDeMedida == null) {
			message = "Unidade de medida inexistente, insira um codigo válido";
		}
		
		bemDeConsumo.setSubCategoria(subCategoria);
		bemDeConsumo.setUnidadeDeMedida(unidadeDeMedida);
		
		ItemDeProducao existe = bemDeConsumoRepositorio.getItemDeProducaoPorCodigo(bemDeConsumo.getCodigo());
		if (existe != null && (existe.getId()!=bemDeConsumo.getId())) {
			message = "Codigo duplicado";
			
		}

		if (bemDeConsumo.getId() == null) {
			bemDeConsumoRepositorio.adicionar(bemDeConsumo);
		} else {
			bemDeConsumoRepositorio.atualizar(bemDeConsumo);
		}
		logger.info(message);
		
	}

	@Transacional
	public void recuperarBemDeConsumoPorId() {
		bemDeConsumo = bemDeConsumoRepositorio.buscarPorId(bemDeConsumoId);
	}

	// Remove um SubCategoria do banco de dados
	@Transacional
	public String remover(BemDeConsumo bemDeConsumo) {
		bemDeConsumo.setStatus(false);
		bemDeConsumoRepositorio.atualizar(bemDeConsumo);
		this.bensDeConsumo = null;
		this.bemDeConsumo = new BemDeConsumo();
		listarTabela();
		return null;
	}

	// Editar um SubCategoria
	public String editar(BemDeConsumo bemDeConsumo) {
		return "bemDeConsumo?bemDeConsumoId=" + bemDeConsumo.getId();
	}

	public boolean bemDeConsumoIdExiste() {
		if (this.bemDeConsumoId == null)
			return false;
		return true;
	}

	public void categoriaSelecionada(SelectEvent event) {
		categoria = (Categoria) event.getObject();
		categoriaCodigo = categoria.getCodigo();
		bemDeConsumo.setCodigo(categoriaCodigo + "-00-00");
		requestContext.update("formBemDeConsumo:categoria");
	}

	@Transacional
	public void getCategoriaPorCodigo() {
		categoria = bemDeConsumoRepositorio.getCategoriaPorCodigo(categoriaCodigo);
	}

	@Transacional
	public void getUnidadeDeMedidaPorSigla() {
		unidadeDeMedida = bemDeConsumoRepositorio.getUnidadeDeMedidaPorSigla(unidadeDeMedidaSigla);
		bemDeConsumo.setUnidadeDeMedida(unidadeDeMedida);
	}

	@Transacional
	public void getSubCategoriaPorCodigo() {
		subCategoria = bemDeConsumoRepositorio.getSubCategoriaPorCodigo(subCategoriaCodigo);
		bemDeConsumo.setSubCategoria(subCategoria);
	}

	public void subCategoriaSelecionada(SelectEvent event) {
		subCategoria = (SubCategoria) event.getObject();
		categoria = subCategoria.getCategoria();
		categoriaCodigo = categoria.getCodigo();
		subCategoriaCodigo = subCategoria.getCodigo();
		bemDeConsumo.setCodigo(subCategoriaCodigo+"-00");
		bemDeConsumo.setSubCategoria(subCategoria);
		requestContext.update("formBemDeConsumo:subCategoria");
	}

	public void unidadeDeMedidaSelecionada(SelectEvent event) {
		unidadeDeMedida = (UnidadeDeMedida) event.getObject();
		unidadeDeMedidaSigla = unidadeDeMedida.getSigla();
		bemDeConsumo.setUnidadeDeMedida(unidadeDeMedida);
		requestContext.update("formBemDeConsumo:unidadeDeMedida");
	}

	// Método usado para carregar objeto para o dialog
	public void selecionarBemDeConsumo(BemDeConsumo bemDeConsumo) {
		requestContext.closeDialog(bemDeConsumo);
	}

	public void inicializa() {
		recuperarBemDeConsumoPorId();
		subCategoriaCodigo = bemDeConsumo.getSubCategoria().getCodigo();
		getSubCategoriaPorCodigo();
		categoriaCodigo = subCategoria.getCategoria().getCodigo();
		getCategoriaPorCodigo();
		unidadeDeMedidaSigla = bemDeConsumo.getUnidadeDeMedida().getSigla();
		getUnidadeDeMedidaPorSigla();

	}

	public void constroiEstrutura() {

		String aux = bemDeConsumo.getCodigo();

		categoriaCodigo = aux.substring(0, 4);
		getCategoriaPorCodigo();
		subCategoriaCodigo = aux.substring(0, 7);
		getSubCategoriaPorCodigo();

	}

}
