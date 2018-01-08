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
import br.com.empresa.bonal.entidades.CoeficienteTecnico;
import br.com.empresa.bonal.entidades.Produto;
import br.com.empresa.bonal.repositorio.CoeficienteTecnicoRepositorio;
import br.com.empresa.bonal.util.FacesContextUtil;
import br.com.empresa.bonal.util.enums.EnumBem;

@ManagedBean
@ViewScoped
public class CoeficienteTecnicoControle implements Serializable {
	private static final long serialVersionUID = 1L;

	final static Logger logger = Logger.getLogger(CoeficienteTecnicoControle.class);

	private CoeficienteTecnico coeficienteTecnico = new CoeficienteTecnico();

	private Long coeficienteTecnicoId;
	private Long produtoId;
	private Long bemId;

	// Atributos para Consulta
	private String coeficienteTecnicoNome = "";

	// Listas para Consulta
	private List<CoeficienteTecnico> coeficienteTecnicos;
	private List<CoeficienteTecnico> lista = new ArrayList<>();

	private List<CoeficienteTecnico> coeficientesDoProduto;

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

	// ----- Carrega os Enums em Arrays -----
	public EnumBem[] getEnumBem() {
		return EnumBem.values();
	}

	public List<CoeficienteTecnico> getCoeficientesDoProduto() {
		this.coeficientesDoProduto = coeficienteTecnicoRepositorio.buscarCoeficienteDoProduto(this.produtoId);
		return coeficientesDoProduto;
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
		if (this.produtoId != null)
			getCoeficientesDoProduto();

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
		if (!produtoContemCoeficiente()) {
			coeficienteTecnicoRepositorio.adicionar(this.coeficienteTecnico, bemId, produtoId);
			new FacesContextUtil().info("CoeficienteTecnico Cadastrado com Sucesso.");
		} else {
			new FacesContextUtil().warn("CoeficienteTecnico já encontra-se registrado no produto.");
		}
		logger.info(message);
		this.coeficienteTecnico = new CoeficienteTecnico();
		return null;
	}

	public void recuperarCoeficienteTecnicoPorId() {
		this.coeficienteTecnico = coeficienteTecnicoRepositorio.buscarPorId(coeficienteTecnicoId);
	}

	// Remove um CoeficienteTecnico do banco de dados
	public String remover() {
		coeficienteTecnicoRepositorio.remover(this.coeficienteTecnico);
		coeficienteTecnicos = null;
		listarTabela();
		coeficienteTecnico = new CoeficienteTecnico();
		new FacesContextUtil().info("Coeficiente Técnico do removido com sucesso.");
		logger.info("Coeficiente removido com sucesso");
		return null; 
	}

	public void remover(CoeficienteTecnico coeficienteTecnico) {
		this.coeficienteTecnico = coeficienteTecnico;
		remover();
	}

	public boolean CoeficienteTecnicoIdExiste() {
		if (this.coeficienteTecnicoId == null)
			return false;
		return true;
	}

	public void carregarDados(Long bem, Long produto) {
		this.produtoId = produto;
		this.bemId = bem;
	}

	public Boolean produtoContemCoeficiente() {
		for (CoeficienteTecnico coeficiente : coeficientesDoProduto) {
			if (coeficiente.getBem().getId().equals(this.bemId))
				return true;
		}
		return false;
	}

}
