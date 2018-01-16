package br.com.empresa.bonal.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.logging.log4j.Logger;

import br.com.empresa.bonal.entidades.Operacao;

public class OperacaoRepositorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;
	
	@Inject
	private Logger logger;

	// m�todo que persiste um registro
	public void adicionar(Operacao operacao) {
		em.persist(operacao);
	}

	// m�todo que atualiza um registro
	public void atualizar(Operacao operacao) {
		em.merge(operacao);
	}

	// m�todo que remove um registro
	public void remover(Operacao operacao) {
		em.merge(operacao);
	}

	// m�todo que recupera um objeto pelo id
	public Operacao buscarPorId(Long id) {
		Operacao medida = em.find(Operacao.class, id);
		return medida;
	}

	// m�todo que lista todos os registros
	public List<Operacao> listarTodos() {
		try {
			return em.createQuery("select o from Operacao o", Operacao.class).getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	// m�todo que lista com crit�rios todos os registros
	public List<Operacao> listarPorCriterios(String nome) {
		String jpql = "select o from Operacao o where o.nome like :pnome or o.codigo like :pcodigo or o.descricao like :pdescricao";

		TypedQuery<Operacao> query = em.createQuery(jpql, Operacao.class);

		query.setParameter("pnome", '%' + nome + '%');
		query.setParameter("pcodigo", '%' + nome + '%');
		query.setParameter("pdescricao", '%' + nome + '%');

		logger.info(jpql);
		List<Operacao> list = query.getResultList();
		return list;
	}

	// m�todo que lista todos os registros com pagina��o
	public List<Operacao> listarTodosPaginada(int firstResult, int maxResults) {
		CriteriaQuery<Operacao> query = em.getCriteriaBuilder().createQuery(Operacao.class);
		query.select(query.from(Operacao.class));
		List<Operacao> list = em.createQuery(query).setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
		return list;
	}

}
