package br.com.empresa.bonal.util.enums;

public enum EnumFormaAquisicao {

	COMPRA("Compra"), DOACAO("Doa��o");

	private String label;

	private EnumFormaAquisicao(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
