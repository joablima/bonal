package br.com.empresa.bonal.util;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@SuppressWarnings("serial")
public class FacesContextUtil implements Serializable {

	@Inject
	FacesContext context;

	@Produces
	@RequestScoped
	public FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

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
}
