package br.com.empresa.bonal.util.enums;

public enum EnumBem {

	BEM_CONSUMO("Bem de Consumo"), BEM_PERMANENTE("Bem Permanente");

	private String label;

	private EnumBem(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
