package br.com.empresa.bonal.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import br.com.empresa.bonal.entidades.Operacao;

public class OperacaoRepositorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	// m�todo que persiste um registro
	public void adicionar(Operacao operacao) {
		em.persist(operacao);
	}

	// m�todo que atualiza um registro
	public void atualizar(Operacao operacao) {
		em.merge(operacao);
	}

	// m�todo que remove um registro
	public void remover(Operacao operacao) {
		em.merge(operacao);
	}

	// m�todo que recupera um objeto pelo id
	public Operacao buscarPorId(Long id) {
		return em.find(Operacao.class, id);
	}

	// m�todo que lista todos os registros
	public List<Operacao> listarTodos() {
		try {
			return em.createQuery("select o from Operacao o", Operacao.class).getResultList();
		} catch (Exception e) {
			return null;
		}
	}
	
	// método que verifica se elemento existe
	public Operacao getOperacaoPorCodigo(String codigo) {
		TypedQuery<Operacao> query = em
				.createQuery("select c from Operacao c where c.codigo = :pcodigo", Operacao.class)
				.setParameter("pcodigo", codigo.toUpperCase());

		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}


	// m�todo que lista com critérios todos os registros
	public List<Operacao> listarPorCriterios(String nome) {
		String jpql = "select o from Operacao o where o.nome like :pnome or o.codigo like :pcodigo or o.descricao like :pdescricao";

		 return em.createQuery(jpql, Operacao.class)
			.setParameter("pnome", '%' + nome + '%')
			.setParameter("pcodigo", '%' + nome + '%')
			.setParameter("pdescricao", '%' + nome + '%')
			.getResultList();
	}

	// método que lista todos os registros com paginação
	public List<Operacao> listarTodosPaginada(int firstResult, int maxResults) {
		CriteriaQuery<Operacao> query = em.getCriteriaBuilder().createQuery(Operacao.class);
		query.select(query.from(Operacao.class));
		List<Operacao> list = em.createQuery(query).setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
		return list;
	}

}
