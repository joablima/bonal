package br.com.empresa.bonal.util;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class LoggerUtil {

	@Produces
	public Logger createLogger(InjectionPoint ip) {
		return LogManager.getLogger(ip.getMember().getDeclaringClass().getName());
	}
}
