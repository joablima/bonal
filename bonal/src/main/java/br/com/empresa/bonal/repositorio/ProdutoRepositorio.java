package br.com.empresa.bonal.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import br.com.empresa.bonal.entidades.Produto;
import br.com.empresa.bonal.entidades.Categoria;
import br.com.empresa.bonal.entidades.UnidadeDeMedida;
import br.com.empresa.bonal.util.JPAUtil;

public class ProdutoRepositorio {

	// m�todo que persiste um registro
	public void adicionar(Produto produto, Long categoriaId, Long unidadeDeMedidaId) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		
		UnidadeDeMedida unidadeDeMedida = em.find(UnidadeDeMedida.class, unidadeDeMedidaId);
		Categoria categoria = em.find(Categoria.class, categoriaId);

		produto.setUnidadeDeMedida(unidadeDeMedida);
		produto.setCategoria(categoria);
		
		em.persist(produto);
		em.getTransaction().commit();
		em.close();
	}

	// m�todo que atualiza um registro
	public void atualizar(Produto produto, Long categoriaId, Long unidadeDeMedidaId) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();

		UnidadeDeMedida unidadeDeMedida = em.find(UnidadeDeMedida.class, unidadeDeMedidaId);
		Categoria categoria = em.find(Categoria.class, categoriaId);

		produto.setUnidadeDeMedida(unidadeDeMedida);
		produto.setCategoria(categoria);

		em.merge(produto);
		em.getTransaction().commit();
		em.close();
	}

	// m�todo que remove um registro
	public void remover(Produto produto) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.remove(em.merge(produto));
		em.getTransaction().commit();
		em.close();
	}

	// m�todo que recupera um objeto pelo id	
	public Produto buscarPorId(Long id) {
		EntityManager em = JPAUtil.getEntityManager();
		Produto bem = em.find(Produto.class, id);
		em.close();
		return bem;
	}

	// m�todo que lista todos os registros
	public List<Produto> listarTodos() {
		EntityManager em = JPAUtil.getEntityManager();
		CriteriaQuery<Produto> query = em.getCriteriaBuilder().createQuery(Produto.class);
		query.select(query.from(Produto.class));
		List<Produto> list = em.createQuery(query).getResultList();
		em.close();
		return list;
	}	
	
	// m�todo que lista com crit�rios todos os registros
	public List<Produto> listarPorCriterios(String nome, Long categoriaId, Long unidadeDeMedidaId) {
		EntityManager em = JPAUtil.getEntityManager();
		String jpql = "select p from Produto p where ";

		if (nome != null)
			jpql += "(p.nome like :pnome or p.codigo like :pcodigo or p.descricao like :pdescricao) and ";
		if (categoriaId != null)
			jpql += "p.categoria.id = :pcategoria and ";
		if (unidadeDeMedidaId != null)
			jpql += "p.unidadeDeMedida.id = :punidade and ";
		jpql += "1 = 1";

		TypedQuery<Produto> query = em.createQuery(jpql, Produto.class);

		if (nome != null) {
			query.setParameter("pnome", '%' + nome + '%');
			query.setParameter("pcodigo", '%' + nome + '%');
			query.setParameter("pdescricao", '%' + nome + '%');
		}
		if (categoriaId != null)
			query.setParameter("pcategoria", categoriaId);	
		if (unidadeDeMedidaId != null)
			query.setParameter("punidade", unidadeDeMedidaId);

		System.out.println(jpql);
		List<Produto> list = query.getResultList();
		em.close();
		return list;
	}
}
