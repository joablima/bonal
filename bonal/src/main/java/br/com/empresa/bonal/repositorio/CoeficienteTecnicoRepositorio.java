package br.com.empresa.bonal.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.empresa.bonal.entidades.CoeficienteTecnico;
import br.com.empresa.bonal.entidades.ItemDeProducao;
import br.com.empresa.bonal.entidades.Produto;
import br.com.empresa.bonal.entidades.UnidadeDeMedida;
import br.com.empresa.bonal.util.logging.Logging;

public class CoeficienteTecnicoRepositorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	// m�todo que persiste um registro
	public void adicionar(CoeficienteTecnico coeficienteTecnico) {

		em.persist(coeficienteTecnico);
	}

	// m�todo que atualiza um registro
	public void atualizar(CoeficienteTecnico coeficienteTecnico) {

		em.merge(coeficienteTecnico);
	}

	// m�todo que remove um registro
	public void remover(CoeficienteTecnico coeficienteTecnico) {
		em.merge(coeficienteTecnico);
	}

	// m�todo que recupera um objeto pelo id
	public CoeficienteTecnico buscarPorId(Long id) {
		return em.find(CoeficienteTecnico.class, id);
	}

	// m�todo que recupera um objeto pelo id
	public Produto getProdutoPorId(Long id) {
		return em.find(Produto.class, id);
	}

	// m�todo que lista todos os registros
	public List<CoeficienteTecnico> listarTodos() {
		try {
			return em.createQuery("select s from CoeficienteTecnico s", CoeficienteTecnico.class).getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	@Logging
	// m�todo que lista todos os registros
	public List<CoeficienteTecnico> listarTodosPorProduto(Long id) {
		String jpql = "select c from CoeficienteTecnico c where produto = "+id;

		

		TypedQuery<CoeficienteTecnico> query = em.createQuery(jpql, CoeficienteTecnico.class);

		

		return query.getResultList();
	}

	/*
	 * // m�todo que lista com crit�rios todos os registros public
	 * List<CoeficienteTecnico> listarPorCriterios(String nome) { String jpql =
	 * "select s from CoeficienteTecnico s where ";
	 * 
	 * if (nome != null) jpql +=
	 * "(s.nome like :pnome or s.codigo like :pcodigo or s.descricao like :pdescricao);"
	 * ; jpql += "1 = 1";
	 * 
	 * TypedQuery<CoeficienteTecnico> query = em.createQuery(jpql,
	 * CoeficienteTecnico.class);
	 * 
	 * if (nome != null) query.setParameter("pnome", '%' + nome +
	 * '%').setParameter("pcodigo", '%' + nome + '%')
	 * .setParameter("pdescricao", '%' + nome + '%');
	 * 
	 * return query.getResultList(); }
	 */

	// método que verifica se elemento existe
	public Produto getProdutoPorCodigo(String codigo) {
		TypedQuery<Produto> query = em.createQuery("select c from Produto c where c.codigo = :pcodigo", Produto.class)
				.setParameter("pcodigo", codigo.toUpperCase());

		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	// método que verifica se elemento existe
	public UnidadeDeMedida getUnidadeDeMedidaPorSigla(String sigla) {
		TypedQuery<UnidadeDeMedida> query = em
				.createQuery("select c from UnidadeDeMedida c where c.sigla = :psigla", UnidadeDeMedida.class)
				.setParameter("psigla", sigla.toUpperCase());

		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	// método que verifica se elemento existe
	public ItemDeProducao getItemDeProducaoPorCodigo(String codigo) {
		TypedQuery<ItemDeProducao> query = em
				.createQuery("select c from ItemDeProducao c where c.codigo = :pcodigo", ItemDeProducao.class)
				.setParameter("pcodigo", codigo.toUpperCase());

		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

}
