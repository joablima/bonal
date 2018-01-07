package br.com.empresa.bonal.util.enums;

public enum EnumPermissao {

	ADMINISTRADOR("Usuário com todas permissões no sistema"), OPERADOR("Usuário com permissões para efetuar ações transacionais"), COMUM("Usuário sem nenhuma permissão no sistema");

	private String label;

	private EnumPermissao(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
