package br.com.empresa.bonal.controles;

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
public class CargoControle {

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

	public Integer getTotalCargosConsultados() {
		return cargos.size();
	}

	// ----------------- METODOS ----------------------
	@PostConstruct
	public void listarTabela() {
		if (this.cargos == null) {
			lista = cargoRepositorio.listarCargos(cargoNome);
			cargos = new ArrayList<>(lista);
		}
		filtrarTabela();
	}

	public void filtrarTabela() {
		Stream<Cargo> filter = lista.stream()
				.filter(c -> (c.getNome().toLowerCase().contains(cargoNome.toLowerCase().trim())));

		cargos = filter.collect(Collectors.toList());
	}

	// Método chamado ao carregar pagina de consulta para popular tabela
	public String listar() {
		listarTabela();
		return null;
	}

	// Limpar tabela da consulta,
	public String limpar() {
		this.cargoNome = "";
		// listarCargos(); // Realiza nova consulta ao repositorio
		filtrarTabela(); // Retorna a lista unmodifiablelist offline armazenada
		return null;
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
		// System.out.println(cargo);
		cargo = new Cargo();
		return null;
	}

	public void recuperarCargoPorId() {
		cargo = cargoRepositorio.getCargo(cargoId);
	}

	// Remove um cargo do banco de dados
	public void remover() {
		cargoRepositorio.remover(cargo);
		cargos = null;
		listarTabela();
		cargo = null;
	}

	public void remover(Cargo c) {
		this.cargo = c;
		remover();
	}

	// Editar um cargo
	public String editar() {
		cargoId = this.cargo.getId();
		return "cargo?cargoid=" + cargoId;
	}

	public String editar(Cargo c) {
		this.cargo = c;
		return editar();
	}

	public boolean cargoIdExiste() {
		if (this.cargoId == null)
			return false;
		return true;
	}
}
