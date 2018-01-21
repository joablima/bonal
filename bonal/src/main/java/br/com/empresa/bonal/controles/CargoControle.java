package br.com.empresa.bonal.controles;

import java.io.FileInputStream;
import java.io.IOException;
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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

import br.com.empresa.bonal.entidades.Cargo;
import br.com.empresa.bonal.entidades.ItemDeProducao;
import br.com.empresa.bonal.entidades.SubCategoria;
import br.com.empresa.bonal.entidades.UnidadeDeMedida;
import br.com.empresa.bonal.repositorio.CargoRepositorio;
import br.com.empresa.bonal.util.FacesContextUtil;
import br.com.empresa.bonal.util.enums.EnumPermissao;
import br.com.empresa.bonal.util.tx.Transacional;

@Named
@ViewScoped
public class CargoControle implements Serializable {
	private static final long serialVersionUID = 1L;

	private Cargo cargo = new Cargo();

	private String subCategoriaCodigo;

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

	// M�todos que utilizam m�todos do reposit�rio
	@Transacional
	public String salvar() {
		String message = "";
		this.cargo.setStatus(true);

		SubCategoria c = cargoRepositorio.getSubCategoriaPorCodigo(subCategoriaCodigo);
		if (c == null) {
			facesContext.warn("SubCategoria inexistente, insira um codigo de sub categoria válido");
			return null;
		}
		if (!c.getCategoria().getTipo().toString().toLowerCase().equals("mao_de_obra")) {
			facesContext.warn("SubCategoria inválida! Está associada com uma categoria de "
					+ c.getCategoria().getTipo().toString().toLowerCase() + ". Não é possível inserir cargos nela.");
			return null;
		}
		cargo.setSubCategoria(c);

		if (cargo.getId() == null) {
			ItemDeProducao existe = cargoRepositorio.getItemDeProducaoPorCodigo(cargo.getCodigo());
			if (existe != null) {
				facesContext.warn("Codigo duplicado");
				return null;
			}

			cargoRepositorio.adicionar(cargo);
			message += "Cargo cadastrado com sucesso.";
		} else {
			cargoRepositorio.atualizar(cargo);
			message += "Cargo atualizado com sucesso.";
		}
		facesContext.info(message);
		logger.info(message);
		cargo = new Cargo();
		return null;
	}

	@Transacional
	public void recuperarCargoPorId() {
		cargo = cargoRepositorio.buscarPorId(cargoId);
	}

	// Remove um SubCategoria do banco de dados
	@Transacional
	public String remover() {
		cargo.setStatus(false);
		cargoRepositorio.atualizar(cargo);
		this.cargos = null;
		this.cargo = new Cargo();
		listarTabela();
		return null;
	}

	// Editar um SubCategoria
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
	
	public void importXlsx(FileUploadEvent upload) {
		try (FileInputStream file = (FileInputStream) upload.getFile().getInputstream()) {

			XSSFWorkbook workbook = new XSSFWorkbook(file);
			Sheet sheet = workbook.getSheetAt(0);

			for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
				Row row = sheet.getRow(i);

				Cell cellCodigos = row.getCell(0); // codigo
				Cell cellNomes = row.getCell(1); // nome
				Cell cellDescricoes = row.getCell(2); // descricao

				String codigo = cellCodigos.getStringCellValue();
				String nome = cellNomes.getStringCellValue();
				String descricao = cellDescricoes.getStringCellValue();

				this.cargo = new Cargo();
				
				this.cargo.setCodigo(codigo);
				this.cargo.setNome(nome);
				this.cargo.setDescricao(descricao);

				this.cargo.setStatus(true);
				this.cargo.setPermissao(EnumPermissao.COMUM.toString());
				this.cargo.setSubCategoria(null);

				salvar();
			}
			workbook.close();

		} catch (IOException e) {
			e.printStackTrace();
			requestContext.scrollTo("messages");
			facesContext.info("Erro na exportação do arquivo!");
		}
	}

}
