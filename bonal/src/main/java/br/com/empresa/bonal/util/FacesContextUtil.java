package br.com.empresa.bonal.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class FacesContextUtil {

	FacesContext context = FacesContext.getCurrentInstance();

	public void info(String message) {
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
	}

	public void error(String message) {
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
	}

	public void warn(String message) {
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, message, null));
	}

	public void fatal(String message) {
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, message, null));
	}

	// ----------------------------------- Dois parametros
	public void info(String str, String message) {
		context.addMessage(str, new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
	}

	public void error(String str, String message) {
		context.addMessage(str, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
	}

	public void warn(String str, String message) {
		context.addMessage(str, new FacesMessage(FacesMessage.SEVERITY_WARN, message, null));
	}

	public void fatal(String str, String message) {
		context.addMessage(str, new FacesMessage(FacesMessage.SEVERITY_FATAL, message, null));
	}
}
