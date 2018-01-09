package br.com.empresa.bonal.controles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.empresa.bonal.entidades.Cargo;
import br.com.empresa.bonal.repositorio.CargoRepositorio;
import br.com.empresa.bonal.util.FacesContextUtil;
import br.com.empresa.bonal.util.enums.EnumPermissao;

@ManagedBean
@ViewScoped
public class CargoControle implements Serializable {
	private static final long serialVersionUID = 1L;

	final static Logger logger = Logger.getLogger(CargoControle.class);

	private Cargo cargo = new Cargo();
	private EnumPermissao permissao;

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

	public EnumPermissao getPermissao() {
		return permissao;
	}

	public void setPermissao(EnumPermissao permissao) {
		this.permissao = permissao;
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

	public void salvar(Cargo c) {
		this.cargo = c;
		salvar();
	}

	// M�todos que utilizam m�todos do reposit�rio
	public String salvar() {
		String message = "";
		this.cargo.setStatus(true);
		if (cargo.getId() == null) {
			Cargo existe = cargoRepositorio.cargoExiste(cargo);
			if (existe != null) {
				new FacesContextUtil().warn("Já existe esse cargo registrado.");
				return null;
			}
			cargoRepositorio.adicionar(cargo);
			message += "Cargo Cadastrado com Sucesso.";
		} else {
			cargoRepositorio.atualizar(cargo);
			message += "Cargo Atualizado com Sucesso.";
		}
		new FacesContextUtil().info(message);
		logger.info(message);
		cargo = new Cargo();
		return null;
	}

	public void recuperarCargoPorId() {
		cargo = cargoRepositorio.buscarPorId(cargoId);
	}

	// Remove um cargo do banco de dados
	public void remover() {
		this.cargo.setStatus(false);
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

	public void cargoDialogShow() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("modal", true);
		options.put("draggable", false);
		options.put("resizable", false);
		options.put("contentWidth", 500);
		options.put("contentHeight", 500);
//		options.put("includeViewParams", true);

		RequestContext.getCurrentInstance().openDialog("cargoDialog", options, null);
	}

	public void cargoDialogReturn(SelectEvent event) {
		Object objeto = event.getObject();
		new FacesContextUtil().info("Cargo " + objeto + " cadastrado com sucesso");
	}

	public void salvarDialog() {
        RequestContext.getCurrentInstance().closeDialog(salvar());
    }

    public void fecharDialog() {
        RequestContext.getCurrentInstance().closeDialog(0);
    }
}
