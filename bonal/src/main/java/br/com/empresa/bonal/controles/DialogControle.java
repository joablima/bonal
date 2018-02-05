package br.com.empresa.bonal.controles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

@Named
@ViewScoped
public class DialogControle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private RequestContext requestContext;

	public void cargoConsultaDialogShow() {
		Map<String, Object> options = optionsDialog();
		requestContext.openDialog("/dialogs/cargoConsultaDialog", options, null);
	}

	public void categoriaConsultaDialogShow() {
		Map<String, Object> options = optionsDialog();
		requestContext.openDialog("/dialogs/categoriaConsultaDialog", options, null);
	}

	public void unidadeDeMedidaConsultaDialogShow() {
		Map<String, Object> options = optionsDialog();
		requestContext.openDialog("/dialogs/unidadeDeMedidaConsultaDialog", options, null);
	}

	public void subCategoriaConsultaDialogShow() {
		Map<String, Object> options = optionsDialog();
		requestContext.openDialog("/dialogs/subCategoriaConsultaDialog", options, null);
	}


	public void itemDeProducaoConsultaDialogShow() {
		Map<String, Object> options = optionsDialog();
		requestContext.openDialog("/dialogs/itemDeProducaoConsultaDialog", options, null);
	}

	public void clienteConsultaDialogShow() {
		Map<String, Object> options = optionsDialog();
		requestContext.openDialog("/dialogs/clienteConsultaDialog", options, null);
	}

	public void funcionarioConsultaDialogShow() {
		Map<String, Object> options = optionsDialog();
		requestContext.openDialog("/dialogs/funcionarioConsultaDialog", options, null);
	}

	public void fornecedorConsultaDialogShow() {
		Map<String, Object> options = optionsDialog();
		requestContext.openDialog("/dialogs/fornecedorConsultaDialog", options, null);
	}

	public void produtoConsultaDialogShow() {
		Map<String, Object> options = optionsDialog();
		requestContext.openDialog("/dialogs/produtoConsultaDialog", options, null);
	}

	private Map<String, Object> optionsDialog() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("modal", true);
		options.put("draggable", true);
		options.put("resizable", false);
		options.put("contentWidth", 720);
		options.put("contentHeight", 500);
		options.put("includeViewParams", true);

		return options;
	}

	public void fecharDialog() {
		requestContext.closeDialog(null);
	}

	// Dialog com todas as opções
	public void dialogShow() {
		// Map<String, Object> options = new HashMap<String, Object>();
		// options.put("modal", true);
		// options.put("draggable", true);
		// options.put("resizable", false);
		// options.put("width", 720);
		// options.put("height", 600);
		// options.put("contentWidth", 720);
		// options.put("contentHeight", "100%");
		// options.put("includeViewParams", true);
		// options.put("headerElement", "customheader");
		// requestContext.openDialog("/dialogs/cargoConsultaDialog", options,
		// null);
	}
}
