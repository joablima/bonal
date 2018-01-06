package br.com.empresa.bonal.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.log4j.Logger;

import br.com.empresa.bonal.entidades.ItemBC;
import br.com.empresa.bonal.util.JPAUtil;

public class ItemBCRepositorio {

	final static Logger logger = Logger.getLogger(ItemBCRepositorio.class);

	// m�todo que persiste um registro
	public void adicionar(ItemBC itemBC) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(itemBC);
		em.getTransaction().commit();
		em.close();
	}

	// m�todo que atualiza um registro
	public void atualizar(ItemBC itemBC) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.merge(itemBC);
		em.getTransaction().commit();
		em.close();
	}

	// m�todo que remove um registro
	public void remover(ItemBC itemBC) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.remove(em.merge(itemBC));
		em.getTransaction().commit();
		em.close();
	}

	// m�todo que recupera um objeto pelo id
	public ItemBC buscarPorId(Long id) {
		EntityManager em = JPAUtil.getEntityManager();
		ItemBC bc = em.find(ItemBC.class, id);
		em.close();
		return bc;
	}

	// m�todo que lista todos os registros
	public List<ItemBC> listarTodos() {
		EntityManager em = JPAUtil.getEntityManager();
		CriteriaQuery<ItemBC> query = em.getCriteriaBuilder().createQuery(ItemBC.class);
		query.select(query.from(ItemBC.class));
		List<ItemBC> list = em.createQuery(query).getResultList();
		em.close();
		return list;
	}

	// m�todo que lista com crit�rios todos os registros
	public List<ItemBC> listarPorCriterios(String nome) {
		EntityManager em = JPAUtil.getEntityManager();
		String jpql = "select i from ItemBC i where i.id like :pid or i.data_de_validade like :pdataDeValidade or i.bem.nome like :pbem";

		TypedQuery<ItemBC> query = em.createQuery(jpql, ItemBC.class);

		query.setParameter("pid", '%' + nome + '%');
		query.setParameter("pdataDeValidade", '%' + nome + '%');
		query.setParameter("pbem", '%' + nome + '%');

		logger.info(jpql);
		List<ItemBC> list = query.getResultList();
		em.close();
		return list;
	}
}
