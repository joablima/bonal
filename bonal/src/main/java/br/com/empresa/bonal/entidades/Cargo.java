package br.com.empresa.bonal.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.empresa.bonal.util.enums.EnumPermissao;

@SuppressWarnings("serial")
@Entity
public class Cargo implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message  = "Nome do Cargo obrigatório")
	private String nome;
	
	private Boolean status;

	// define um salario para o cargo
	@NotNull(message  = "Salário do Cargo obrigatório")
	@NotEmpty(message  = "Salário do Cargo obrigatório")
	@Column(precision = 10, scale = 2, nullable = true)
	private BigDecimal salario;

	// define niveis de acesso ao sistema
	@Enumerated(EnumType.STRING)
	private EnumPermissao permissao;

	@Version
	private Integer version;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_cadastro")
	private Calendar dataCadastro = Calendar.getInstance();

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getSalario() {
		return salario;
	}

	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}
	
	
	

	public EnumPermissao getPermissao() {
		return permissao;
	}

	public void setPermissao(EnumPermissao permissao) {
		this.permissao = permissao;
	}

	public Integer getVersion() {
		return version;
	}

	public Calendar getDataCadastro() {
		return dataCadastro;
	}
	
	

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getClass().getSimpleName() + " {");
		builder.append("\n\tid= " + getId());
		builder.append("\n\tnome= " + getNome());
		builder.append("\n\tsalario= " + getSalario());
		builder.append("\n\tpermissao= " + getPermissao());
		builder.append("\n\tdataCadastro=" + getDataCadastro().getTime());
		builder.append("\n\tversion=" + getVersion());
		builder.append("\n }");
		return builder.toString();
	}

	// TODO falta implementar equal e hashcode
}
