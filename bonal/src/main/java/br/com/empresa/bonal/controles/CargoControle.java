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

import br.com.empresa.bonal.entidades.Cargo;
import br.com.empresa.bonal.entidades.Categoria;
import br.com.empresa.bonal.entidades.ItemDeProducao;
import br.com.empresa.bonal.entidades.SubCategoria;
import br.com.empresa.bonal.repositorio.CargoRepositorio;
import br.com.empresa.bonal.util.FacesContextUtil;
import br.com.empresa.bonal.util.enums.EnumPermissao;
import br.com.empresa.bonal.util.tx.Transacional;

@Named
@ViewScoped
public class CargoControle implements Serializable {
	private static final long serialVersionUID = 1L;

	private Cargo cargo = new Cargo();

	private String categoriaCodigo = "";
	private Categoria categoria = new Categoria();
	private String subCategoriaCodigo = "";
	private SubCategoria subCategoria = new SubCategoria();

	private Long cargoId;

	// Atributos para Consulta
	private String cargoNome = "";

	private Boolean status = true;
	// Listas para Consulta
	private List<Cargo> cargos;
	private List<Cargo> lista = new ArrayList<>();

	@Inject
	private CargoRepositorio cargoRepositorio;

	@Inject
	private RequestContext requestContext;

	@Inject
	private FacesContextUtil facesContext;

	@Inject
	private Logger logger;

	// Getters and Setters
	public Cargo getCargo() {
		return cargo;
	}

	// Adicionado para propriedade de contexto das tabelas do Primefaces
	public void setCargo(Cargo c) {
		this.cargo = c;
	}

	public Long getCargoId() {
		return cargoId;
	}

	public void setCargoId(Long cargoId) {
		this.cargoId = cargoId;
	}

	public String getCargoNome() {
		return cargoNome;
	}

	public void setCargoNome(String cargoNome) {
		this.cargoNome = cargoNome;
	}

	public List<Cargo> getCargos() {
		return cargos;
	}

	public List<Cargo> getLista() {
		return Collections.unmodifiableList(lista);
	}

	// verificar importancia dos m�todos abaixo //verificar se est�o trocados??
	public Integer getTotalCargos() {
		return lista.size();
	}

