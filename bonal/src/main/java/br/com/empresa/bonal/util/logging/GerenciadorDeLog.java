package br.com.empresa.bonal.util.logging;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.apache.commons.lang3.StringUtils;

@SuppressWarnings("serial")
@Interceptor
@Logging
public class GerenciadorDeLog implements Serializable {

	@AroundInvoke
	public Object executaLogging(InvocationContext context) throws Exception {
		String classe = context.getMethod().getDeclaringClass().getName();
		String metodo = context.getMethod().getName();
		Object[] parameters = context.getParameters();
		int length = parameters.length;

		String[] p = new String[length];
		if (length >= 1)
			for (int i = 0; i < parameters.length; i++) {
				p[i] = (!parameters[i].equals(null)) ? parameters[i].getClass().getSimpleName() : "null";
			}

		String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss,SSS"));
		String parametros = StringUtils.join(p, ", ");
		System.out.println("[INFO ] " + now + " [INICIALIZADA] " + classe + " - " + metodo + "(" + parametros + ")");

		Instant inicio = Instant.now();
		long total;
		try {
			Object object = context.proceed();

			now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss,SSS"));
			total = Duration.between(inicio, Instant.now()).toMillis();

			System.out.println("[INFO ] " + now + " [FINALIZADA  ] " + classe + " - " + metodo + "(" + parametros
					+ ") - tempo de execução: " + total + " milesegundos");
			return object;
		} catch (Exception e) {
			now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss,SSS"));
			total = Duration.between(inicio, Instant.now()).toMillis();
			System.err.println("[ERROR] " + now + " [FINALIZADA  ] " + classe + " - " + metodo + "(" + parametros
					+ ") - tempo de execução: " + total + " milesegundos");
			return null;
		}
	}
}
