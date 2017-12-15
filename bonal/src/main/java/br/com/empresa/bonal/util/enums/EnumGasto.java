package br.com.empresa.bonal.util.enums;

public enum EnumGasto {

	CUSTO_FIXO("Custo Fixo"), CUSTO_VARIAVEL("Custo Variável"), DESPESA("Despesa");

	private String label;

	private EnumGasto(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
