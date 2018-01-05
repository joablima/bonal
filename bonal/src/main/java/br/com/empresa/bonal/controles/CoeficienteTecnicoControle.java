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

import br.com.empresa.bonal.entidades.Bem;
import br.com.empresa.bonal.entidades.CoeficienteTecnico;
import br.com.empresa.bonal.entidades.Produto;
import br.com.empresa.bonal.repositorio.CoeficienteTecnicoRepositorio;
import br.com.empresa.bonal.util.FacesContextUtil;
import br.com.empresa.bonal.util.enums.EnumBem;

@ManagedBean
@ViewScoped
public class CoeficienteTecnicoControle implements Serializable {
	private static final long serialVersionUID = 1L;

	private CoeficienteTecnico coeficienteTecnico = new CoeficienteTecnico();

	private Long coeficienteTecnicoId;

	private Long produtoId;
	private Long bemId;
	
	private EnumBem tipoBem;
	private Long categoriaId;

	// Atributos para Consulta
	private String coeficienteTecnicoNome = "";

	// Listas para Consulta
	private List<CoeficienteTecnico> coeficienteTecnicos;
	private List<CoeficienteTecnico> lista = new ArrayList<>();

	// Repositorio
	private CoeficienteTecnicoRepositorio coeficienteTecnicoRepositorio;

	// Construtor chamando a classe repositorio
	public CoeficienteTecnicoControle() {
		coeficienteTecnicoRepositorio = new CoeficienteTecnicoRepositorio();
	}

	// Getters and Setters
	public CoeficienteTecnico getCoeficienteTecnico() {
		return coeficienteTecnico;
	}

	// Adicionado para propriedade de contexto das tabelas do Primefaces
	public void setCoeficienteTecnico(CoeficienteTecnico coeficienteTecnico) {
		this.coeficienteTecnico = coeficienteTecnico;
	}

	public Long getCoeficienteTecnicoId() {
		return coeficienteTecnicoId;
	}

	public void setCoeficienteTecnicoId(Long coeficienteTecnicoId) {
		this.coeficienteTecnicoId = coeficienteTecnicoId;
	}

	public Long getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
	}

	public Long getBemId() {
		return bemId;
	}

	public void setBemId(Long bemId) {
		this.bemId = bemId;
	}

	public String getCoeficienteTecnicoNome() {
		return coeficienteTecnicoNome;
	}

	public void setCoeficienteTecnicoNome(String coeficienteTecnicoNome) {
		this.coeficienteTecnicoNome = coeficienteTecnicoNome;
	}
	
	public Long getCategoriaId() {
		return categoriaId;
	}
	
	public void setCategoriaId(Long categoriaId) {
		this.categoriaId = categoriaId;
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

	public List<CoeficienteTecnico> getCoeficienteTecnicos() {
		return coeficienteTecnicos;
	}

	public List<CoeficienteTecnico> getLista() {
		return Collections.unmodifiableList(lista);
	}

	// verificar importancia dos m�todos abaixo //verificar se est�o trocados??
	public Integer getTotalCoeficienteTecnicos() {
		return lista.size();
	}

	public Integer getTotalCoeficienteTecnicosConsulta() {
		return coeficienteTecnicos.size();
	}

	// ----------------- METODOS ----------------------
	@PostConstruct
	public void listarTabela() {
		if (this.coeficienteTecnicos == null) {
			lista = coeficienteTecnicoRepositorio.listarTodos();
			coeficienteTecnicos = new ArrayList<>(lista);
		}
		filtrarTabela();
	}

	public void filtrarTabela() {
		Stream<CoeficienteTecnico> stream = lista.stream();

		if (produtoId != null)
			stream = stream.filter(c -> (c.getProduto().getId().equals(produtoId)));

		if (bemId != null)
			stream = stream.filter(c -> (c.getBem().getId().equals(bemId)));

		coeficienteTecnicos = stream.collect(Collectors.toList());
	}

	// M�todo chamado ao carregar pagina de consulta para popular tabela
	public String listar() {
		listarTabela();
		return null;
	}

	// Limpar tabela da consulta
	public String limpar() {
		limparFiltros();
		this.coeficienteTecnicos = new ArrayList<>(this.lista);
		return null;
	}

	public void limparFiltros() {
		this.coeficienteTecnicoNome = "";
		this.produtoId = null;
		this.bemId = null;
	}

	public void salvar(CoeficienteTecnico b, Produto c, Bem u) {
		this.coeficienteTecnico = b;
		this.produtoId = c.getId();
		this.bemId = u.getId();
		salvar();
	}

	// M�todos que utilizam m�todos do reposit�rio
	public String salvar() {
		String message = "";
		if (coeficienteTecnico.getId() == null) {
			coeficienteTecnicoRepositorio.adicionar(coeficienteTecnico, bemId, produtoId);
			message += "CoeficienteTecnico Cadastrado com Sucesso.";
		} else {
			coeficienteTecnicoRepositorio.atualizar(coeficienteTecnico, bemId, produtoId);
			message += "CoeficienteTecnico Atualizado com Sucesso.";
		}
		new FacesContextUtil().info(message);
		System.out.println(message);
		coeficienteTecnico = new CoeficienteTecnico();
		return null;
	}

	public void recuperarCoeficienteTecnicoPorId() {
		coeficienteTecnico = coeficienteTecnicoRepositorio.buscarPorId(coeficienteTecnicoId);
	}

	// Remove um CoeficienteTecnico do banco de dados
	public void remover() {
		coeficienteTecnicoRepositorio.remover(coeficienteTecnico);
		coeficienteTecnicos = null;
		listarTabela();
		coeficienteTecnico = null;
	}

	public void remover(CoeficienteTecnico coeficienteTecnico) {
		this.coeficienteTecnico = coeficienteTecnico;
		remover();
	}

	// Editar um CoeficienteTecnico
	public String editar() {
		coeficienteTecnicoId = this.coeficienteTecnico.getId();
		return "coeficienteTecnico?coeficienteTecnicoId=" + coeficienteTecnicoId;
	}

	public String editar(CoeficienteTecnico coeficienteTecnico) {
		this.coeficienteTecnico = coeficienteTecnico;
		return editar();
	}

	public boolean CoeficienteTecnicoIdExiste() {
		if (this.coeficienteTecnicoId == null)
			return false;
		return true;
	}

}
