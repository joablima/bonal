package br.com.empresa.bonal.controles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import br.com.empresa.bonal.entidades.Bem;
import br.com.empresa.bonal.entidades.Categoria;
import br.com.empresa.bonal.entidades.UnidadeDeMedida;
import br.com.empresa.bonal.repositorio.BemRepositorio;
import br.com.empresa.bonal.util.FacesContextUtil;
import br.com.empresa.bonal.util.enums.EnumBem;

@ManagedBean
@ViewScoped
public class BemControle implements Serializable {
	private static final long serialVersionUID = 1L;

	final static Logger logger = Logger.getLogger(BemControle.class);

	private Bem bem = new Bem();

	private Long bemId;

	// Atributos para Consulta
	private String bemNome = "";
	private EnumBem tipoBem;
	private Long categoriaId;
	private Long unidadeDeMedidaId;

	// Listas para Consulta
	private List<Bem> bens;
	private List<Bem> lista = new ArrayList<>();
	
	private Boolean status = true;
	
	// Repositorio
	private BemRepositorio bemRepositorio;

	// Construtor chamando a classe repositorio
	public BemControle() {
		bemRepositorio = new BemRepositorio();
	}

	// Getters and Setters
	public Bem getBem() {
		return bem;
	}

	// Adicionado para propriedade de contexto das tabelas do Primefaces
	public void setBem(Bem bem) {
		this.bem = bem;
	}

	public Long getBemId() {
		return bemId;
	}

	public void setBemId(Long bemId) {
		this.bemId = bemId;
	}

	public Long getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Long categoriaId) {
		this.categoriaId = categoriaId;
	}

	public Long getUnidadeDeMedidaId() {
		return unidadeDeMedidaId;
	}

	public void setUnidadeDeMedidaId(Long unidadeDeMedidaId) {
		this.unidadeDeMedidaId = unidadeDeMedidaId;
	}

	public String getBemNome() {
		return bemNome;
	}

	public void setBemNome(String bemNome) {
		this.bemNome = bemNome;
	}

	public EnumBem getTipoBem() {
		return tipoBem;
	}

	public void setTipoBem(EnumBem tipoBem) {
		this.tipoBem = tipoBem;
	}

	// ----- Carrega os Enums em Arrays -----
	public EnumBem[] getEnumBem() {
		return EnumBem.values();
	}

	public List<Bem> getBens() {
		return bens;
	}

	public List<Bem> getLista() {
		return Collections.unmodifiableList(lista);
	}

	// verificar importancia dos m�todos abaixo //verificar se est�o trocados??
	public Integer getTotalBens() {
		return lista.size();
	}

	public Integer getTotalBensConsulta() {
		return bens.size();
	}
	

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	// ----------------- METODOS ----------------------
	@PostConstruct
	public void listarTabela() {
		if (this.bens == null) {
			lista = bemRepositorio.listarTodos();
			bens = new ArrayList<>(lista);
		}
		filtrarTabela();
	}

	public void filtrarTabela() {
		Stream<Bem> stream = lista.stream();

		if (!bemNome.equals(null)) {
			stream = stream.filter(b -> (b.getNome().toLowerCase().contains(bemNome.toLowerCase().trim()))
					| (b.getCodigo().toLowerCase().contains(bemNome.toLowerCase().trim()))
					| b.getDescricao().toLowerCase().contains(bemNome.toLowerCase().trim()));
		}

		if (categoriaId != null)
			stream = stream.filter(b -> (b.getCategoria().getId().equals(categoriaId)));

		if (unidadeDeMedidaId != null)
			stream = stream.filter(b -> (b.getUnidadeDeMedida().getId().equals(unidadeDeMedidaId)));

		if (tipoBem != null) {
			stream = stream.filter(b -> (b.getTipo().equals(tipoBem)));
		}
		stream = stream.filter(b -> (b.getStatus().equals(status)));

		bens = stream.collect(Collectors.toList());
	}

	// M�todo chamado ao carregar pagina de consulta para popular tabela
	public String listar() {
		listarTabela();
		return null;
	}

	// Limpar tabela da consulta
	public String limpar() {
		limparFiltros();
		this.bens = new ArrayList<>(this.lista);
		return null;
	}

	public void limparFiltros() {
		this.bemNome = "";
		this.categoriaId = null;
		this.unidadeDeMedidaId = null;
	}

	public void salvar(Bem b, Categoria c, UnidadeDeMedida u) {
		this.bem = b;
		this.categoriaId = c.getId();
		this.unidadeDeMedidaId = u.getId();
		salvar();
	}

	// M�todos que utilizam m�todos do reposit�rio
	public String salvar() {
		String message = "";
		this.bem.setStatus(true);
		
		if (bem.getId() == null) {
			Bem existe = bemRepositorio.codigoExiste(bem);
			if (existe != null) {
				new FacesContextUtil().warn("Já existe um Bem registrado com esse código." + existe.resumo());
				return null;
			}
			bemRepositorio.adicionar(bem, categoriaId, unidadeDeMedidaId);
			message += "Bem Cadastrado com Sucesso.";
		} else {
			bemRepositorio.atualizar(bem, categoriaId, unidadeDeMedidaId);
			message += "Bem Atualizado com Sucesso.";
		}
		new FacesContextUtil().info(message);
		logger.info(message);
		bem = new Bem();
		return null;
	}

	public void recuperarBemPorId() {
		bem = bemRepositorio.buscarPorId(bemId);
	}

	// Remove um Bem do banco de dados
	public void remover() {
		this.bem.setStatus(false);
		
		bemRepositorio.remover(bem);
		bens = null;
		listarTabela();
		bem = null;
	}

	public void remover(Bem bem) {
		this.bem = bem;
		remover();
	}

	// Editar um Bem
	public String editar() {
		bemId = this.bem.getId();
		return "bem?bemId=" + bemId;
	}

	public String editar(Bem bem) {
		this.bem = bem;
		return editar();
	}

	public boolean BemIdExiste() {
		if (this.bemId == null)
			return false;
		return true;
	}

}
