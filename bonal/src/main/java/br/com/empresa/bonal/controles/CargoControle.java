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

import br.com.empresa.bonal.entidades.Cargo;
import br.com.empresa.bonal.repositorio.CargoRepositorio;
import br.com.empresa.bonal.util.FacesContextUtil;

@ManagedBean
@ViewScoped
public class CargoControle implements Serializable {
	private static final long serialVersionUID = 1L;

	private Cargo cargo = new Cargo();

	private Long cargoId;

	// Atributos para Consulta
	private String cargoNome = "";

	// Listas para Consulta
	private List<Cargo> cargos;
	private List<Cargo> lista = new ArrayList<>();

	// Repositorio
	private CargoRepositorio cargoRepositorio;

	// Construtor chamando a classe repositorio
	public CargoControle() {
		cargoRepositorio = new CargoRepositorio();
	}

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

	public List<Cargo> getCargos() {
		return cargos;
	}

	public List<Cargo> getLista() {
		return Collections.unmodifiableList(lista);
	}

	// verificar importancia dos métodos abaixo //verificar se estão trocados??
	public Integer getTotalCargos() {
		return lista.size();
	}

	public Integer getTotalCargosConsulta() {
		return cargos.size();
	}

	// ----------------- METODOS ----------------------
	@PostConstruct
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

		cargos = stream.collect(Collectors.toList());
	}

	// Método chamado ao carregar pagina de consulta para popular tabela
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

	// Métodos que utilizam métodos do repositório
	public String salvar() {
		String message = "";
		if (cargo.getId() == null) {
			cargoRepositorio.adicionar(cargo);
			message += "Cargo Cadastrado com Sucesso.";
		} else {
			cargoRepositorio.atualizar(cargo);
			message += "Cargo Atualizado com Sucesso.";
		}
		new FacesContextUtil().info(message);
		System.out.println(message);
		cargo = new Cargo();
		return null;
	}

	public void recuperarCargoPorId() {
		cargo = cargoRepositorio.buscarPorId(cargoId);
	}

	// Remove um cargo do banco de dados
	public void remover() {
		cargoRepositorio.remover(cargo);
		cargos = null;
		listarTabela();
		cargo = null;
	}

	public void remover(Cargo cargo) {
		this.cargo = cargo;
		remover();
	}

	// Editar um cargo
	public String editar() {
		cargoId = this.cargo.getId();
		return "cargo?cargoId=" + cargoId;
	}

	public String editar(Cargo cargo) {
		this.cargo = cargo;
		return editar();
	}

	public boolean cargoIdExiste() {
		if (this.cargoId == null)
			return false;
		return true;
	}
}
