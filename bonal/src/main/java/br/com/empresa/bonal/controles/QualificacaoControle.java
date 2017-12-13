package br.com.empresa.bonal.controles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.empresa.bonal.entidades.QualificacaoProfissional;
import br.com.empresa.bonal.repositorio.QualificacaoRepositorio;
import br.com.empresa.bonal.util.FacesContextUtil;

@ManagedBean
@ViewScoped
public class QualificacaoControle {

	private QualificacaoProfissional qualificacao = new QualificacaoProfissional();

	private Long qualificacaoId;

	// Atributos para Consulta
	private String qualificacaoNome = "";

	// Listas para Consulta
	private List<QualificacaoProfissional> qualificacoes;
	private List<QualificacaoProfissional> lista = new ArrayList<>();

	// Repositorio
	private QualificacaoRepositorio qualificacaoRepositorio;

	// Construtor chamando a classe repositorio
	public QualificacaoControle() {
		qualificacaoRepositorio = new QualificacaoRepositorio();
	}

	// Getters and Setters
	public QualificacaoProfissional getQualificacao() {
		return qualificacao;
	}

	// Adicionado para propriedade de contexto das tabelas do Primefaces
	public void setQualificacao(QualificacaoProfissional qualificacao) {
		this.qualificacao = qualificacao;
	}

	public Long getQualificacaoId() {
		return qualificacaoId;
	}

	public void setQualificacaoId(Long qualificacaoId) {
		this.qualificacaoId = qualificacaoId;
	}

	public String getQualificacaoNome() {
		return qualificacaoNome;
	}

	public void setQualificacaoNome(String qualificacaoNome) {
		this.qualificacaoNome = qualificacaoNome;
	}

	public List<QualificacaoProfissional> getQualificacoes() {
		return qualificacoes;
	}

	public List<QualificacaoProfissional> getLista() {
		return Collections.unmodifiableList(lista);
	}

	// retorna total de lista do banco de dados
	public Integer getTotalQualificacoes() {
		return lista.size();
	}

	// retorna total da lista filtrada
	public Integer getTotalQualificacoesConsulta() {
		return qualificacoes.size();
	}

	// ----------------- METODOS ----------------------
	@PostConstruct
	public void listarTabela() {
		if (this.qualificacoes == null) {
			lista = qualificacaoRepositorio.listarQualificacoes(qualificacaoNome);
			qualificacoes = new ArrayList<>(lista);
		}
		filtrarTabela();
	}

	public void filtrarTabela() {
		Stream<QualificacaoProfissional> filter = lista.stream();

		if (!qualificacaoNome.equals(null))
			filter = filter.filter(q -> (q.getTitulo().toLowerCase().contains(qualificacaoNome.toLowerCase().trim())));

		qualificacoes = filter.collect(Collectors.toList());
	}

	// Método chamado ao carregar pagina de consulta para popular tabela
	public String listar() {
		listarTabela();
		return null;
	}

	// Limpar tabela da consulta,
	public String limpar() {
		this.qualificacaoNome = "";
		filtrarTabela(); // Retorna a lista unmodifiablelist offline armazenada
		return null;
	}

	// Métodos que utilizam métodos do repositório
	public String salvar() {
		String message = "";
		if (qualificacao.getId() == null) {
			qualificacaoRepositorio.adicionar(qualificacao);
			message += "Qualificacao profissional cadastrada com Sucesso.";
		} else {
			qualificacaoRepositorio.atualizar(qualificacao);
			message += "Qualificacao profissional atualizada com Sucesso.";
		}
		new FacesContextUtil().info(message);
		System.out.println(message);
		qualificacao = new QualificacaoProfissional();
		return null;
	}

	public void recuperarQualificacaoPorId() {
		qualificacao = qualificacaoRepositorio.getQualificacao(qualificacaoId);
	}

	// Remove um objeto do banco de dados
	public void remover() {
		qualificacaoRepositorio.remover(qualificacao);
		qualificacoes = null;
		listarTabela();
		qualificacao = null;
	}

	public void remover(QualificacaoProfissional qualificacao) {
		this.qualificacao = qualificacao;
		remover();
	}

	// Editar objeto
	public String editar() {
		qualificacaoId = this.qualificacao.getId();
		return "qualificacao?qualificacaoId=" + qualificacaoId;
	}

	public String editar(QualificacaoProfissional qualificacao) {
		this.qualificacao = qualificacao;
		return editar();
	}

	public boolean qualificacaoIdExiste() {
		if (this.qualificacaoId == null)
			return false;
		return true;
	}
}
