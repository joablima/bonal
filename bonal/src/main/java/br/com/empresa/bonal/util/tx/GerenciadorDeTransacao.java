package br.com.empresa.bonal.util.tx;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

@SuppressWarnings("serial")
@Transacional
@Interceptor
public class GerenciadorDeTransacao implements Serializable {

	@Inject
	EntityManager em;

	@AroundInvoke
	public Object executaTx(InvocationContext context) throws Exception {
		try {
			em.getTransaction().begin();

			Object object = context.proceed();

			em.getTransaction().commit();

			return object;

		} catch (Exception e) {
			em.getTransaction().rollback();
			return null;
		}
	}
}
