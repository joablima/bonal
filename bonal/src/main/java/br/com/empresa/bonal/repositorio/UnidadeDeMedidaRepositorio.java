package br.com.empresa.bonal.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.log4j.Logger;

import br.com.empresa.bonal.entidades.UnidadeDeMedida;
import br.com.empresa.bonal.util.JPAUtil;

public class UnidadeDeMedidaRepositorio {

	static Logger logger = Logger.getLogger(UnidadeDeMedidaRepositorio.class);

	// m�todo que persiste um registro
	public void adicionar(UnidadeDeMedida unidadeDeMedida) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(unidadeDeMedida);
		em.getTransaction().commit();
		logger.info("objeto persistido com sucesso");
		em.close();
	}

	// m�todo que atualiza um registro
	public void atualizar(UnidadeDeMedida unidadeDeMedida) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.merge(unidadeDeMedida);
		em.getTransaction().commit();
		em.close();
	}

	// m�todo que remove um registro
	public void remover(UnidadeDeMedida unidadeDeMedida) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.merge(unidadeDeMedida);
		em.getTransaction().commit();
		em.close();
	}

	// m�todo que recupera um objeto pelo id
	public UnidadeDeMedida buscarPorId(Long id) {
		EntityManager em = JPAUtil.getEntityManager();
		UnidadeDeMedida medida = em.find(UnidadeDeMedida.class, id);
		em.close();
		return medida;
	}

	// m�todo que lista todos os registros
	public List<UnidadeDeMedida> listarTodos() {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			return em.createQuery("select u from UnidadeDeMedida u", UnidadeDeMedida.class)
					.getResultList();
		} catch (Exception e) {
			return null;
		} finally {
			em.close();
		}
	}

	// m�todo que lista com crit�rios todos os registros
	public List<UnidadeDeMedida> listarPorCriterios(String nome) {
		EntityManager em = JPAUtil.getEntityManager();
		String jpql = "select u from UnidadeDeMedida u where u.nome like :pnome or u.sigla like :psigla";

		TypedQuery<UnidadeDeMedida> query = em.createQuery(jpql, UnidadeDeMedida.class);

		query.setParameter("pnome", '%' + nome + '%');
		query.setParameter("psigla", '%' + nome + '%');

		logger.info(jpql);
		List<UnidadeDeMedida> list = query.getResultList();
		em.close();
		return list;
	}

	// m�todo que lista todos os registros com pagina��o
	public List<UnidadeDeMedida> listarTodosPaginada(int firstResult, int maxResults) {
		EntityManager em = JPAUtil.getEntityManager();
		CriteriaQuery<UnidadeDeMedida> query = em.getCriteriaBuilder().createQuery(UnidadeDeMedida.class);
		query.select(query.from(UnidadeDeMedida.class));
		List<UnidadeDeMedida> list = em.createQuery(query).setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
		em.close();
		return list;
	}

	// método que verifica se elemento existe
	public UnidadeDeMedida unidadeMedidaExiste(UnidadeDeMedida unidade) {
		EntityManager em = JPAUtil.getEntityManager();

		TypedQuery<UnidadeDeMedida> query = em
				.createQuery("select u from UnidadeDeMedida u where u.sigla = :sigla", UnidadeDeMedida.class)
				.setParameter("sigla", unidade.getSigla());

		try {
			UnidadeDeMedida novaUnidade = query.getSingleResult();
			return novaUnidade;
		} catch (Exception e) {
			return null;
		} finally {
			em.close();
		}
	}

}
