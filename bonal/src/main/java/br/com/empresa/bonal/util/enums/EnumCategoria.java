package br.com.empresa.bonal.util.enums;

public enum EnumCategoria {

	BEM("Bem"), SERVICO("Serviço"), MAO_DE_OBRA("Mao de obra");

	private String label;

	private EnumCategoria(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
