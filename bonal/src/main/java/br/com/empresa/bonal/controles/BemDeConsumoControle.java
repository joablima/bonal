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

	private String categoriaCodigo;
	private Categoria categoria;
	private String subCategoriaCodigo;
	private SubCategoria subCategoria;
	private String unidadeDeMedidaSigla;
	private UnidadeDeMedida unidadeDeMedida;

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
	@PostConstruct
	@Transacional
	public void listarTabela() {
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

	// M�todos que utilizam m�todos do reposit�rio
	@Transacional
	public String salvar() {
		String message = "";
		this.bemDeConsumo.setStatus(true);

		if (subCategoria == null) {
			facesContext.warn("SubCategoria inexistente, insira um codigo de categoria válido");
			return null;
		}
		if (!subCategoria.getCategoria().getTipo().toString().toLowerCase().equals("bem")) {
			facesContext.warn("SubCategoria inválida! Está associada com uma categoria de "
					+ subCategoria.getCategoria().getTipo().toString().toLowerCase()
					+ ". Não é possível inserir bens de consumo nela.");
			return null;
		}

		if (unidadeDeMedida == null) {
			facesContext.warn("Unidade de medida inexistente, insira um codigo válido");
			return null;
		}

		bemDeConsumo.setUnidadeDeMedida(unidadeDeMedida);

		if (bemDeConsumo.getId() == null) {
			ItemDeProducao existe = bemDeConsumoRepositorio.getItemDeProducaoPorCodigo(bemDeConsumo.getCodigo());
			if (existe != null) {
				facesContext.warn("Codigo duplicado");
				return null;
			}

			bemDeConsumoRepositorio.adicionar(bemDeConsumo);
			message += "BemDeConsumo Cadastrada com Sucesso.";
		} else {
			bemDeConsumoRepositorio.atualizar(bemDeConsumo);
			message += "BemDeConsumo Atualizada com Sucesso.";
		}
		facesContext.info(message);
		logger.info(message);
		bemDeConsumo = new BemDeConsumo();
		unidadeDeMedida = new UnidadeDeMedida();
		subCategoria = new SubCategoria();
		subCategoriaCodigo = null;
		unidadeDeMedidaSigla = null;
		return null;
	}

	@Transacional
	public void recuperarBemDeConsumoPorId() {
		bemDeConsumo = bemDeConsumoRepositorio.buscarPorId(bemDeConsumoId);
	}

	// Remove um SubCategoria do banco de dados
	@Transacional
	public String remover() {
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
		bemDeConsumo.setCodigo(categoriaCodigo + "-__-__");
		requestContext.update("formBemDeConsumo:categoria");
	}

	public void getCategoriaPorCodigo() {
		categoria = bemDeConsumoRepositorio.getCategoriaPorCodigo(categoriaCodigo);

	}

	public void subCategoriaSelecionada(SelectEvent event) {
		subCategoria = (SubCategoria) event.getObject();
		subCategoriaCodigo = subCategoria.getCodigo();
		bemDeConsumo.setSubCategoria(subCategoria);
		bemDeConsumo.setCodigo(subCategoriaCodigo);
		requestContext.update("formBemDeConsumo:subCategoria");
	}

	public void getSubCategoriaPorCodigo() {
		subCategoria = bemDeConsumoRepositorio.getSubCategoriaPorCodigo(subCategoriaCodigo);
	}

	public void unidadeDeMedidaSelecionada(SelectEvent event) {
		unidadeDeMedida = (UnidadeDeMedida) event.getObject();
		unidadeDeMedidaSigla = unidadeDeMedida.getSigla();
		bemDeConsumo.setUnidadeDeMedida(unidadeDeMedida);
		requestContext.update("formBemDeConsumo:unidadeDeMedida");
	}

	public void getUnidadeDeMedidaPorSigla() {
		unidadeDeMedida = bemDeConsumoRepositorio.getUnidadeDeMedidaPorSigla(unidadeDeMedidaSigla);
	}

	// Método usado para carregar objeto para o dialog
	public void selecionarBemDeConsumo(BemDeConsumo bemDeConsumo) {
		requestContext.closeDialog(bemDeConsumo);
	}

	public void inicializa() {
		recuperarBemDeConsumoPorId();
		subCategoria = bemDeConsumo.getSubCategoria();
		subCategoriaCodigo = subCategoria.getCodigo();
		unidadeDeMedida = bemDeConsumo.getUnidadeDeMedida();
		unidadeDeMedidaSigla = unidadeDeMedida.getSigla();
		constroiEstrutura();

	}

	public void constroiEstrutura() {

		String aux = bemDeConsumo.getCodigo();
		if (aux.length() >= 4) {
			categoriaCodigo = aux.substring(0, 4);
			getCategoriaPorCodigo();
			if (aux.length() >= 7) {
				subCategoriaCodigo = aux.substring(0, 7);
				getSubCategoriaPorCodigo();
			} else {
				subCategoriaCodigo = aux;
				getSubCategoriaPorCodigo();
			}
		} else {
			categoriaCodigo = aux;
			getCategoriaPorCodigo();
		}

	}

}
