package br.com.empresa.bonal.controles;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

@Named
@ViewScoped
public class DialogControle implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private RequestContext requestContext;

	public void cargoCadastroDialogShow() {
		Map<String, Object> options = optionsDialog();
		requestContext.openDialog("/dialogs/cargoCadastroDialog", options, null);
	}

	public void cargoConsultaDialogShow() {
		Map<String, Object> options = optionsDialog();
		requestContext.openDialog("/dialogs/cargoConsultaDialog", options, null);
	}
	
	public void categoriaCadastroDialogShow() {
		Map<String, Object> options = optionsDialog();
		requestContext.openDialog("/dialogs/categoriaCadastroDialog", options, null);
	}

	public void categoriaConsultaDialogShow() {
		Map<String, Object> options = optionsDialog();
		requestContext.openDialog("/dialogs/categoriaConsultaDialog", options, null);
	}

	public void unidadeDeMedidaCadastroDialogShow() {
		Map<String, Object> options = optionsDialog();
		requestContext.openDialog("/dialogs/unidadeDeMedidaCadastroDialog", options, null);
	}

	
	public void unidadeDeMedidaConsultaDialogShow() {
		Map<String, Object> options = optionsDialog();
		requestContext.openDialog("/dialogs/unidadeDeMedidaConsultaDialog", options, null);
	}
	
	public void subCategoriaCadastroDialogShow() {
		Map<String, Object> options = optionsDialog();
		requestContext.openDialog("/dialogs/subCategoriaCadastroDialog", options, null);
	}

	
	public void subCategoriaConsultaDialogShow() {
		Map<String, Object> options = optionsDialog();
		requestContext.openDialog("/dialogs/subCategoriaConsultaDialog", options, null);
	}
	


	private Map<String, Object> optionsDialog() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("modal", true);
		options.put("draggable", true);
		options.put("resizable", false);
		options.put("contentWidth", 720);
		options.put("contentHeight", 500);
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
		// requestContext.openDialog("/dialogs/cargoConsultaDialog", options, null);
	}
}
