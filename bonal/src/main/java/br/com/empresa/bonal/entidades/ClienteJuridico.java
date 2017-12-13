package br.com.empresa.bonal.entidades;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("cliente_juridico")
public class ClienteJuridico extends PessoaJuridica {



}
