//package br.com.empresa.bonal.entidades;
//
//
//
//import java.util.Date;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//
//@Entity
//public class BemPermanente {
//	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	private Long id;
//	//define um codigo que irá obedecer a uma ordem com Prefixo da categoria que pertence+codigo dado pelo usuário
//	private String codigo, descricao; 
//	
//	//campo para guardar a data de garanti do bem 	
//	private Date dataGarantia;
//	
//	//registra a unidade de medida referente ao bem
//	@ManyToOne(cascade=CascadeType.ALL)
//	@JoinColumn(name="unidadeDeMedida")
//	private UnidadeDeMedida unidadeDeMedida;
//	
//	//registra a categoria que esse bem pertence 
//	@ManyToOne(cascade=CascadeType.ALL)
//	@JoinColumn(name="categoria")
//	private Categoria categoria;
//	
//	//registra a quantidade de bem que existe disponível
//	private double quantidade;
//	
//	//registra a vida util e a partir dela calcula a taxa de depreciação
//	private int vidaUtil, taxaDepreciacao;
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public String getCodigo() {
//		return codigo;
//	}
//
//	public void setCodigo(String codigo) {
//		this.codigo = codigo;
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
//	public Date getDataGarantia() {
//		return dataGarantia;
//	}
//
//	public void setDataGarantia(Date dataGarantia) {
//		this.dataGarantia = dataGarantia;
//	}
//
//	public UnidadeDeMedida getUnidadeDeMedida() {
//		return unidadeDeMedida;
//	}
//
//	public void setUnidadeDeMedida(UnidadeDeMedida unidadeDeMedida) {
//		this.unidadeDeMedida = unidadeDeMedida;
//	}
//
//	public double getQuantidade() {
//		return quantidade;
//	}
//
//	public void setQuantidade(double quantidade) {
//		this.quantidade = quantidade;
//	}
//
//	public int getVidaUtil() {
//		return vidaUtil;
//	}
//
//	public void setVidaUtil(int vidaUtil) {
//		this.vidaUtil = vidaUtil;
//	}
//
//	public int getTaxaDepreciacao() {
//		return taxaDepreciacao;
//	}
//
//	public void setTaxaDepreciacao(int taxaDepreciacao) {
//		this.taxaDepreciacao = taxaDepreciacao;
//	}
//
//	public Categoria getCategoria() {
//		return categoria;
//	}
//
//	public void setCategoria(Categoria categoria) {
//		this.categoria = categoria;
//	}
//	
//	
//	
//}
