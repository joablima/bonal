package br.com.empresa.bonal.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import br.com.empresa.bonal.entidades.ItemBC;
import br.com.empresa.bonal.util.JPAUtil;

public class ItemBCRepositorio {

	// método que persiste um registro
	public void adicionar(ItemBC itemBC) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(itemBC);
		em.getTransaction().commit();
		em.close();
	}

	// método que atualiza um registro
	public void atualizar(ItemBC itemBC) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.merge(itemBC);
		em.getTransaction().commit();
		em.close();
	}

	// método que remove um registro
	public void remover(ItemBC itemBC) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.remove(em.merge(itemBC));
		em.getTransaction().commit();
		em.close();
	}

	// método que recupera um objeto pelo id
	public ItemBC buscarPorId(Long id) {
		EntityManager em = JPAUtil.getEntityManager();
		ItemBC bc = em.find(ItemBC.class, id);
		em.close();
		return bc;
	}

	// método que lista todos os registros
	public List<ItemBC> listarTodos() {
		EntityManager em = JPAUtil.getEntityManager();
		CriteriaQuery<ItemBC> query = em.getCriteriaBuilder().createQuery(ItemBC.class);
		query.select(query.from(ItemBC.class));
		List<ItemBC> list = em.createQuery(query).getResultList();
		em.close();
		return list;
	}

	// método que lista com critérios todos os registros
	public List<ItemBC> listarPorCriterios(String nome) {
		EntityManager em = JPAUtil.getEntityManager();
		String jpql = "select i from ItemBC i where i.codigo like :pcodigo or i.dataDeValidade like :pdataDeValidade";

		TypedQuery<ItemBC> query = em.createQuery(jpql, ItemBC.class);

		query.setParameter("pnome", '%' + nome + '%');
		query.setParameter("psigla", '%' + nome + '%');

		System.out.println(jpql);
		List<ItemBC> list = query.getResultList();
		em.close();
		return list;
	}
}
