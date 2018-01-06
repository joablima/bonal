package br.com.empresa.bonal.repositorio;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.log4j.Logger;

import br.com.empresa.bonal.entidades.Bem;
import br.com.empresa.bonal.entidades.CoeficienteTecnico;
import br.com.empresa.bonal.entidades.Produto;
import br.com.empresa.bonal.util.JPAUtil;

public class CoeficienteTecnicoRepositorio {

	final static Logger logger = Logger.getLogger(CoeficienteTecnicoRepositorio.class);

	// m�todo que persiste um registro
	public void adicionar(CoeficienteTecnico coeficiente, Long bemId, Long produtoId) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		
		Produto produto = em.find(Produto.class, produtoId);
		Bem bem = em.find(Bem.class, bemId);
		
		coeficiente.setProduto(produto);
		coeficiente.setBem(bem);
		
		em.persist(coeficiente);
		em.getTransaction().commit();
		em.close();
	}

	// m�todo que atualiza um registro
	public void atualizar(CoeficienteTecnico coeficiente, Long bemId, Long produtoId) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();

		Produto produto = em.find(Produto.class, produtoId);
		Bem bem = em.find(Bem.class, bemId);

		coeficiente.setProduto(produto);
		coeficiente.setBem(bem);

		em.merge(coeficiente);
		em.getTransaction().commit();
		em.close();
	}

	// m�todo que remove um registro
	public void remover(CoeficienteTecnico coeficiente) {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.remove(em.merge(coeficiente));
		em.getTransaction().commit();
		em.close();
	}

	// m�todo que recupera um objeto pelo id	
	public CoeficienteTecnico buscarPorId(Long id) {
		EntityManager em = JPAUtil.getEntityManager();
		CoeficienteTecnico bem = em.find(CoeficienteTecnico.class, id);
		em.close();
		return bem;
	}

	// m�todo que lista todos os registros
	public List<CoeficienteTecnico> listarTodos() {
		EntityManager em = JPAUtil.getEntityManager();
		CriteriaQuery<CoeficienteTecnico> query = em.getCriteriaBuilder().createQuery(CoeficienteTecnico.class);
		query.select(query.from(CoeficienteTecnico.class));
		List<CoeficienteTecnico> list = em.createQuery(query).getResultList();
		em.close();
		return list;
	}	
	
	// m�todo que lista com crit�rios todos os registros
	public List<CoeficienteTecnico> listarPorCriterios(BigDecimal quantidade, Long bemId, Long produtoId) {
		EntityManager em = JPAUtil.getEntityManager();
		String jpql = "select c from CoeficienteTecnico c where ";

		if (quantidade != null)
			jpql += "(c.quantidade like :pquantidade) and ";
		if (bemId != null)
			jpql += "s.bem.id = :pbem and ";
		if (produtoId != null)
			jpql += "s.produto.id = :pproduto and ";
		jpql += "1 = 1";

		TypedQuery<CoeficienteTecnico> query = em.createQuery(jpql, CoeficienteTecnico.class);

		if (quantidade != null) {
			query.setParameter("pnome", '%' + quantidade.toString() + '%');
		}
		if (bemId != null)
			query.setParameter("pbem", bemId);	
		if (produtoId != null)
			query.setParameter("pproduto", produtoId);

		logger.info(jpql);
		List<CoeficienteTecnico> list = query.getResultList();
		em.close();
		return list;
	}
}
