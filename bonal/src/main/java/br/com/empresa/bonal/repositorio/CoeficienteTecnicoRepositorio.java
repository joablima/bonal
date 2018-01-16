package br.com.empresa.bonal.repositorio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.logging.log4j.Logger;

import br.com.empresa.bonal.entidades.Bem;
import br.com.empresa.bonal.entidades.CoeficienteTecnico;
import br.com.empresa.bonal.entidades.Produto;

public class CoeficienteTecnicoRepositorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;
	
	@Inject
	private Logger logger;

	// m�todo que persiste um registro
	public void adicionar(CoeficienteTecnico coeficiente, Long bemId, Long produtoId) {
		Produto produto = em.find(Produto.class, produtoId);
		Bem bem = em.find(Bem.class, bemId);

		coeficiente.setProduto(produto);
		coeficiente.setBem(bem);

		em.persist(coeficiente);
	}

	// m�todo que atualiza um registro
	public void atualizar(CoeficienteTecnico coeficiente, Long bemId, Long produtoId) {

		Produto produto = em.find(Produto.class, produtoId);
		Bem bem = em.find(Bem.class, bemId);

		coeficiente.setProduto(produto);
		coeficiente.setBem(bem);

		em.merge(coeficiente);
	}

	// m�todo que remove um registro
	public void remover(CoeficienteTecnico coeficiente) {
		em.merge(coeficiente);
	}

	// m�todo que recupera um objeto pelo id
	public CoeficienteTecnico buscarPorId(Long id) {
		CoeficienteTecnico bem = em.find(CoeficienteTecnico.class, id);
		return bem;
	}

	// m�todo que lista todos os registros
	public List<CoeficienteTecnico> listarTodos() {
		CriteriaQuery<CoeficienteTecnico> query = em.getCriteriaBuilder().createQuery(CoeficienteTecnico.class);
		query.select(query.from(CoeficienteTecnico.class));
		List<CoeficienteTecnico> list = em.createQuery(query).getResultList();
		return list;
	}

	// m�todo que lista com crit�rios todos os registros
	public List<CoeficienteTecnico> listarPorCriterios(BigDecimal quantidade, Long bemId, Long produtoId) {
		String jpql = "select c from CoeficienteTecnico c where ";

		if (quantidade != null)
			jpql += "(c.quantidade like :pquantidade) and ";
		if (bemId != null)
			jpql += "c.bem.id = :pbem and ";
		if (produtoId != null)
			jpql += "c.produto.id = :pproduto and ";
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
		return list;
	}

	public List<CoeficienteTecnico> buscarCoeficienteDoProduto(Long produtoId) {
		List<CoeficienteTecnico> lista = em
				.createQuery("select c from CoeficienteTecnico c where c.produto.id = :produto",
						CoeficienteTecnico.class)
				.setParameter("produto", produtoId).getResultList();

		return lista;
	}
}
