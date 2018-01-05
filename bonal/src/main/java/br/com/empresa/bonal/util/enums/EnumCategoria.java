package br.com.empresa.bonal.util.enums;

public enum EnumCategoria {

	BEM("Bem"), SERVICO("Serviço");

	private String label;

	private EnumCategoria(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
