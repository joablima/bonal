package br.com.empresa.bonal.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.log4j.Logger;

import br.com.empresa.bonal.entidades.TipoDePessoa;
import br.com.empresa.bonal.util.JPAUtil;

public class TipoDePessoaRepositorio {

	static Logger logger = Logger.getLogger(TipoDePessoaRepositorio.class);

	// m�todo que persiste um registro
	public void adicionar(TipoDePessoa tipoDePessoa) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(tipoDePessoa);
		em.getTransaction().commit();
		logger.info("objeto persistido com sucesso");
		em.close();
	}

	// m�todo que atualiza um registro
	public void atualizar(TipoDePessoa tipoDePessoa) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.merge(tipoDePessoa);
		em.getTransaction().commit();
		em.close();
	}

	// m�todo que remove um registro
	public void remover(TipoDePessoa tipoDePessoa) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.remove(em.merge(tipoDePessoa));
		em.getTransaction().commit();
		em.close();
	}

	// m�todo que recupera um objeto pelo id
	public TipoDePessoa buscarPorId(Long id) {
		EntityManager em = JPAUtil.getEntityManager();
		TipoDePessoa medida = em.find(TipoDePessoa.class, id);
		em.close();
		return medida;
	}

	// m�todo que lista todos os registros
	public List<TipoDePessoa> listarTodos() {
		EntityManager em = JPAUtil.getEntityManager();
		CriteriaQuery<TipoDePessoa> query = em.getCriteriaBuilder().createQuery(TipoDePessoa.class);
		query.select(query.from(TipoDePessoa.class));
		List<TipoDePessoa> list = em.createQuery(query).getResultList();
		em.close();
		return list;
	}

	// m�todo que lista com crit�rios todos os registros
	public List<TipoDePessoa> listarPorCriterios(String nome) {
		EntityManager em = JPAUtil.getEntityManager();
		String jpql = "select t from TipoDePessoa t where t.nome like :pnome";

		TypedQuery<TipoDePessoa> query = em.createQuery(jpql, TipoDePessoa.class);

		query.setParameter("pnome", '%' + nome + '%');

		System.out.println(jpql);
		List<TipoDePessoa> list = query.getResultList();
		em.close();
		return list;
	}

	// m�todo que lista todos os registros com pagina��o
	public List<TipoDePessoa> listarTodosPaginada(int firstResult, int maxResults) {
		EntityManager em = JPAUtil.getEntityManager();
		CriteriaQuery<TipoDePessoa> query = em.getCriteriaBuilder().createQuery(TipoDePessoa.class);
		query.select(query.from(TipoDePessoa.class));
		List<TipoDePessoa> list = em.createQuery(query).setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
		em.close();
		return list;
	}

}
