package br.com.empresa.bonal.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.log4j.Logger;

import br.com.empresa.bonal.entidades.ItemBP;
import br.com.empresa.bonal.util.JPAUtil;

public class ItemBPRepositorio {

	final static Logger logger = Logger.getLogger(ItemBPRepositorio.class);

	// m�todo que persiste um registro
	public void adicionar(ItemBP itemBP) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(itemBP);
		em.getTransaction().commit();
		em.close();
	}

	// m�todo que atualiza um registro
	public void atualizar(ItemBP itemBP) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.merge(itemBP);
		em.getTransaction().commit();
		em.close();
	}

	// m�todo que remove um registro
	public void remover(ItemBP itemBP) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.remove(em.merge(itemBP));
		em.getTransaction().commit();
		em.close();
	}

	// m�todo que recupera um objeto pelo id
	public ItemBP buscarPorId(Long id) {
		EntityManager em = JPAUtil.getEntityManager();
		ItemBP bp = em.find(ItemBP.class, id);
		em.close();
		return bp;
	}

	// m�todo que lista todos os registros
	public List<ItemBP> listarTodos() {
		EntityManager em = JPAUtil.getEntityManager();
		CriteriaQuery<ItemBP> query = em.getCriteriaBuilder().createQuery(ItemBP.class);
		query.select(query.from(ItemBP.class));
		List<ItemBP> list = em.createQuery(query).getResultList();
		em.close();
		return list;
	}

	// m�todo que lista com crit�rios todos os registros
	public List<ItemBP> listarPorCriterios(String nome) {
		EntityManager em = JPAUtil.getEntityManager();
		String jpql = "select i from ItemBP i where i.id like :pid or i.data_de_garantia like :pdataDeGarantia or i.bem.nome like :pbem";

		TypedQuery<ItemBP> query = em.createQuery(jpql, ItemBP.class);

		query.setParameter("pid", '%' + nome + '%');
		query.setParameter("pdataDeGarantia", '%' + nome + '%');
		query.setParameter("pbem", '%' + nome + '%');

		logger.info(jpql);
		List<ItemBP> list = query.getResultList();
		em.close();
		return list;
	}
}
