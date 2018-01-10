package br.com.empresa.bonal.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import br.com.empresa.bonal.entidades.Bem;
import br.com.empresa.bonal.entidades.Categoria;
import br.com.empresa.bonal.entidades.UnidadeDeMedida;
import br.com.empresa.bonal.util.JPAUtil;

public class BemRepositorio {

	final static Logger logger = Logger.getLogger(BemRepositorio.class);

	// m�todo que persiste um registro
	public void adicionar(Bem bem, Long categoriaId, Long unidadeDeMedidaId) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();

		UnidadeDeMedida unidadeDeMedida = em.find(UnidadeDeMedida.class, unidadeDeMedidaId);
		Categoria categoria = em.find(Categoria.class, categoriaId);

		bem.setUnidadeDeMedida(unidadeDeMedida);
		bem.setCategoria(categoria);

		em.persist(bem);
		em.getTransaction().commit();
		em.close();
	}

	// m�todo que atualiza um registro
	public void atualizar(Bem bem, Long categoriaId, Long unidadeDeMedidaId) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();

		UnidadeDeMedida unidadeDeMedida = em.find(UnidadeDeMedida.class, unidadeDeMedidaId);
		Categoria categoria = em.find(Categoria.class, categoriaId);

		bem.setUnidadeDeMedida(unidadeDeMedida);
		bem.setCategoria(categoria);

		em.merge(bem);
		em.getTransaction().commit();
		em.close();
	}

	// m�todo que remove um registro
	public void remover(Bem bem) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.merge(bem);
		em.getTransaction().commit();
		em.close();
	}

	// m�todo que recupera um objeto pelo id
	public Bem buscarPorId(Long id) {
		EntityManager em = JPAUtil.getEntityManager();
		Bem bem = em.find(Bem.class, id);
		em.close();
		return bem;
	}

	// m�todo que lista todos os registros
	public List<Bem> listarTodos() {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			return em.createQuery("select b from Bem b", Bem.class).getResultList();
		} catch (Exception e) {
			return null;
		} finally {
			em.close();
		}
	}

	// m�todo que lista com crit�rios todos os registros
	public List<Bem> listarPorCriterios(String nome, Long categoriaId, Long unidadeDeMedidaId) {
		EntityManager em = JPAUtil.getEntityManager();
		String jpql = "select b from Bem b where ";

		if (nome != null)
			jpql += "(b.nome like :pnome or b.codigo like :pcodigo or b.descricao like :pdescricao) and ";
		if (categoriaId != null)
			jpql += "b.categoria.id = :pcategoria and ";
		if (unidadeDeMedidaId != null)
			jpql += "b.unidadeDeMedida.id = :punidade and ";
		jpql += "1 = 1";

		TypedQuery<Bem> query = em.createQuery(jpql, Bem.class);

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
		List<Bem> list = query.getResultList();
		em.close();
		return list;
	}

	// método que verifica se elemento existe
	public Bem codigoExiste(Bem bem) {
		EntityManager em = JPAUtil.getEntityManager();

		TypedQuery<Bem> query = em.createQuery("select b from Bem b where b.codigo = :pcodigo", Bem.class)
				.setParameter("pcodigo", bem.getCodigo());

		try {
			Bem novoBem = query.getSingleResult();
			return novoBem;
		} catch (Exception e) {
			return null;
		} finally {
			em.close();
		}
	}
}
