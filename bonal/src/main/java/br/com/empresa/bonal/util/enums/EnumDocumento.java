package br.com.empresa.bonal.util.enums;

import br.com.empresa.bonal.util.FormatadorDeCnpj;
import br.com.empresa.bonal.util.FormatadorDeCpf;
import br.com.empresa.bonal.util.ValidadorDeCnpj;
import br.com.empresa.bonal.util.ValidadorDeCpf;
import br.com.empresa.bonal.util.interfaces.Formatador;
import br.com.empresa.bonal.util.interfaces.Validador;

public enum EnumDocumento {

	RG("RG - Registro...", null, null), 
	CPF("CPF - Cadastro...", new FormatadorDeCpf(), new ValidadorDeCpf()), 
	CNPJ("CNPJ - Cadastro...", new FormatadorDeCnpj(), new ValidadorDeCnpj());

	private String descricao;
	private Formatador formatador;
	private Validador validador;

	private EnumDocumento(String descricao, Formatador formatador, Validador validador) {
		this.descricao = descricao;
		this.formatador = formatador;
		this.validador = validador;
	}
	
	public String getDescricao() {
		return descricao;
	}

	/**
     * Formata número do documento
     */
    public String formata(String numero) {
        if (this.formatador == null)
            return numero;
        return this.formatador.formata(numero);
    }
    
    public boolean valida(String numero) {
    	if(this.validador == null)
    		return true;
    	return this.validador.valida(numero);
    }

}
