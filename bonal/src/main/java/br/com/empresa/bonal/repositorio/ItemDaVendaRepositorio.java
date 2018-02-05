package br.com.empresa.bonal.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.empresa.bonal.entidades.ItemDaVenda;
import br.com.empresa.bonal.entidades.ItemDeProducao;
import br.com.empresa.bonal.entidades.Produto;
import br.com.empresa.bonal.entidades.UnidadeDeMedida;
import br.com.empresa.bonal.entidades.Venda;
import br.com.empresa.bonal.util.logging.Logging;

public class ItemDaVendaRepositorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	// m�todo que persiste um registro
	public void adicionar(ItemDaVenda itemDaVenda) {

		em.persist(itemDaVenda);
	}

	// m�todo que atualiza um registro
	public void atualizar(ItemDaVenda itemDaVenda) {

		em.merge(itemDaVenda);
	}

	// m�todo que atualiza um registro
	public void atualizar(Venda venda) {

		em.merge(venda);
	}

	// m�todo que atualiza um registro
	public void atualizar(Produto produto) {

		em.merge(produto);
	}

	// m�todo que remove um registro
	public void remover(ItemDaVenda itemDaVenda) {
		em.merge(itemDaVenda);
	}

	// m�todo que recupera um objeto pelo id
	public ItemDaVenda buscarPorId(Long id) {
		return em.find(ItemDaVenda.class, id);
	}

	// m�todo que recupera um objeto pelo id
	public Produto getProdutoPorId(Long id) {
		return em.find(Produto.class, id);
	}

	// m�todo que recupera um objeto pelo id
	public Venda getVendaPorId(Long id) {
		return em.find(Venda.class, id);
	}

	// m�todo que lista todos os registros
	public List<ItemDaVenda> listarTodos() {
		try {
			return em.createQuery("select s from ItemDaVenda s", ItemDaVenda.class).getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	@Logging
	// m�todo que lista todos os registros
	public List<ItemDaVenda> listarTodosPorVenda(Long id) {
		String jpql = "select c from ItemDaVenda c where venda = " + id;

		TypedQuery<ItemDaVenda> query = em.createQuery(jpql, ItemDaVenda.class);

		return query.getResultList();
	}

	/*
	 * // m�todo que lista com crit�rios todos os registros public
	 * List<ItemDaVenda> listarPorCriterios(String nome) { String jpql =
	 * "select s from ItemDaVenda s where ";
	 * 
	 * if (nome != null) jpql +=
	 * "(s.nome like :pnome or s.codigo like :pcodigo or s.descricao like :pdescricao);"
	 * ; jpql += "1 = 1";
	 * 
	 * TypedQuery<ItemDaVenda> query = em.createQuery(jpql, ItemDaVenda.class);
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
