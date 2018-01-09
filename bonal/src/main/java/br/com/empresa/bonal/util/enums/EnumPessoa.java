package br.com.empresa.bonal.util.enums;

public enum EnumPessoa {

	PESSOA_FISICA("Pessoa Física"), PESSOA_JURIDICA("Pessoa Jurídica");

	private String label;

	private EnumPessoa(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
