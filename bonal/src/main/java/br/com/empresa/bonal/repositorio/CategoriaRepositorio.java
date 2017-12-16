package br.com.empresa.bonal.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import br.com.empresa.bonal.entidades.Categoria;
import br.com.empresa.bonal.util.JPAUtil;

public class CategoriaRepositorio {

	// método que persiste um registro
	public void adicionar(Categoria categoria) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(categoria);
		em.getTransaction().commit();
		em.close();
	}

	// método que atualiza um registro
	public void atualizar(Categoria categoria) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.merge(categoria);
		em.getTransaction().commit();
		em.close();
	}

	// método que remove um registro
	public void remover(Categoria categoria) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.remove(em.merge(categoria));
		em.getTransaction().commit();
		em.close();
	}

	// método que recupera um objeto pelo id
	public Categoria buscarPorId(Long id) {
		EntityManager em = JPAUtil.getEntityManager();
		Categoria categoria = em.find(Categoria.class, id);
		em.close();
		return categoria;
	}

	// método que lista todos os registros
	public List<Categoria> listarTodos() {
		EntityManager em = JPAUtil.getEntityManager();
		CriteriaQuery<Categoria> query = em.getCriteriaBuilder().createQuery(Categoria.class);
		query.select(query.from(Categoria.class));
		List<Categoria> list = em.createQuery(query).getResultList();
		em.close();
		return list;
	}

	// método que lista com critérios todos os registros
	public List<Categoria> listarPorCriterios(String nome) {
		EntityManager em = JPAUtil.getEntityManager();
		String jpql = "select c from Categoria c where c.nome like :pnome or c.codigo like :pcodigo or c.descricao like :pdescricao";

		TypedQuery<Categoria> query = em.createQuery(jpql, Categoria.class);

		query.setParameter("pnome", '%' + nome + '%');
		query.setParameter("pcodigo", '%' + nome + '%');
		query.setParameter("pdescricao", '%' + nome + '%');

		System.out.println(jpql);
		List<Categoria> list = query.getResultList();
		em.close();
		return list;
	}
}
