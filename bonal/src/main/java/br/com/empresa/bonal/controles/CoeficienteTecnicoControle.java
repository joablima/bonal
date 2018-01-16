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

import org.apache.log4j.Logger;

import br.com.empresa.bonal.entidades.Bem;
import br.com.empresa.bonal.entidades.CoeficienteTecnico;
import br.com.empresa.bonal.entidades.Produto;
import br.com.empresa.bonal.repositorio.CoeficienteTecnicoRepositorio;
import br.com.empresa.bonal.util.FacesContextUtil;
import br.com.empresa.bonal.util.enums.EnumBem;
import br.com.empresa.bonal.util.tx.transacional;

@Named
@ViewScoped
public class CoeficienteTecnicoControle implements Serializable {
	private static final long serialVersionUID = 1L;

	final static Logger logger = Logger.getLogger(CoeficienteTecnicoControle.class);

	private CoeficienteTecnico coeficienteTecnico = new CoeficienteTecnico();

	private Long coeficienteTecnicoId;
	private Long produtoId;
	private Long bemId;

	private Boolean status = true;

	// Atributos para Consulta
	private String coeficienteTecnicoNome = "";

	// Listas para Consulta
	private List<CoeficienteTecnico> coeficienteTecnicos;
	private List<CoeficienteTecnico> lista = new ArrayList<>();

	private List<CoeficienteTecnico> coeficientesDoProduto;

	@Inject
	private CoeficienteTecnicoRepositorio coeficienteTecnicoRepositorio;

	@Inject
	private FacesContextUtil facesContext;

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

	@transacional
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

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	// ----------------- METODOS ----------------------
	@PostConstruct
	@transacional
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

		if (status.equals(true))
			stream = stream.filter(c -> (c.getStatus().equals(status)));

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
	@transacional
	public String salvar() {
		String message = "";

		this.coeficienteTecnico.setStatus(true);
		if (!produtoContemCoeficiente()) {
			coeficienteTecnicoRepositorio.adicionar(this.coeficienteTecnico, bemId, produtoId);
			facesContext.info("CoeficienteTecnico Cadastrado com Sucesso.");
		} else {
			facesContext.warn("CoeficienteTecnico já encontra-se registrado no produto.");
		}
		logger.info(message);
		this.coeficienteTecnico = new CoeficienteTecnico();
		return null;
	}

	@transacional
	public String recuperarCoeficienteTecnicoPorId() {
		this.coeficienteTecnico = coeficienteTecnicoRepositorio.buscarPorId(coeficienteTecnicoId);
		return null;
	}

	// Remove um CoeficienteTecnico do banco de dados
	@transacional
	public String remover(CoeficienteTecnico coeficiente) {
		coeficiente.setStatus(false);
		coeficienteTecnicoRepositorio.remover(coeficiente);
		this.coeficienteTecnicos = null;
		listarTabela();
		facesContext.info("Coeficiente Técnico do removido com sucesso.");
		logger.info("Coeficiente removido com sucesso");
		return null;
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
