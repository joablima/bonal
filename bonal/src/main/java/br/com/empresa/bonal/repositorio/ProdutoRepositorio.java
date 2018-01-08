package br.com.empresa.bonal.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import br.com.empresa.bonal.entidades.Produto;
import br.com.empresa.bonal.entidades.UnidadeDeMedida;
import br.com.empresa.bonal.util.JPAUtil;

public class ProdutoRepositorio {

	final static Logger logger = Logger.getLogger(ProdutoRepositorio.class);

	// m�todo que persiste um registro
	public void adicionar(Produto produto, Long unidadeDeMedidaId) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();

		UnidadeDeMedida unidadeDeMedida = em.find(UnidadeDeMedida.class, unidadeDeMedidaId);

		produto.setUnidadeDeMedida(unidadeDeMedida);

		em.persist(produto);
		em.getTransaction().commit();
		em.close();
	}

	// m�todo que atualiza um registro
	public void atualizar(Produto produto, Long unidadeDeMedidaId) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();

		UnidadeDeMedida unidadeDeMedida = em.find(UnidadeDeMedida.class, unidadeDeMedidaId);

		produto.setUnidadeDeMedida(unidadeDeMedida);

		em.merge(produto);
		em.getTransaction().commit();
		em.close();
	}

	// m�todo que remove um registro
	public void remover(Produto produto) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.merge(produto);
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
		try {
			return em.createQuery("select p from Produto p where p.status = true", Produto.class).getResultList();
		} catch (Exception e) {
			return null;
		} finally {
			em.close();
		}
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

		logger.info(jpql);
		List<Produto> list = query.getResultList();
		em.close();
		return list;
	}

	// método que verifica se elemento existe
	public Produto codigoExiste(Produto produto) {
		EntityManager em = JPAUtil.getEntityManager();

		TypedQuery<Produto> query = em.createQuery("select p from Produto p where p.codigo = :codigo", Produto.class)
				.setParameter("codigo", produto.getCodigo());

		try {
			Produto novoProduto = query.getSingleResult();
			return novoProduto;
		} catch (Exception e) {
			return null;
		} finally {
			em.close();
		}
	}
}
