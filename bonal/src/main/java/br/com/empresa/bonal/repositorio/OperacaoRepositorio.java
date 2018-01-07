package br.com.empresa.bonal.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.log4j.Logger;

import br.com.empresa.bonal.entidades.Operacao;
import br.com.empresa.bonal.util.JPAUtil;

public class OperacaoRepositorio {

	static Logger logger = Logger.getLogger(OperacaoRepositorio.class);

	// m�todo que persiste um registro
	public void adicionar(Operacao operacao) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(operacao);
		em.getTransaction().commit();
		logger.info("objeto persistido com sucesso");
		em.close();
	}

	// m�todo que atualiza um registro
	public void atualizar(Operacao operacao) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.merge(operacao);
		em.getTransaction().commit();
		em.close();
	}

	// m�todo que remove um registro
	public void remover(Operacao operacao) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.merge(operacao);
		em.getTransaction().commit();
		em.close();
	}

	// m�todo que recupera um objeto pelo id
	public Operacao buscarPorId(Long id) {
		EntityManager em = JPAUtil.getEntityManager();
		Operacao medida = em.find(Operacao.class, id);
		em.close();
		return medida;
	}

	// m�todo que lista todos os registros
	public List<Operacao> listarTodos() {
		EntityManager em = JPAUtil.getEntityManager();
		CriteriaQuery<Operacao> query = em.getCriteriaBuilder().createQuery(Operacao.class);
		query.select(query.from(Operacao.class));
		List<Operacao> list = em.createQuery(query).getResultList();
		em.close();
		return list;
	}

	// m�todo que lista com crit�rios todos os registros
	public List<Operacao> listarPorCriterios(String nome) {
		EntityManager em = JPAUtil.getEntityManager();
		String jpql = "select o from Operacao o where o.nome like :pnome or o.codigo like :pcodigo or o.descricao like :pdescricao";

		TypedQuery<Operacao> query = em.createQuery(jpql, Operacao.class);

		query.setParameter("pnome", '%' + nome + '%');
		query.setParameter("pcodigo", '%' + nome + '%');
		query.setParameter("pdescricao", '%' + nome + '%');

		logger.info(jpql);
		List<Operacao> list = query.getResultList();
		em.close();
		return list;
	}

	// m�todo que lista todos os registros com pagina��o
	public List<Operacao> listarTodosPaginada(int firstResult, int maxResults) {
		EntityManager em = JPAUtil.getEntityManager();
		CriteriaQuery<Operacao> query = em.getCriteriaBuilder().createQuery(Operacao.class);
		query.select(query.from(Operacao.class));
		List<Operacao> list = em.createQuery(query).setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
		em.close();
		return list;
	}

}
