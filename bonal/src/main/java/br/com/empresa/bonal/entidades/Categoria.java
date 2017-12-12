package br.com.empresa.bonal.entidades;

/*
	Esta classe irá categorizar os bens de consumo e permanentes, como por exemplo
	bens de consumos podem ser Produtos, Maquinas, equipamentos
*/
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Categoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// define o nome da categoria e o codigo da categoria
	private String nome;
	private String codigo;
	private String descricao;

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getClass().getSimpleName() + " {");
		builder.append("\n\tid= " + getId());
		builder.append("\n\tnome= " + getNome());
		builder.append("\n\tfone= " + getCodigo());
		builder.append("\n\temail= " + getDescricao());
		builder.append("\n }");
		return builder.toString();
	}
}
