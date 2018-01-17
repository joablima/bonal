package br.com.empresa.bonal.controles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import br.com.empresa.bonal.entidades.Categoria;
import br.com.empresa.bonal.entidades.Servico;
import br.com.empresa.bonal.entidades.UnidadeDeMedida;
import br.com.empresa.bonal.repositorio.CategoriaRepositorio;
import br.com.empresa.bonal.repositorio.ServicoRepositorio;
import br.com.empresa.bonal.repositorio.UnidadeDeMedidaRepositorio;
import br.com.empresa.bonal.util.FacesContextUtil;
import br.com.empresa.bonal.util.tx.Transacional;

public class ServicoControle {

	private Servico servico = new Servico();
	private Long servicoId;

	// Atributos para Consulta
	private String campoPesquisa = "";
	private Long categoriaId;
	private Long unidadeDeMedidaId;

	// Listas para Consulta
	private List<Servico> servicos;
	private List<Servico> lista = new ArrayList<>();

	private Boolean status = true;

	@Inject
	private ServicoRepositorio servicoRepositorio;

	@Inject
	private FacesContextUtil facesContext;

	@Inject
	private UnidadeDeMedidaRepositorio unidadeRepositorio;

	@Inject
	private CategoriaRepositorio categoriaRepositorio;

	// Getters and Setters
	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public Long getServicoId() {
		return servicoId;
	}

	public void setServicoId(Long servicoId) {
		this.servicoId = servicoId;
	}

	public String getCampoPesquisa() {
		return campoPesquisa;
	}

	public void setCampoPesquisa(String campoPesquisa) {
		this.campoPesquisa = campoPesquisa;
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

	public List<Servico> getServicos() {
		return servicos;
	}

	public List<Servico> getLista() {
		return Collections.unmodifiableList(lista);
	}
	// ----- Carrega os Enums em Arrays -----

	public Integer getTotalServicos() {
		return lista.size();
	}

	public Integer getTotalServicosConsulta() {
		return servicos.size();
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	// ----------------- METODOS ----------------------
	@PostConstruct
	@Transacional
	public void listarTabela() {
		if (this.servicos == null) {
			lista = servicoRepositorio.listarTodos();
			servicos = new ArrayList<>(lista);
		}
		filtrarTabela();
	}

	public void filtrarTabela() {
		Stream<Servico> stream = lista.stream();

		if (!campoPesquisa.equals(null))
			stream = stream.filter(b -> (b.getNome().toLowerCase().contains(campoPesquisa.toLowerCase().trim()))
					| (b.getCodigo().toLowerCase().contains(campoPesquisa.toLowerCase().trim()))
					| b.getDescricao().toLowerCase().contains(campoPesquisa.toLowerCase().trim())
					| b.getUnidadeDeMedida().getNome().toLowerCase().contains(campoPesquisa.toLowerCase().trim())
					| b.getCategoria().getNome().toLowerCase().contains(campoPesquisa.toLowerCase().trim()));

		if (categoriaId != null)
			stream = stream.filter(b -> (b.getCategoria().getId().equals(categoriaId)));

		if (unidadeDeMedidaId != null)
			stream = stream.filter(b -> (b.getUnidadeDeMedida().getId().equals(unidadeDeMedidaId)));

		if (status.equals(true))
			stream = stream.filter(b -> (b.getStatus().equals(status)));

		servicos = stream.collect(Collectors.toList());
	}

	// M�todo chamado ao carregar pagina de consulta para popular tabela
	public String listar() {
		listarTabela();
		return null;
	}

	// Limpar tabela da consulta
	public String limpar() {
		limparFiltros();
		this.servicos = new ArrayList<>(this.lista);
		return null;
	}

	public void limparFiltros() {
		this.campoPesquisa = "";
		this.categoriaId = null;
		this.unidadeDeMedidaId = null;
	}

	public void salvar(Servico b, Categoria c, UnidadeDeMedida u) {
		this.servico = b;
		this.categoriaId = c.getId();
		this.unidadeDeMedidaId = u.getId();
		salvar();
	}

	public void salvar(Servico Servico) {
		this.servico = Servico;
		this.categoriaId = Servico.getCategoria().getId();
		this.unidadeDeMedidaId = Servico.getUnidadeDeMedida().getId();
		salvar();
	}

	// M�todos que utilizam m�todos do reposit�rio
	@Transacional
	public String salvar() {
		String message = "";
		this.servico.setStatus(true);

		Categoria categoria = categoriaRepositorio.buscarPorId(categoriaId);
		UnidadeDeMedida unidade = unidadeRepositorio.buscarPorId(unidadeDeMedidaId);

		servico.setCategoria(categoria);
		servico.setUnidadeDeMedida(unidade);

		if (servico.getId() == null) {
			Servico existe = servicoRepositorio.codigoExiste(servico);
			if (existe != null) {
				facesContext.warn("Já existe um Servico registrado com esse código.");
				return null;
			}
			servicoRepositorio.adicionar(servico);
			message += "Servico Cadastrado com Sucesso.";
		} else {
			servicoRepositorio.atualizar(servico);
			message += "Servico Atualizado com Sucesso.";
		}
		facesContext.info(message);
		servico = new Servico();
		return null;
	}

	public void recuperarServicoPorId() {
		servico = servicoRepositorio.buscarPorId(servicoId);
	}

	// Remove um Servico do banco de dados
	@Transacional
	public String remover(Servico Servico) {
		Servico.setStatus(false);
		servicoRepositorio.remover(Servico);
		this.servicos = null;
		listarTabela();
		return null;
	}

	// Editar um Servico
	public String editar(Servico Servico) {
		return "Servico?ServicoId=" + Servico.getId();
	}

	public boolean ServicoIdExiste() {
		if (this.servicoId == null)
			return false;
		return true;
	}
}
