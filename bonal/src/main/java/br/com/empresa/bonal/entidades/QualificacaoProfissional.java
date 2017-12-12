//package br.com.empresa.bonal.entidades;
//
//import java.util.Calendar;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//
//@Entity
//public class QualificacaoProfissional {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
//
//	private String titulo;
//
//	private String descricao;
//
//	@Temporal(TemporalType.DATE)
//	@Column(name = "data_Inicio")
//	private Calendar dataInicio = null;
//
//	@Temporal(TemporalType.DATE)
//	@Column(name = "data_Fim")
//	private Calendar dataFim = null;
//
//	// ---------- Inicio Relacionamentos ----------
//	@ManyToOne(cascade = CascadeType.MERGE)
//	@JoinColumn(name = "funcionario_id")
//	private Funcionario funcionario;
//
//	public Long getId() {
//		return id;
//	}
//
//	public String getTitulo() {
//		return titulo;
//	}
//
//	public void setTitulo(String titulo) {
//		this.titulo = titulo;
//	}
//
//	public String getDescricao() {
//		return descricao;
//	}
//
//	public void setDescricao(String descricao) {
//		this.descricao = descricao;
//	}
//
//	public Calendar getDataInicio() {
//		return dataInicio;
//	}
//
//	public void setDataInicio(Calendar dataInicio) {
//		this.dataInicio = dataInicio;
//	}
//
//	public Calendar getDataFim() {
//		return dataFim;
//	}
//
//	public void setDataFim(Calendar dataFim) {
//		this.dataFim = dataFim;
//	}
//
//	public Funcionario getFuncionario() {
//		return funcionario;
//	}
//
//	public void setFuncionario(Funcionario funcionario) {
//		this.funcionario = funcionario;
//	}
//
//	// não necessário version e dataDeRegistro por estar em funcionario
//}
