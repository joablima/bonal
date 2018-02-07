package br.com.empresa.bonal.util.enums;

public enum EnumStatusPagamento {

	PENDENTE("Pendente"), PAGO("Pago");

	private String label;

	private EnumStatusPagamento(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
