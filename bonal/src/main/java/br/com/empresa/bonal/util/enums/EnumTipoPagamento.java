package br.com.empresa.bonal.util.enums;

public enum EnumTipoPagamento {

	DINHEIRO("Dinheiro"), CARTAO("Cartão"), TRANSFERENCIA("Transferencia bancária"), BOLETO("Boleto"), DEPOSITO("Depósito");

	private String label;

	private EnumTipoPagamento(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
