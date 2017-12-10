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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	//define o nome da categoria e o prefixo que irá ser combinado no código de todos os bens pertencentes a essa clase
	private String nome, prefixo;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getPrefixo() {
		return prefixo;
	}
	public void setPrefixo(String prefixo) {
		this.prefixo = prefixo;
	}
	
	
}
