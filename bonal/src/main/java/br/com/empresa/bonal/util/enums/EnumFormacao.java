package br.com.empresa.bonal.util.enums;

public enum EnumFormacao {

	FUNDAMENTAL_INCOMPLETO("Ensino Fundamental Incompleto"), FUNDAMENTAL_COMPLETO(
			"Ensino Fundamental Completo"), MEDIO_INCOMPLETO("Ensino M�dio Incompleto"), MEDIO_COMPLETO(
					"Ensino M�dio Completo"), SUPERIOR_INCOMPLETO("Ensino Superior Incompleto"), SUPERIOR_COMPLETO(
							"Ensino Superior Completo"), MESTRADO("Mestrado"), DOUTORADO("Doutorado");

	private String label;

	private EnumFormacao(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