	public Integer getTotalCargosConsulta() {
		return cargos.size();
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

	public SubCategoria getSubCategoria() {
		return subCategoria;
	}

	public void setSubCategoria(SubCategoria subCategoria) {
		this.subCategoria = subCategoria;
	}

	// ----- Carrega os Enums em Arrays -----
	public EnumPermissao[] getEnumPermissao() {
		return EnumPermissao.values();
	}

	// ----------------- METODOS ----------------------
	@PostConstruct
	@Transacional
	public void listarTabela() {
		if (this.cargos == null) {
			lista = cargoRepositorio.listarTodos();
			cargos = new ArrayList<>(lista);
		}
		filtrarTabela();
	}

	public void filtrarTabela() {
		Stream<Cargo> stream = lista.stream();

		stream = stream.filter(c -> (c.getNome().toLowerCase().contains(cargoNome.toLowerCase().trim()))
				| (c.getCodigo().toLowerCase().contains(cargoNome.toLowerCase().trim()))
				| (c.getSubCategoria().getNome().toLowerCase().contains(cargoNome.toLowerCase().trim()))
				| c.getDescricao().toLowerCase().contains(cargoNome.toLowerCase().trim()));

		if (status.equals(true))
			stream = stream.filter(c -> (c.getStatus().equals(status)));

		cargos = stream.collect(Collectors.toList());
	}

	// M�todo chamado ao carregar pagina de consulta para popular tabela
	public String listar() {
		listarTabela();
		return null;
	}

	// Limpar tabela da consulta
	public String limpar() {
		limparFiltros();
		this.cargos = new ArrayList<>(this.lista);
		return null;
	}

	public void limparFiltros() {
		this.cargoNome = "";
	}

	@Transacional
	public String salvar(Cargo cargo) {
		cargo.setStatus(true);
		cargoRepositorio.atualizar(cargo);
		this.cargos = null;
		this.cargo = new Cargo();
		listarTabela();
		return null;
	}

	// M�todos que utilizam m�todos do reposit�rio
	@Transacional
	public String salvar() {
		String message = "";
		this.cargo.setStatus(true);

		subCategoria = cargoRepositorio.getSubCategoriaPorCodigo(cargo.getSubCategoria().getCodigo());

		if (subCategoria == null) {
			facesContext.warn("SubCategoria inexistente, insira um codigo de categoria válido");
			return null;
		}
		if (!subCategoria.getCategoria().getTipo().toString().toLowerCase().equals("mao_de_obra")) {
			facesContext.warn("SubCategoria inválida! Está associada com uma categoria de "
					+ subCategoria.getCategoria().getTipo().toString().toLowerCase()
					+ ". Não é possível inserir cargos nela.");
			return null;
		}

		cargo.setSubCategoria(subCategoria);

		ItemDeProducao existe = cargoRepositorio.getItemDeProducaoPorCodigo(cargo.getCodigo());
		if (existe != null && (existe.getId() != cargo.getId())) {
			facesContext.warn("Codigo duplicado");
			return null;
		}

		if (cargo.getId() == null) {
			cargoRepositorio.adicionar(cargo);
			message += "Cargo Cadastrada com Sucesso.";
		} else {
			cargoRepositorio.atualizar(cargo);
			message += "Cargo Atualizada com Sucesso.";
		}
		facesContext.info(message);
		logger.info(message);
		cargo = new Cargo();
		subCategoria = new SubCategoria();
		subCategoriaCodigo = null;
		categoria = new Categoria();
		categoriaCodigo = null;
		return null;
	}

	@Transacional
	public void recuperarCargoPorId() {
		cargo = cargoRepositorio.buscarPorId(cargoId);
	}

	// Remove um SubCategoria do banco de dados
	@Transacional
	public String remover(Cargo cargo) {
		cargo.setStatus(false);
		cargoRepositorio.atualizar(cargo);
		this.cargos = null;
		this.cargo = new Cargo();
		listarTabela();
		return null;
	}

	// Editar um SubCategoria
	public String editar(Cargo cargo) {
		return "cargo?cargoId=" + cargo.getId();
	}

	public boolean cargoIdExiste() {
		if (this.cargoId == null)
			return false;
		return true;
	}

	public void categoriaSelecionada(SelectEvent event) {
		categoria = (Categoria) event.getObject();
		categoriaCodigo = categoria.getCodigo();
		cargo.setCodigo(categoriaCodigo + "-00-00");
		requestContext.update("formCargo:categoria");
	}

	@Transacional
	public void getCategoriaPorCodigo() {
		categoria = cargoRepositorio.getCategoriaPorCodigo(categoriaCodigo);
	}

	@Transacional
	public void getSubCategoriaPorCodigo() {
		subCategoria = cargoRepositorio.getSubCategoriaPorCodigo(subCategoriaCodigo);
		cargo.setSubCategoria(subCategoria);
	}

	public void subCategoriaSelecionada(SelectEvent event) {
		subCategoria = (SubCategoria) event.getObject();
		categoria = subCategoria.getCategoria();
		categoriaCodigo = categoria.getCodigo();
		subCategoriaCodigo = subCategoria.getCodigo();
		cargo.setCodigo(subCategoriaCodigo + "-00");
		cargo.setSubCategoria(subCategoria);
		requestContext.update("formCargo:subCategoria");
	}

	// Método usado para carregar objeto para o dialog
	public void selecionarCargo(Cargo cargo) {
		requestContext.closeDialog(cargo);
	}

	public void inicializa() {
		recuperarCargoPorId();
		subCategoriaCodigo = cargo.getSubCategoria().getCodigo();
		getSubCategoriaPorCodigo();
		categoriaCodigo = subCategoria.getCategoria().getCodigo();
		getCategoriaPorCodigo();
	}

	public void constroiEstrutura() {

		String aux = cargo.getCodigo();

		categoriaCodigo = aux.substring(0, 4);
		getCategoriaPorCodigo();
		subCategoriaCodigo = aux.substring(0, 7);
		getSubCategoriaPorCodigo();

	}

}
