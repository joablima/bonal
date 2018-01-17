package br.com.empresa.bonal.util.logging;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.apache.logging.log4j.Logger;

@SuppressWarnings("serial")
@Interceptor
@Logging
public class GerenciadorDeLog implements Serializable {

	@Inject
	Logger logger;

	@AroundInvoke
	public Object executaLogging(InvocationContext context) throws Exception {
		String classe = context.getMethod().getDeclaringClass().getName();
		String metodo = context.getMethod().getName();
		Object[] parameters = context.getParameters();
		int length = parameters.length;

		String[] p = new String[length];
		if (length >= 1) {
			for (int i = 0; i < parameters.length; i++) {
				if (!parameters[i].equals(null)) {
					String tipo = parameters[i].getClass().getSimpleName();
					p[i] = tipo;
				}
			}
			logger.info("Inicio -> " + classe + ":" + metodo + " - com " + length + " parametros: " + p);
			for (int i = 0; i < p.length; i++) {
				if (parameters[i].equals(null)) {
					logger.warn("Inicio -> " + classe + ":" + metodo + " - O " + i + "º parametro do método " + metodo
							+ " é null");
				}
			}
		}
		logger.info("Inicio -> " + classe + ":" + metodo + " - sem parametros");

		Long tempoInicial = System.currentTimeMillis();
		Long tempoFinal;
		try {
			Object object = context.proceed();

			tempoFinal = System.currentTimeMillis();
			logger.info("Finalizado regularmente -> " + classe + ":" + metodo + " - tempo de execução: "
					+ (tempoFinal - tempoInicial) + " milesegundos");
			return object;
		} catch (Exception e) {
			tempoFinal = System.currentTimeMillis();
			logger.warn("Finalizado com Exception -> " + classe + ":" + metodo + " - tempo de execução: "
					+ (tempoFinal - tempoInicial) + " milesegundos");
			return null;
		}
	}
}
