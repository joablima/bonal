package br.com.empresa.bonal.entidades;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
@DiscriminatorValue("FORNECEDOR")
public class Cliente extends Pessoa implements Serializable{
	
	
	
}
