//package br.com.empresa.bonal.depreciadas;
//
//import java.io.Serializable;
//import java.math.BigDecimal;
//import java.util.List;
//
//import javax.inject.Inject;
//import javax.persistence.EntityManager;
//import javax.persistence.TypedQuery;
//import javax.persistence.criteria.CriteriaQuery;
//
//import br.com.empresa.bonal.entidades.CoeficienteTecnico;
//
//public class CoeficienteTecnicoRepositorio implements Serializable {
//	private static final long serialVersionUID = 1L;
//
//	@Inject
//	EntityManager em;
//
//	public void adicionar(CoeficienteTecnico coeficiente) {
//		em.persist(coeficiente);
//	}
//
//	public void atualizar(CoeficienteTecnico coeficiente) {
//		em.merge(coeficiente);
//	}
//
//	public void remover(CoeficienteTecnico coeficiente) {
//		em.merge(coeficiente);
//	}
//
//	public CoeficienteTecnico buscarPorId(Long id) {
//		return em.find(CoeficienteTecnico.class, id);
//	}
//
//	public List<CoeficienteTecnico> listarTodos() {
//		CriteriaQuery<CoeficienteTecnico> query = em.getCriteriaBuilder().createQuery(CoeficienteTecnico.class);
//		query.select(query.from(CoeficienteTecnico.class));
//		List<CoeficienteTecnico> list = em.createQuery(query).getResultList();
//		return list;
//	}
//
//	public List<CoeficienteTecnico> listarPorCriterios(BigDecimal quantidade, Long bemId, Long produtoId) {
//		String jpql = "select c from CoeficienteTecnico c where ";
//
//		if (quantidade != null)
//			jpql += "(c.quantidade like :pquantidade) and ";
//		if (bemId != null)
//			jpql += "c.bem.id = :pbem and ";
//		if (produtoId != null)
//			jpql += "c.produto.id = :pproduto and ";
//		jpql += "1 = 1";
//
//		TypedQuery<CoeficienteTecnico> query = em.createQuery(jpql, CoeficienteTecnico.class);
//
//		if (quantidade != null)
//			query.setParameter("pnome", '%' + quantidade.toString() + '%');
//		if (bemId != null)
//			query.setParameter("pbem", bemId);
//		if (produtoId != null)
//			query.setParameter("pproduto", produtoId);
//
//		return query.getResultList();
//	}
//
//	public List<CoeficienteTecnico> buscarCoeficienteDoProduto(Long produtoId) {
//		return em.createQuery("select c from CoeficienteTecnico c where c.produto.id = :produto",
//				CoeficienteTecnico.class).setParameter("produto", produtoId).getResultList();
//	}
//}
