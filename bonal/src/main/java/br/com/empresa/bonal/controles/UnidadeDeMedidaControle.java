package br.com.empresa.bonal.controles;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

import br.com.empresa.bonal.entidades.UnidadeDeMedida;
import br.com.empresa.bonal.repositorio.UnidadeDeMedidaRepositorio;
import br.com.empresa.bonal.util.FacesContextUtil;
import br.com.empresa.bonal.util.logging.Logging;
import br.com.empresa.bonal.util.tx.Transacional;

@Named
@ViewScoped
public class UnidadeDeMedidaControle implements Serializable {
	private static final long serialVersionUID = 1L;

	private UnidadeDeMedida unidadeDeMedida = new UnidadeDeMedida();

	private Long unidadeDeMedidaId;
	private Boolean status = true;

	// Atributos para Consulta
	private String unidadeDeMedidaNome = "";

	// Listas para Consulta
	private List<UnidadeDeMedida> unidadesDeMedida;
	private List<UnidadeDeMedida> lista = new ArrayList<>();

	@Inject
	private UnidadeDeMedidaRepositorio unidadeDeMedidaRepositorio;

	@Inject
	private FacesContextUtil facesContext;

	@Inject
	private RequestContext requestContext;

	// Getters and Setters
	public UnidadeDeMedida getUnidadeDeMedida() {
		return unidadeDeMedida;
	}

	// Adicionado para propriedade de contexto das tabelas do Primefaces
	public void setUnidadeDeMedida(UnidadeDeMedida unidadeDeMedida) {
		this.unidadeDeMedida = unidadeDeMedida;
	}

	public Long getUnidadeDeMedidaId() {
		return unidadeDeMedidaId;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public void setUnidadeDeMedidaId(Long unidadeDeMedidaId) {
		this.unidadeDeMedidaId = unidadeDeMedidaId;
	}

	public String getUnidadeDeMedidaNome() {
		return unidadeDeMedidaNome;
	}

	public void setUnidadeDeMedidaNome(String unidadeDeMedidaNome) {
		this.unidadeDeMedidaNome = unidadeDeMedidaNome;
	}

	public List<UnidadeDeMedida> getUnidadesDeMedida() {
		return unidadesDeMedida;
	}

	public List<UnidadeDeMedida> getLista() {
		return Collections.unmodifiableList(lista);
	}

	// verificar importancia dos m�todos abaixo //verificar se est�o trocados??
	public Integer getTotalUnidadesDeMedida() {
		return lista.size();
	}

	public Integer getTotalUnidadesDeMedidaConsulta() {
		return unidadesDeMedida.size();
	}

	// ----------------- METODOS ----------------------
	// @PostConstruct
	public void listarTabela() {
		if (this.unidadesDeMedida == null) {
			lista = unidadeDeMedidaRepositorio.listarTodos();
			unidadesDeMedida = new ArrayList<>(lista);
		}
		filtrarTabela();
	}

	public void filtrarTabela() {
		Stream<UnidadeDeMedida> stream = lista.stream();

		stream = stream.filter(u -> (u.getNome().toLowerCase().contains(unidadeDeMedidaNome.toLowerCase().trim())));

		if (status.equals(true))
			stream = stream.filter(u -> (u.getStatus().equals(status)));

		unidadesDeMedida = stream.collect(Collectors.toList());
	}

	// M�todo chamado ao carregar tabela
	public String listar() {
		listarTabela();
		return null;
	}

	// Limpar tabela da consulta
	public String limpar() {
		this.unidadeDeMedidaNome = "";
		this.status = true;
		this.unidadesDeMedida = new ArrayList<>(this.lista);
		return null;
	}

	// M�todos que utilizam m�todos do reposit�rio
	@Logging
	@Transacional
	public String salvar() {
		String message = "";
		this.unidadeDeMedida.setStatus(true);
		if (unidadeDeMedida.getId() == null) {
			UnidadeDeMedida existe = unidadeDeMedidaRepositorio.unidadeMedidaExiste(unidadeDeMedida);
			if (existe != null) {
				facesContext.warn("Já existe essa unidade de medida registrada.");
				return null;
			}
			unidadeDeMedidaRepositorio.adicionar(unidadeDeMedida);
			message += "Unidade de Medida Cadastrada com Sucesso.";
		} else {
			unidadeDeMedidaRepositorio.atualizar(unidadeDeMedida);
			message += "Unidade de Medida Atualizada com Sucesso.";
		}
		facesContext.info(message);
		unidadeDeMedida = new UnidadeDeMedida();
		return null;
	}

	public void recuperarUnidadeDeMedidaPorId() {
		unidadeDeMedida = unidadeDeMedidaRepositorio.buscarPorId(unidadeDeMedidaId);
	}

	// Remove um cargo do banco de dados
	@Logging
	@Transacional
	public String remover(UnidadeDeMedida unidade) {
		unidade.setStatus(false);
		unidadeDeMedidaRepositorio.remover(unidade);
		this.unidadesDeMedida = null;
		listar();
		return null;
	}

	// essa classe eh bom deixar de ser usada... use button ou link e ponha isso
	// outcome="unidadeDeMedida?unidadeDeMedidaId=#{unidadeDeMedidaControle.unidadeDeMedida.id}"
	public String editar(UnidadeDeMedida unidadeDeMedida) {
		return "unidadeDeMedida?unidadeDeMedidaId=" + unidadeDeMedida.getId();
	}

	public String cancelar() {
		return "index";
	}

	@Logging
	public boolean cargoIdExiste() {
		if (this.unidadeDeMedidaId == null)
			return false;
		return true;
	}

	public void importXlsx(FileUploadEvent upload) {
		try (FileInputStream file = (FileInputStream) upload.getFile().getInputstream()) {

			XSSFWorkbook workbook = new XSSFWorkbook(file);
			Sheet sheet = workbook.getSheetAt(0);

			for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
				Row row = sheet.getRow(i);

				Cell cellSiglas = row.getCell(0); // sigla
				Cell cellNomes = row.getCell(1); // nome

				String sigla = cellSiglas.getStringCellValue();
				String nome = cellNomes.getStringCellValue();
				this.unidadeDeMedida = new UnidadeDeMedida();
				this.unidadeDeMedida.setSigla(sigla);
				this.unidadeDeMedida.setNome(nome);
				this.unidadeDeMedida.setStatus(true);
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
