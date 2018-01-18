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

import br.com.empresa.bonal.entidades.ItemDeProducao;
import br.com.empresa.bonal.entidades.Servico;
import br.com.empresa.bonal.entidades.SubCategoria;
import br.com.empresa.bonal.entidades.UnidadeDeMedida;
import br.com.empresa.bonal.repositorio.ServicoRepositorio;
import br.com.empresa.bonal.util.FacesContextUtil;
import br.com.empresa.bonal.util.tx.Transacional;

@Named
@ViewScoped
public class ServicoControle implements Serializable {
	private static final long serialVersionUID = 1L;

	private Servico servico = new Servico();
	
	private String subCategoriaCodigo;
	private String unidadeDeMedidaCodigo;

	private Long servicoId;

	// Atributos para Consulta
	private String servicoNome = "";

	private Boolean status = true;
	// Listas para Consulta
	private List<Servico> servicos;
	private List<Servico> lista = new ArrayList<>();

	@Inject
	private ServicoRepositorio servicoRepositorio;

	@Inject
	private FacesContextUtil facesContext;

	@Inject
	private Logger logger;

	// Getters and Setters
	public Servico getServico() {
		return servico;
	}

	// Adicionado para propriedade de contexto das tabelas do Primefaces
	public void setServico(Servico c) {
		this.servico = c;
	}

	public Long getServicoId() {
		return servicoId;
	}

	public void setServicoId(Long servicoId) {
		this.servicoId = servicoId;
	}

	public String getServicoNome() {
		return servicoNome;
	}

	public void setServicoNome(String servicoNome) {
		this.servicoNome = servicoNome;
	}

	

	public List<Servico> getServicos() {
		return servicos;
	}


	public List<Servico> getLista() {
		return Collections.unmodifiableList(lista);
	}

	// verificar importancia dos m�todos abaixo //verificar se est�o trocados??
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

	public String getSubCategoriaCodigo() {
		return subCategoriaCodigo;
	}

	public void setSubCategoriaCodigo(String subCategoriaCodigo) {
		this.subCategoriaCodigo = subCategoriaCodigo;
	}
	
	
	public String getUnidadeDeMedidaCodigo() {
		return unidadeDeMedidaCodigo;
	}

	public void setUnidadeDeMedidaCodigo(String unidadeDeMedidaCodigo) {
		this.unidadeDeMedidaCodigo = unidadeDeMedidaCodigo;
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

		stream = stream.filter(c -> (c.getNome().toLowerCase().contains(servicoNome.toLowerCase().trim()))
				| (c.getCodigo().toLowerCase().contains(servicoNome.toLowerCase().trim()))
				| (c.getSubCategoria().getNome().toLowerCase().contains(servicoNome.toLowerCase().trim()))
				| c.getDescricao().toLowerCase().contains(servicoNome.toLowerCase().trim()));

		if (status.equals(true))
			stream = stream.filter(c -> (c.getStatus().equals(status)));

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
		this.servicoNome = "";
	}

	

	// M�todos que utilizam m�todos do reposit�rio
	@Transacional
	public String salvar() {
		String message = "";
		this.servico.setStatus(true);
		
		SubCategoria c = servicoRepositorio.getSubCategoriaPorCodigo(subCategoriaCodigo);
		if(c == null){
			facesContext.warn("SubCategoria inexistente, insira um codigo de categoria válido");
			return null;
		}
		if(c.getCategoria().getTipo().toString().toLowerCase().equals("bem")){
			facesContext.warn("SubCategoria inválida! Está associada com uma categoria de bens. Não é possível inserir servicos nela.");
			return null;
		}
		
		UnidadeDeMedida u = servicoRepositorio.getUnidadeDeMedidaPorCodigo(subCategoriaCodigo);
		if(u == null){
			facesContext.warn("Unidade de medida inexistente, insira um codigo válido");
			return null;
		}
		
		servico.setSubCategoria(c);

		if (servico.getId() == null) {
			ItemDeProducao existe = servicoRepositorio.getItemDeProducaoPorCodigo(servico.getCodigo());
			if (existe != null) {
				facesContext.warn("Codigo duplicado");
				return null;
			}
			
			servicoRepositorio.adicionar(servico);
			message += "Servico Cadastrada com Sucesso.";
		} else {
			servicoRepositorio.atualizar(servico);
			message += "Servico Atualizada com Sucesso.";
		}
		facesContext.info(message);
		logger.info(message);
		servico = new Servico();
		return null;
	}

	@Transacional
	public void recuperarServicoPorId() {
		servico = servicoRepositorio.buscarPorId(servicoId);
	}

	// Remove um SubCategoria do banco de dados
	@Transacional
	public String remover() {
		servico.setStatus(false);
		servicoRepositorio.atualizar(servico);
		this.servicos = null;
		this.servico = new Servico();
		listarTabela();
		return null;
	}

	// Editar um SubCategoria
	public String editar() {
		return "servico?servicoId=" + this.servico.getId();
	}

	public boolean servicoIdExiste() {
		if (this.servicoId == null)
			return false;
		return true;
	}

}
