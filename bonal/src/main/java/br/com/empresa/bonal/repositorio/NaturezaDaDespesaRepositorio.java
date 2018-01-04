package br.com.empresa.bonal.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.log4j.Logger;

import br.com.empresa.bonal.entidades.NaturezaDaDespesa;
import br.com.empresa.bonal.util.JPAUtil;

public class NaturezaDaDespesaRepositorio {

	static Logger logger = Logger.getLogger(NaturezaDaDespesaRepositorio.class);

	// m�todo que persiste um registro
	public void adicionar(NaturezaDaDespesa naturezaDaDespesa) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(naturezaDaDespesa);
		em.getTransaction().commit();
		logger.info("objeto persistido com sucesso");
		em.close();
	}

	// m�todo que atualiza um registro
	public void atualizar(NaturezaDaDespesa naturezaDaDespesa) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.merge(naturezaDaDespesa);
		em.getTransaction().commit();
		em.close();
	}

	// m�todo que remove um registro
	public void remover(NaturezaDaDespesa naturezaDaDespesa) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.remove(em.merge(naturezaDaDespesa));
		em.getTransaction().commit();
		em.close();
	}

	// m�todo que recupera um objeto pelo id
	public NaturezaDaDespesa buscarPorId(Long id) {
		EntityManager em = JPAUtil.getEntityManager();
		NaturezaDaDespesa medida = em.find(NaturezaDaDespesa.class, id);
		em.close();
		return medida;
	}

	// m�todo que lista todos os registros
	public List<NaturezaDaDespesa> listarTodos() {
		EntityManager em = JPAUtil.getEntityManager();
		CriteriaQuery<NaturezaDaDespesa> query = em.getCriteriaBuilder().createQuery(NaturezaDaDespesa.class);
		query.select(query.from(NaturezaDaDespesa.class));
		List<NaturezaDaDespesa> list = em.createQuery(query).getResultList();
		em.close();
		return list;
	}

	// m�todo que lista com crit�rios todos os registros
	public List<NaturezaDaDespesa> listarPorCriterios(String nome) {
		EntityManager em = JPAUtil.getEntityManager();
		String jpql = "select n from NaturezaDaDespesa n where n.nome like :pnome or n.codigo like :pcodigo";

		TypedQuery<NaturezaDaDespesa> query = em.createQuery(jpql, NaturezaDaDespesa.class);

		query.setParameter("pnome", '%' + nome + '%');
		query.setParameter("pcodigo", '%' + nome + '%');

		System.out.println(jpql);
		List<NaturezaDaDespesa> list = query.getResultList();
		em.close();
		return list;
	}

	// m�todo que lista todos os registros com pagina��o
	public List<NaturezaDaDespesa> listarTodosPaginada(int firstResult, int maxResults) {
		EntityManager em = JPAUtil.getEntityManager();
		CriteriaQuery<NaturezaDaDespesa> query = em.getCriteriaBuilder().createQuery(NaturezaDaDespesa.class);
		query.select(query.from(NaturezaDaDespesa.class));
		List<NaturezaDaDespesa> list = em.createQuery(query).setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
		em.close();
		return list;
	}

}
