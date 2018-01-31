package br.com.empresa.bonal.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;


@SuppressWarnings("serial")
@Entity
@DiscriminatorValue("FORNECEDOR")
public class Fornecedor extends Pessoa implements Serializable{
	
	@ManyToMany
	@JoinTable(name = "fornecimento_de_item", joinColumns=@JoinColumn(name="fornecedor_id"), 
	inverseJoinColumns=@JoinColumn(name="item_id"))
	private List<ItemDeProducao> itens;
	
	public void addItem(ItemDeProducao i){
		if(itens == null){
			itens = new ArrayList<>();
		}
		
		itens.add(i);
	}
	
	public void delItem(ItemDeProducao i){
		if(itens == null){
			itens = new ArrayList<>();
		}
		
		itens.remove(i);
	}

	public List<ItemDeProducao> getItens() {
		return itens;
	}

	public void setItens(List<ItemDeProducao> itens) {
		this.itens = itens;
	}
	
	
	
}
