package br.com.empresa.bonal.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.log4j.Logger;

import br.com.empresa.bonal.entidades.Categoria;
import br.com.empresa.bonal.util.JPAUtil;

public class CategoriaRepositorio {

	final static Logger logger = Logger.getLogger(CategoriaRepositorio.class);

	// m�todo que persiste um registro
	public void adicionar(Categoria categoria) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		logger.info("Categoria sendo adicionada, irá ser persistida nesse momento");
		em.persist(categoria);
		em.getTransaction().commit();
		em.close();
	}

	// m�todo que atualiza um registro
	public void atualizar(Categoria categoria) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.merge(categoria);
		em.getTransaction().commit();
		em.close();
	}

	// m�todo que remove um registro
	public void remover(Categoria categoria) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.merge(categoria);
		em.getTransaction().commit();
		em.close();
	}

	// m�todo que recupera um objeto pelo id
	public Categoria buscarPorId(Long id) {
		EntityManager em = JPAUtil.getEntityManager();
		Categoria categoria = em.find(Categoria.class, id);
		em.close();
		return categoria;
	}

	// m�todo que lista todos os registros
	public List<Categoria> listarTodos() {
		EntityManager em = JPAUtil.getEntityManager();
		CriteriaQuery<Categoria> query = em.getCriteriaBuilder().createQuery(Categoria.class);
		query.select(query.from(Categoria.class));
		List<Categoria> list = em.createQuery(query).getResultList();
		em.close();
		return list;
	}

	// m�todo que lista com crit�rios todos os registros
	public List<Categoria> listarPorCriterios(String nome) {
		EntityManager em = JPAUtil.getEntityManager();
		String jpql = "select c from Categoria c where ";

		if (nome != null)
			jpql += "(c.nome like :pnome or c.codigo like :pcodigo or c.descricao like :pdescricao);";
		jpql += "1 = 1";

		TypedQuery<Categoria> query = em.createQuery(jpql, Categoria.class);

		if (nome != null) {
			query.setParameter("pnome", '%' + nome + '%');
			query.setParameter("pcodigo", '%' + nome + '%');
			query.setParameter("pdescricao", '%' + nome + '%');
		}

		logger.info(jpql);
		List<Categoria> list = query.getResultList();
		em.close();
		return list;
	}

	// método que verifica se elemento existe
	public Categoria codigoExiste(Categoria categoria) {
		EntityManager em = JPAUtil.getEntityManager();
		String jpql = "select c from Categoria c where c.codigo = :pcodigo";

		TypedQuery<Categoria> query = em.createQuery(jpql, Categoria.class);
		query.setParameter("pcodigo", categoria.getCodigo());

		try {
			Categoria novaCategoria = query.getSingleResult();
			return novaCategoria;
		} catch (Exception e) {
			return null;
		} finally {
			logger.info(jpql);
			em.close();
		}
	}
}
