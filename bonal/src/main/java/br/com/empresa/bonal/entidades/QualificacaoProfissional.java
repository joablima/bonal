package br.com.empresa.bonal.entidades;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
public class QualificacaoProfissional {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String titulo;

	private String descricao;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_Inicio")
	private Calendar dataInicio = null;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_Fim")
	private Calendar dataFim = null;

	@Version
	private Integer version;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_cadastro")
	private Calendar dataCadastro = Calendar.getInstance();
	
	//relacionamento corrigido, muitas qualificações para um Funcionario
	//e apenas um funcionario para uma qualificação
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="funcionario")
	private Funcionario funcionario;

//	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "funcionario")
//	private Funcionario funcionario;

	// ---------- Relacionamentos para muito com Funcionario ----------
	// @ManyToOne(cascade = CascadeType.MERGE)
	// @JoinColumn(name = "funcionario_id")
	// private Funcionario funcionario;
	//
	// public Funcionario getFuncionario() {
	// return funcionario;
	// }
	//
	// public void setFuncionario(Funcionario funcionario) {
	// this.funcionario = funcionario;
	// }
	// -----------------------------------------------------------------

	public Long getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Calendar getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Calendar dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Calendar getDataFim() {
		return dataFim;
	}

	public void setDataFim(Calendar dataFim) {
		this.dataFim = dataFim;
	}

	public Integer getVersion() {
		return version;
	}

	public Calendar getDataCadastro() {
		return dataCadastro;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getClass().getSimpleName() + " {");
		builder.append("\n\tid= " + getId());
		builder.append("\n\ttitulo= " + getTitulo());
		builder.append("\n\tdescricao= " + getDescricao());
		builder.append("\n\tdataInicio= " + getDataInicio().getTime());
		builder.append("\n\tdataFim= " + getDataFim().getTime());
		builder.append("\n\tdataCadastro=" + getDataCadastro().getTime());
		builder.append("\n\tversion=" + getVersion());
		builder.append("\n }");
		return builder.toString();
	}
}
