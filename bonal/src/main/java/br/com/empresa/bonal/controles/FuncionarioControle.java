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
import br.com.empresa.bonal.entidades.Funcionario;
import br.com.empresa.bonal.entidades.QualificacaoProfissional;
import br.com.empresa.bonal.repositorio.FuncionarioRepositorio;
import br.com.empresa.bonal.util.FacesContextUtil;
import br.com.empresa.bonal.util.enums.EnumFormacao;
import br.com.empresa.bonal.util.enums.EnumSexo;

@ManagedBean
@ViewScoped
public class FuncionarioControle {

	private Funcionario funcionario = new Funcionario();

	private Long funcionarioId;

	private Long cargoId;
	private Long qualificacaoId;

	// Atributos para Consulta
	private String funcionarioNome = "";

	// Listas para Consulta
	private List<Funcionario> funcionarios;
	private List<Funcionario> lista = new ArrayList<>();

	// Repositorio
	private FuncionarioRepositorio funcionarioRepositorio;

	// Construtor chamando a classe repositorio
	public FuncionarioControle() {
		funcionarioRepositorio = new FuncionarioRepositorio();
	}

	// Getters and Setters
	public Funcionario getFuncionario() {
		return funcionario;
	}

	// Adicionado para propriedade de contexto das tabelas do Primefaces
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Long getFuncionarioId() {
		return funcionarioId;
	}

	public void setFuncionarioId(Long funcionarioId) {
		this.funcionarioId = funcionarioId;
	}

	public Long getCargoId() {
		return cargoId;
	}

	public void setCargoId(Long cargoId) {
		this.cargoId = cargoId;
	}

	public Long getQualificacaoId() {
		return qualificacaoId;
	}

	public void setQualificacaoId(Long qualificacaoId) {
		this.qualificacaoId = qualificacaoId;
	}

	public String getFuncionarioNome() {
		return funcionarioNome;
	}

	public void setFuncionarioNome(String funcionarioNome) {
		this.funcionarioNome = funcionarioNome;
	}

	// ----- Carrega os Enums em Arrays -----
	public EnumSexo[] getEnumSexo() {
		return EnumSexo.values();
	}

	public EnumFormacao[] getEnumFormacoes() {
		return EnumFormacao.values();
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public List<Funcionario> getLista() {
		return Collections.unmodifiableList(lista);
	}

	// verificar importancia dos métodos abaixo //verificar se estão trocados??
	public Integer getTotalFuncionarios() {
		return lista.size();
	}

	public Integer getTotalFuncionariosConsulta() {
		return funcionarios.size();
	}

	// ----------------- METODOS ----------------------
	@PostConstruct
	public void listarTabela() {
		if (this.funcionarios == null) {
			lista = funcionarioRepositorio.listarTodos();
			funcionarios = new ArrayList<>(lista);
		}
		filtrarTabela();
	}

	public void filtrarTabela() {
		Stream<Funcionario> stream = lista.stream();

		if (!funcionarioNome.equals(null))
			stream = stream.filter(c -> (c.getNome().toLowerCase().contains(funcionarioNome.toLowerCase().trim())));

		if (cargoId != null)
			stream = stream.filter(c -> (c.getCargo().getId().equals(cargoId)));

		if (qualificacaoId != null)
			stream = stream.filter(c -> (c.getQualificacao().getId().equals(qualificacaoId)));

		funcionarios = stream.collect(Collectors.toList());
	}

	// Método chamado ao carregar pagina de consulta para popular tabela
	public String listar() {
		listarTabela();
		return null;
	}

	// Limpar tabela da consulta,
	public String limpar() {
		this.funcionarioNome = "";
		filtrarTabela(); // Retorna a lista unmodifiablelist offline armazenada
		return null;
	}

	public void salvar(Funcionario funcionario, Cargo cargo, QualificacaoProfissional qualificacao) {
		this.funcionario = funcionario;
		this.cargoId = cargo.getId();
		this.qualificacaoId = qualificacao.getId();
		salvar();
	}

	// Métodos que utilizam métodos do repositório
	public String salvar() {
		String message = "";
		if (funcionario.getId() == null) {
			funcionarioRepositorio.adicionar(funcionario, cargoId, qualificacaoId);
			message += "Funcionário cadastrado com Sucesso.";
		} else {
			funcionarioRepositorio.atualizar(funcionario, cargoId, qualificacaoId);
			message += "Funcionário atualizado com Sucesso.";
		}
		new FacesContextUtil().info(message);
		System.out.println(message);
		funcionario = new Funcionario();
		return null;
	}

	public void recuperarFuncionarioPorId() {
		funcionario = funcionarioRepositorio.buscarPorId(funcionarioId);
	}

	// Remove um Bem do banco de dados
	public void remover() {
		funcionarioRepositorio.remover(funcionario);
		funcionarios = null;
		listarTabela();
		funcionario = null;
	}

	public void remover(Funcionario funcionario) {
		this.funcionario = funcionario;
		remover();
	}

	// Editar um Bem
	public String editar() {
		funcionarioId = this.funcionario.getId();
		return "funcionario?funcionarioId=" + funcionarioId;
	}

	public String editar(Funcionario funcionario) {
		this.funcionario = funcionario;
		return editar();
	}

	public boolean funcionarioIdExiste() {
		if (this.funcionarioId == null)
			return false;
		return true;
	}

}
