//package br.com.empresa.bonal.entidades;
//
//import java.util.Calendar;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//import javax.persistence.Version;
//
//@Entity
//public class Endereco {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
//
//	private String logradouro;
//	private Integer numero;
//	private String bairro;
//	private String cidade;
//	private String uf;
//	private String complemento;
//	private String cep;
//
//	@Version
//	private Integer version;
//
//	@Temporal(TemporalType.DATE)
//	@Column(name = "data_registro")
//	private Calendar dataRegistro = Calendar.getInstance();
//
//	// ---------- Inicio Relacionamentos ----------
//
//	// @OneToMany(mappedBy = "endereco", cascade = CascadeType.MERGE)
//	// private List<Pessoa> pessoas = new ArrayList<>();
//
//	// ---------- Fim Relacionamentos ----------
//
//	public Long getId() {
//		return id;
//	}
//
//	public String getLogradouro() {
//		return logradouro;
//	}
//
//	public void setLogradouro(String logradouro) {
//		this.logradouro = logradouro;
//	}
//
//	public Integer getNumero() {
//		return numero;
//	}
//
//	public void setNumero(Integer numero) {
//		this.numero = numero;
//	}
//
//	public String getBairro() {
//		return bairro;
//	}
//
//	public void setBairro(String bairro) {
//		this.bairro = bairro;
//	}
//
//	public String getCidade() {
//		return cidade;
//	}
//
//	public void setCidade(String cidade) {
//		this.cidade = cidade;
//	}
//
//	public String getUf() {
//		return uf;
//	}
//
//	public void setUf(String uf) {
//		this.uf = uf;
//	}
//
//	public String getComplemento() {
//		return complemento;
//	}
//
//	public void setComplemento(String complemento) {
//		this.complemento = complemento;
//	}
//
//	public String getCep() {
//		return cep;
//	}
//
//	public void setCep(String cep) {
//		this.cep = cep;
//	}
//
//	public Integer getVersion() {
//		return version;
//	}
//
//	public Calendar getDataRegistro() {
//		return dataRegistro;
//	}
//
//	// public List<Pessoa> getPessoas() {
//	// return pessoas;
//	// }
//
//	// Adicionar e Remover Endereço a Pessoa
//	// public void adicionarPessoa(Pessoa pessoa) {
//	// pessoa.setEndereco(this);
//	// getPessoas().add(pessoa);
//	// }
//	//
//	// public void removerPessoa(Pessoa pessoa) {
//	// pessoa.setEndereco(null);
//	// getPessoas().remove(pessoa);
//	// }
//
//	@Override
//	public String toString() {
//		StringBuilder builder = new StringBuilder();
//		builder.append(getClass().getSimpleName() + " {");
//		builder.append("\n\tid= " + getId());
//		builder.append("\n\tlogradouro= " + getLogradouro());
//		builder.append("\n\tnumero= " + getNumero());
//		builder.append("\n\tbairro= " + getBairro());
//		builder.append("\n\tcidade= " + getCidade());
//		builder.append("\n\tuf= " + getUf());
//		builder.append("\n\tcep= " + getCep());
//		builder.append("\n\tdata de registro= " + getDataRegistro());
//		builder.append("\n\tversion= " + getVersion());
//		builder.append("\n }");
//		return builder.toString();
//	}
//
//	// Criar hashCode
//}
