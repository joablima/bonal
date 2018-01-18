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

import br.com.empresa.bonal.entidades.Cargo;
import br.com.empresa.bonal.repositorio.CargoRepositorio;
import br.com.empresa.bonal.util.FacesContextUtil;
import br.com.empresa.bonal.util.enums.EnumPermissao;
import br.com.empresa.bonal.util.tx.Transacional;

@Named
@ViewScoped
public class CargoControle implements Serializable {
	private static final long serialVersionUID = 1L;

	private Cargo cargo = new Cargo();

	private EnumPermissao permissao;

	private Long cargoId;

	private Boolean status = true;

	// Atributos para Consulta
	private String cargoNome = "";

	// Listas para Consulta
	private List<Cargo> cargos;
	private List<Cargo> lista = new ArrayList<>();

	@Inject
	private CargoRepositorio cargoRepositorio;

	@Inject
	private FacesContextUtil facesContext;

	@Inject
	private RequestContext requestContext;

	@Inject
	private Logger logger;

	// Getters and Setters
	public Cargo getCargo() {
		return cargo;
	}

	// Adicionado para propriedade de contexto das tabelas do Primefaces
	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
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

	public EnumPermissao getPermissao() {
		return permissao;
	}

	public void setPermissao(EnumPermissao permissao) {
		this.permissao = permissao;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	// ----- Carrega os Enums em Arrays -----
	public EnumPermissao[] getEnumPermissao() {
		return EnumPermissao.values();
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

		stream = stream.filter(c -> (c.getNome().toLowerCase().contains(cargoNome.toLowerCase().trim())));

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

	

	// M�todos que utilizam m�todos do reposit�rio
	@Transacional
	public String salvar() {
		String messages = "";
		this.cargo.setStatus(true);
		if (cargo.getId() == null) {
			Cargo existe = cargoRepositorio.cargoExiste(cargo);
			if (existe != null) {
				facesContext.warn("Já existe esse cargo registrado.");
				return null;
			}
			cargoRepositorio.adicionar(cargo);
			messages += "Cargo Cadastrado com Sucesso.";
		} else {
			cargoRepositorio.atualizar(cargo);
			messages += "Cargo Atualizado com Sucesso.";
		}
		requestContext.scrollTo("messages");
		facesContext.info(messages);
		logger.info(messages);
		cargo = new Cargo();
		return null;
	}

	@Transacional
	public void recuperarCargoPorId() {
		cargo = cargoRepositorio.buscarPorId(cargoId);
	}

	// Remove um cargo do banco de dados
	@Transacional
	public String remover(Cargo cargo) {
		cargo.setStatus(false);
		cargoRepositorio.remover(cargo);
		this.cargos = null;
		listarTabela();
		return null;
	}

	// Editar um cargo
	public String editar() {
		return "cargo?cargoId=" + this.cargo.getId();
	}

	public boolean cargoIdExiste() {
		if (this.cargoId == null)
			return false;
		return true;
	}

	// Método usado para carregar objeto para o dialog
	public void selecionarCargo(Cargo cargo) {
		requestContext.closeDialog(cargo);
	}
}
