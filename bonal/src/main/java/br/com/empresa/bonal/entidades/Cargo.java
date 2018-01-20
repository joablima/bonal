package br.com.empresa.bonal.entidades;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
@DiscriminatorValue("cargo")
public class Cargo extends ItemDeProducao implements Serializable{
	
	private String permissao;
	
	
	public String getPermissao() {
		return permissao;
	}


	public void setPermissao(String permissao) {
		this.permissao = permissao;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getClass().getSimpleName() + " {");
		builder.append("\n\tid= " + getId());
		builder.append("\n\tnome= " + getNome());
		builder.append("\n\tpermissao= " + getPermissao());
		builder.append("\n\tdataCadastro=" + getDataCadastro().getTime());
		builder.append("\n\tversion=" + getVersion());
		builder.append("\n }");
		return builder.toString();
	}

	// TODO falta implementar equal e hashcode
}
