package br.com.empresa.bonal.entidades;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("fornecedor_juridico")
public class FornecedorJuridico extends PessoaJuridica {

}
