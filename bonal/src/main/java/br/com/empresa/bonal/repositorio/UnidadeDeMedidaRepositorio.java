package br.com.empresa.bonal.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.logging.log4j.Logger;

import br.com.empresa.bonal.entidades.UnidadeDeMedida;

@SuppressWarnings("serial")
public class UnidadeDeMedidaRepositorio implements Serializable {

	@Inject
	EntityManager em;
	
	@Inject
	private Logger logger;

	// m�todo que persiste um registro
	public void adicionar(UnidadeDeMedida unidadeDeMedida) {
		em.persist(unidadeDeMedida);
	}

	// m�todo que atualiza um registro
	public void atualizar(UnidadeDeMedida unidadeDeMedida) {
		em.merge(unidadeDeMedida);
	}

	// m�todo que remove um registro
	public void remover(UnidadeDeMedida unidadeDeMedida) {
		em.merge(unidadeDeMedida);
	}

	// m�todo que recupera um objeto pelo id
	public UnidadeDeMedida buscarPorId(Long id) {
		UnidadeDeMedida medida = em.find(UnidadeDeMedida.class, id);
		return medida;
	}

	// m�todo que lista todos os registros
	public List<UnidadeDeMedida> listarTodos() {
		try {
			return em.createQuery("select u from UnidadeDeMedida u", UnidadeDeMedida.class).getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	// m�todo que lista com crit�rios todos os registros
	public List<UnidadeDeMedida> listarPorCriterios(String nome) {
		String jpql = "select u from UnidadeDeMedida u where u.nome like :pnome or u.sigla like :psigla";

		TypedQuery<UnidadeDeMedida> query = em.createQuery(jpql, UnidadeDeMedida.class);

		query.setParameter("pnome", '%' + nome + '%');
		query.setParameter("psigla", '%' + nome + '%');

		logger.info(jpql);
		List<UnidadeDeMedida> list = query.getResultList();
		return list;
	}

	// m�todo que lista todos os registros com pagina��o
	public List<UnidadeDeMedida> listarTodosPaginada(int firstResult, int maxResults) {
		CriteriaQuery<UnidadeDeMedida> query = em.getCriteriaBuilder().createQuery(UnidadeDeMedida.class);
		query.select(query.from(UnidadeDeMedida.class));
		List<UnidadeDeMedida> list = em.createQuery(query).setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
		return list;
	}

	// método que verifica se elemento existe
	public UnidadeDeMedida unidadeMedidaExiste(UnidadeDeMedida unidade) {
		TypedQuery<UnidadeDeMedida> query = em
				.createQuery("select u from UnidadeDeMedida u where u.sigla = :sigla", UnidadeDeMedida.class)
				.setParameter("sigla", unidade.getSigla());

		try {
			UnidadeDeMedida novaUnidade = query.getSingleResult();
			return novaUnidade;
		} catch (Exception e) {
			return null;
		}
	}

}
