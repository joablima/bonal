package br.com.empresa.bonal.util;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;

import org.primefaces.context.RequestContext;

@SuppressWarnings("serial")
public class RequestContextUtil implements Serializable{

	@Produces
	@RequestScoped
	public RequestContext getRequestContext() {
		return RequestContext.getCurrentInstance();
	}
}
