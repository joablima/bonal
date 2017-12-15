package br.com.empresa.bonal.util.enums;

public enum EnumSexo {

	MASCULINO("Masculino"), FEMININO("Feminino");

	private String label;

	private EnumSexo(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
