package br.com.empresa.bonal.entidades;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

@Entity
@DiscriminatorValue("Cliente")
public class Cliente extends Pessoa{
	
	
	
}
