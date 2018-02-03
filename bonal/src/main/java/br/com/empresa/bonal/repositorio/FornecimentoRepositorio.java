package br.com.empresa.bonal.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.empresa.bonal.entidades.Fornecedor;
import br.com.empresa.bonal.entidades.ItemDeProducao;
import br.com.empresa.bonal.entidades.Produto;
import br.com.empresa.bonal.entidades.UnidadeDeMedida;
import br.com.empresa.bonal.entidades.Venda;
import br.com.empresa.bonal.util.logging.Logging;

public class FornecimentoRepositorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	// m�todo que persiste um registro
	public void adicionar(Fornecedor fornecedor) {
		em.persist(fornecedor);
	}

	// m�todo que atualiza um registro
	public void atualizar(Fornecedor fornecedor) {
		em.merge(fornecedor);
	}

	// m�todo que remove um registro
	public void remover(Fornecedor fornecedor) {
		em.merge(fornecedor);
	}

	// m�todo que recupera um objeto pelo id
	public ItemDeProducao getItemDeProducaoPorId(Long id) {
		return em.find(ItemDeProducao.class, id);
	}

	// m�todo que lista todos os registros
	public List<Fornecedor> listarTodosItensPorFornecedor(Long fornecedorId) {
		try {
			return em.createQuery("select s from fornecimento_de_item s where fornecedor_id = "+fornecedorId, Fornecedor.class).getResultList();
		} catch (Exception e) {
			return null;
		}
	}


	/*
	 * // m�todo que lista com crit�rios todos os registros public
	 * List<Fornecedor> listarPorCriterios(String nome) { String jpql =
	 * "select s from Fornecedor s where ";
	 * 
	 * if (nome != null) jpql +=
	 * "(s.nome like :pnome or s.codigo like :pcodigo or s.descricao like :pdescricao);"
	 * ; jpql += "1 = 1";
	 * 
	 * TypedQuery<Fornecedor> query = em.createQuery(jpql, Fornecedor.class);
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
	public Fornecedor getFornecedorPorCodigo(String codigo) {
		TypedQuery<Fornecedor> query = em
				.createQuery("select c from Fornecedor c where c.codigo = :pcodigo", Fornecedor.class)
				.setParameter("pcodigo", codigo.toUpperCase());

		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

}
