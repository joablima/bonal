package br.com.empresa.bonal.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import br.com.empresa.bonal.entidades.ItemDeProducao;

@SuppressWarnings("serial")
public class ItemDeProducaoRepositorio implements Serializable {

	@Inject
	EntityManager em;

	// m�todo que persiste um registro
	public void adicionar(ItemDeProducao itemDeProducao) {
		em.persist(itemDeProducao);
	}

	// m�todo que atualiza um registro
	public void atualizar(ItemDeProducao itemDeProducao) {
		em.merge(itemDeProducao);
	}

	// m�todo que remove um registro
	public void remover(ItemDeProducao itemDeProducao) {
		em.merge(itemDeProducao);
	}

	// m�todo que recupera um objeto pelo id
	public ItemDeProducao buscarPorId(Long id) {
		return em.find(ItemDeProducao.class, id);
	}

	// m�todo que lista todos os registros
	public List<ItemDeProducao> listarTodos() {
		try {
			return em.createQuery("select u from ItemDeProducao u", ItemDeProducao.class).getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	// m�todo que lista com crit�rios todos os registros
	public List<ItemDeProducao> listarPorCriterios(String nome) {
		String jpql = "select u from ItemDeProducao u where u.nome like :pnome or u.sigla like :psigla";

		return em.createQuery(jpql, ItemDeProducao.class)
				.setParameter("pnome", '%' + nome + '%')
				.setParameter("psigla", '%' + nome + '%')
				.getResultList();
	}

	// m�todo que lista todos os registros com pagina��o
	public List<ItemDeProducao> listarTodosPaginada(int firstResult, int maxResults) {
		CriteriaQuery<ItemDeProducao> query = em.getCriteriaBuilder().createQuery(ItemDeProducao.class);
		query.select(query.from(ItemDeProducao.class));
		List<ItemDeProducao> list = em.createQuery(query).setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
		return list;
	}

	// método que verifica se elemento existe
	public ItemDeProducao getItemDeProducaoPorCodigo(String codigo) {
		TypedQuery<ItemDeProducao> query = em
				.createQuery("select u from ItemDeProducao u where u.codigo = :codigo", ItemDeProducao.class)
				.setParameter("codigo", codigo.toUpperCase());

		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

}
